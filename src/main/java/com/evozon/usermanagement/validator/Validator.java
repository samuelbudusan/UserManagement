package com.evozon.usermanagement.validator;

public interface Validator<T> {
	
	public String validate(T value);
}
