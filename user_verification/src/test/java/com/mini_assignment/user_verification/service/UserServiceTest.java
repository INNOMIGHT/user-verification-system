package com.mini_assignment.user_verification.service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mini_assignment.user_verification.dto.UserGetQuery;
import com.mini_assignment.user_verification.dto.UsersGetResponse;
import com.mini_assignment.user_verification.entity.User;
import com.mini_assignment.user_verification.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private UserRepository userRepository;
    private Page<User> page;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        WebClient randomUserWebClient = mock(WebClient.class);
        WebClient nationalizeWebClient = mock(WebClient.class);
        WebClient genderWebClient = mock(WebClient.class);
        userRepository = mock(UserRepository.class);
        page = mock(Page.class);
        userService = new UserService(genderWebClient, randomUserWebClient, nationalizeWebClient, userRepository); // Create an instance of your service
    }

    @ParameterizedTest(name = "Test: Sort Type: {0}, Sort Order: {1}, Limit: {2}, Offset: {3}")
    @CsvSource(value = {
            "age,even,5,0,src/test/java/resources/sort_type_age_order_even/test_users_input.json,src/test/java/resources/sort_type_age_order_even/test_users_output.json",
            "age,odd,5,0,src/test/java/resources/sort_type_age_order_even/test_users_input.json,src/test/java/resources/sort_type_age_sort_order_odd/test_users_output.json",
            "name,even,5,0,src/test/java/resources/sort_type_age_order_even/test_users_input.json,src/test/java/resources/sort_type_name_order_even/test_users_output.json",
            "name,odd,5,0,src/test/java/resources/sort_type_age_order_even/test_users_input.json,src/test/java/resources/sort_type_name_order_odd/test_users_output.json",
    }, delimiter = ',')
    public void itShouldReturnListOfUsersBasedOnTheInputParameters(String sortType, String sortOrder, int limit, int offset, String inputFilePath, String expectedFilePath) throws IOException {
        // GIVEN
        List<User> randomListOfUsers = objectMapper.readValue(new File(inputFilePath), new TypeReference<>() {
        });
        List<User> sortedListOfUsers = objectMapper.readValue(new File(expectedFilePath), new TypeReference<>() {
        });
        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(page.toList()).thenReturn(randomListOfUsers);

        // WHEN
        UsersGetResponse usersList = userService.getUsers(new UserGetQuery(sortType, sortOrder, limit, offset));

        // THEN
        assertEquals(5, usersList.getUsersList().size());
        assertEquals(sortedListOfUsers, usersList.getUsersList());
    }
}

