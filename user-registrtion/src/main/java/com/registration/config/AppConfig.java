package com.registration.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.registration.entity.User;
import com.registration.payload.request.UserRequest;

@Configuration
@EnableScheduling
public class AppConfig {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true)
				.setFieldMatchingEnabled(true);
		modelMapper.createTypeMap(UserRequest.class, User.class).addMappings(mapper -> {
			mapper.skip(User::setUserId);
			mapper.skip(User::setPwdUpdatedOn);
			mapper.skip(User::setDateOfCreation);
			mapper.skip(User::setDateOfUpdate);
		});
		return modelMapper;
	}
}