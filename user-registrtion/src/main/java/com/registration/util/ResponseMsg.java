package com.registration.util;

public interface ResponseMsg {
	static final String EMAIL_FOUND = "Email address is already taken";
	static final String CONTACT_FOUND = "Contact number is already taken";
	static final String ALREADY_FOUND = "Details already found";

	static final String NOT_FOUND = "No result found";
	static final String USER_NOT_FOUND = "No user found";
	static final String BOOK_NOT_FOUND = "No book found";
	static final String COURSE_NOT_FOUND = "No course found";
	static final String CARD_NOT_FOUND = "No card found";

	static final String INCORRECT_PARAMETER = "Incorrect parameter to search";
	static final Object INCORRECT_URI = "Please check the URL or the parameter.";

	static final String INTERNAL_ERROR = "Unable to perform task due to internal error."
			+ " It may cause due to inappropriate URI, pathvariable request parameter or request body please check them.";
	static final String FORBIDDEN_ERROR = "You are not allowed to perform this task";
	static final String UNAUTHORIZE = "You are not an authentic user please connect with admin to register";
	static final String EXPIRE = "Please try to re-login either session is expierd or token has issue.";
	
	
	static final String CREATED = "Created Successfully";
	static final String DELETED = "Deleted Successfully";
	static final String FETCHED = "Result get successfully";
	static final String UPDATED = "Updated successfully";

	static final String SUCCESS = "Task Successfully done";
	
	static final String INVALID_ARGUMENT = "Invalid argument provided";
}
