package com.registration.payload.request;

import org.springframework.validation.annotation.Validated;

import com.registration.util.Constants;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Validated
public class SearchRequest {
	@Min(0)
	private int pageNumber;

	@Min(1)
	private int size;

	@Nullable
	@Pattern(regexp = Constants.SORTBY_REGEXP, message = Constants.VALID_SORTBY)
	private String sortBy;

	@Nullable
	@Pattern(regexp = Constants.ORDER_REGEXP, message = Constants.VALID_ORDER)
	private String order;

	private String searchBy;
}
