package com.mini_assignment.user_verification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini_assignment.user_verification.dto.*;
import com.mini_assignment.user_verification.dto.RandomUserDTO.Result;
import com.mini_assignment.user_verification.entity.User;
import com.mini_assignment.user_verification.entity.User.AgeComparator;
import com.mini_assignment.user_verification.entity.User.NameComparator;
import com.mini_assignment.user_verification.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.management.RuntimeErrorException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class UserService {

    private final WebClient randomUserWebClient;
    private final WebClient nationalizeWebClient;
    private final WebClient genderWebClient;

    private final UserRepository userRepository;


    @Autowired
    public UserService(@Qualifier("randomUserWebClient") WebClient randomUserWebClient, @Qualifier("nationalizeWebClient") WebClient nationalizeWebClient, @Qualifier("genderWebClient") WebClient genderWebClient,
                       UserRepository userRepository) {
        super();
        this.randomUserWebClient = randomUserWebClient;
        this.nationalizeWebClient = nationalizeWebClient;
        this.genderWebClient = genderWebClient;
        this.userRepository = userRepository;
    }

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);


    public List<UnverifiedUserDTO> getRandomUser(Integer size) {

        RandomUserDTO randomUserData = null;
        List<UnverifiedUserDTO> unverifiedUsersList = new ArrayList<UnverifiedUserDTO>();

        Mono<String> responseMono = randomUserWebClient
                .get()
                .uri("/?results=" + size)
                .retrieve()
                .bodyToMono(String.class);

        String randomUser = responseMono.block();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            RandomUserDTO randomUserDTO = objectMapper.readValue(randomUser, RandomUserDTO.class);

            // Assuming there's only one result in the "results" array
            RandomUserDTO.Result result = randomUserDTO.getResults().get(0);
            randomUserData = randomUserDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (var i = 0; i < size; i++) {
            Result newRandomUser = randomUserData.getResults().get(i);
            UnverifiedUserDTO newUnverifiedUser = new UnverifiedUserDTO();
            newUnverifiedUser.setName(newRandomUser.getFullName());
            newUnverifiedUser.setAge(newRandomUser.getAge());
            newUnverifiedUser.setGender(newRandomUser.getGender());
            newUnverifiedUser.setNationality(newRandomUser.getNationality());
            try {
                newUnverifiedUser.setDob(newRandomUser.getDobAsDate());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            unverifiedUsersList.add(newUnverifiedUser);
        }
        return unverifiedUsersList;

    }

    public CompletableFuture<NationalityResponse> getNationalityAsync(String name) {


        return CompletableFuture.supplyAsync(() -> {
            // Use webClientForApi2 to make API calls to the second API
            String nationality = nationalizeWebClient
                    .get()
                    .uri("/?name=" + name.split(" ")[0])
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Block to get the result synchronously
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(nationality);
            try {
                NationalityResponse nationalityResponse = objectMapper.readValue(nationality, NationalityResponse.class);
                return nationalityResponse;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeErrorException(null);
            }
        }, executorService);
    }

    public CompletableFuture<ExternalGenderDTO> getGenderAsync(String name) {
        return CompletableFuture.supplyAsync(() -> {
            // Use webClientForApi3 to make API calls to the third API
            String gender = genderWebClient
                    .get()
                    .uri("/?name=" + name.split(" ")[0])
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Block to get the result synchronously

            ObjectMapper objectMapper = new ObjectMapper();

            ExternalGenderDTO genderResponse = null;

            try {
                ExternalGenderDTO genderDTO = objectMapper.readValue(gender, ExternalGenderDTO.class);
                genderResponse = genderDTO;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return genderResponse;
        }, executorService);
    }


    public User verifyUser(UnverifiedUserDTO userToBeVerified, CompletableFuture<NationalityResponse> nationality, CompletableFuture<ExternalGenderDTO> gender) {
        NationalityResponse nationalityResponse = nationality.join();
        ExternalGenderDTO genderToBeVerified = gender.join();
        CountryDTO country = CountryDTO.from(userToBeVerified.getNationality());


        if (nationalityResponse.contains(country) && userToBeVerified.getGender().equals(genderToBeVerified.getGender())) {
            User user = new User(userToBeVerified.getName(), userToBeVerified.getAge(), userToBeVerified.getGender(), userToBeVerified.getDob(), userToBeVerified.getNationality(), "VERIFIED");
            return user;
        } else {
            User user = new User(userToBeVerified.getName(), userToBeVerified.getAge(), userToBeVerified.getGender(), userToBeVerified.getDob(), userToBeVerified.getNationality(), "TO_BE_VERIFIED");
            return user;
        }


    }


    public void saveAllUsers(List<User> usersList) {
        userRepository.saveAll(usersList);
    }

    public UsersGetResponse getUsers(UserGetQuery userGetQuery) {

//		validateParameters(userGetQuery.getSortType(), userGetQuery.getSortOrder(), userGetQuery.getLimit(), userGetQuery.getOffset());

        // TODO Auto-generated method stub
        Page<User> page = userRepository.findAll(PageRequest.of(userGetQuery.getOffset(), userGetQuery.getLimit()));

        var users = new ArrayList<>(page.toList());

        if (userGetQuery.getSortType().equals("age")) {
            users.sort(new AgeComparator(userGetQuery));
        } else if (userGetQuery.getSortType().equals("name")) {
            users.sort(new NameComparator(userGetQuery));
        } else {
            throw new IllegalStateException();
        }

        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotal(userRepository.count());
        pageInfo.setHasNextPage((userGetQuery.getOffset() + 1) * userGetQuery.getLimit() < pageInfo.getTotal());
        pageInfo.setHasPreviousPage(userGetQuery.getOffset() > 0);


        return new UsersGetResponse(users, pageInfo);

    }

}
