package com.mini_assignment.user_verification.validator;

public class NumericValidator implements Validator {
	
	private static NumericValidator numericValidator;
	
	private NumericValidator() {
		
	}
	
	public static NumericValidator getInstance() {
		if (numericValidator == null) {
			numericValidator = new NumericValidator();
		}
		return numericValidator;
	}

	@Override
    public boolean validate(String input) {
      
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false; 
        }
    }

}
