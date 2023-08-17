package com.registration.util;

public class Constants {
	public static final String USER_TAG_DESCRIPTION = "User resources that provides access to availabe user operations";
	public static final String BOOK_TAG_DESCRIPTION = "Book resources that provides access to availabe book operations";
	public static final String CARD_TAG_DESCRIPTION = "Card resources that provides access to availabe card operations";
	public static final String COURSE_TAG_DESCRIPTION = "Course resources that provides access to availabe course operations";
	public static final String GENERAL_TAG_DESCRIPTION = "General resources that provides access to availabe linking operations in between user and its association entiies";

	public static final String VALID_BOOK_TITLE = "Please provide a valid book title";
	public static final String VALID_BOOK_NAME = "Please provide a valid book name";
	public static final String VALID_AUTHOR = "Please provide a valid author name";

	public static final String VALID_USER_NAME = "Please provide a valid user name.";
	public static final String VALID_PASSWORD = "Please provide a valid password.";
	public static final String VALID_EMAIL = "Please provide a valid email address.";
	public static final String VALID_CONTACT = "Please provide a valid conatact number.";

	public static final String VALID_COURE_NAME = "Please provide a valid course name.";
	public static final String VALID_ABBREVIATION = "Please provide a valid abbreviation.";

	public static final String VALID_LIBRARY_NAME = "Please provide a valid library name.";

	public static final String VALID_SORTBY = "Invalid sort by value";
	public static final String VALID_ORDER = "Invalid order request";

	public static final String VALID_ROLES = "Provided roles are invalid, Only (ADMIN, MANAGER, USER) or without any role are allowed";

	public static final String VALID_STATUS = "Invalid status";
	
	public static final String REQUIRE_BOOK_TITLE = "The book title is required.";
	public static final String REQUIRE_BOOK_NAME = "The book name is required.";
	public static final String REQUIRE_AUTHOR_NAME = "The author name is required.";

	public static final String REQUIRE_USER_NAME = "The user name is required.";
	public static final String REQUIRE_PASSWORD = "Password is required.";
	public static final String REQUIRE_EMAIL = "Email address is required.";
	public static final String REQUIRE_CONTACT = "Contact number is required.";

	public static final String REQUIRE_COURE_NAME = "The course name is required.";
	public static final String REQUIRE_ABBREVIATION = "The course abbreviation is required.";
	public static final String REQUIRE_MODULE = "The course module is required.";
	public static final String REQUIRE_FEE = "The course fee is required.";

	public static final String REQUIRE_LIBRARY_NAME = "The library name is required.";

	public static final String REQUIRE_STATUS = "Please provide status";
	
	public static final String NAME_REGEXP = "^[a-zA-Z \\\\\\\\s]+";
	public static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	public static final String EMAIL_REGEXP = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";
	public static final String CONTACT_REGEXP = "(^$|[0-9]{10})";
	public static final String SORTBY_REGEXP = "^(contact|CONTACT|name|NAME|email|EMAIL|Id)$";
	public static final String ORDER_REGEXP = "^(ASC|asc|DSC|dsc)$";
	public static final String ROLES_REGEXP = "^(ADMIN|APPRAISER|PO)$";//"^(ADMIN|APPRAISER|PO)(,(APPRAISER|PO))*))$"; // "^(PO|ADMIN|APPRAISER)?(, ?(PO|ADMIN|APPRAISER))?((, ?(PO|ADMIN|APPRAISER))?)?$";
	public static final String STATUS_REGEXP = "^(suspend|active)$";
	
	public static final String DUPLICATE_EMAIL = "uk_8eulmsea0khwgg03lj12e7ln7";
	public static final String DUPLICATE_CONTACT = "uk_1rb7cvun0p14nm9hm4h32obdq";
	public static final String DUPLICATE_BOOK_TITLE = "uk_huuyjnwtueqthge3assy2gst5";
	public static final String DUPLICATE_LIBRARY = "uk_25rsi65cpp3uv543sfamdkjj7";
	public static final String DUPLICATE_COURSE_NAME = "UK_9dll001xc2cip6hug6axoab0p";

	public static final String NO_VAlUE = "No value present";
}
