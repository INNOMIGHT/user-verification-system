package com.mini_assignment.user_verification.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mini_assignment.user_verification.validator.Validator;
import com.mini_assignment.user_verification.validator.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mini_assignment.user_verification.dto.ErrorResponse;
import com.mini_assignment.user_verification.dto.ExternalGenderDTO;
import com.mini_assignment.user_verification.dto.NationalityResponse;
import com.mini_assignment.user_verification.dto.SizeDTO;
import com.mini_assignment.user_verification.dto.UnverifiedUserDTO;
import com.mini_assignment.user_verification.dto.UserGetQuery;
import com.mini_assignment.user_verification.dto.UsersGetResponse;
import com.mini_assignment.user_verification.entity.User;
import com.mini_assignment.user_verification.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<List<User>> getRandomUser(@RequestBody SizeDTO size) {
        List<UnverifiedUserDTO> randomUsersList = userService.getRandomUser(size.getSize());
        List<User> usersVerified = new ArrayList<>();

        for(var randomUser: randomUsersList) {

        	CompletableFuture<NationalityResponse> externalNationalities = userService.getNationalityAsync(randomUser.getName());
        	CompletableFuture<ExternalGenderDTO> externalGender = userService.getGenderAsync(randomUser.getName());
        	usersVerified.add(userService.verifyUser(randomUser, externalNationalities, externalGender));

        }
        userService.saveAllUsers(usersVerified);
        return ResponseEntity.ok(usersVerified);
    }

	@GetMapping
    public ResponseEntity<UsersGetResponse> getUsers(@ModelAttribute UserGetQuery userGetQuery) {
        validateParameters(userGetQuery);
        UsersGetResponse users = userService.getUsers(userGetQuery);
        return ResponseEntity.ok(users);
    }

    public void validateParameters(UserGetQuery userGetQuery) {
        Validator sortTypeValidator = ValidatorFactory.createValidator("name"); // Use EnglishAlphabetsValidator for sortType
        Validator sortOrderValidator = ValidatorFactory.createValidator("name"); // Use EnglishAlphabetsValidator for sortOrder
        Validator limitValidator = ValidatorFactory.createValidator("limit"); // Use NumericValidator for limit
        Validator offsetValidator = ValidatorFactory.createValidator("offset"); // Use NumericValidator for offset

        if (!sortTypeValidator.validate(userGetQuery.getSortType())) {
            throw new IllegalArgumentException("Invalid sortType parameter");
        }

        if (!sortOrderValidator.validate(userGetQuery.getSortOrder())) {
            throw new IllegalArgumentException("Invalid sortOrder parameter");
        }

        if (!limitValidator.validate(String.valueOf((int) userGetQuery.getLimit()))) {
            throw new IllegalArgumentException("Invalid limit parameter");
        }

        if (!offsetValidator.validate(String.valueOf((int) userGetQuery.getOffset()))) {
            throw new IllegalArgumentException("Invalid offset parameter");
        }
    }

}
