package com.mini_assignment.user_verification.validator;

public class EnglishAlphabetsValidator implements Validator {
	
	private static EnglishAlphabetsValidator englishAlphabetsValidator;
	
	private EnglishAlphabetsValidator() {
		
	}
	
	public static EnglishAlphabetsValidator getInstance() {
		if (englishAlphabetsValidator == null) {
			englishAlphabetsValidator = new EnglishAlphabetsValidator();
		}
		return englishAlphabetsValidator;
	}
	
	@Override
    public boolean validate(String input) {
        // Check if the input contains only English alphabets
        return input.matches("^[a-zA-Z]*$");
    }
	
}
