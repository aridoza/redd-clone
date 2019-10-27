package com.ga.repository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ga.dao.UserProfileDao;
import com.ga.entity.UserProfile;

public class UserProfileDaoStub implements UserProfileDao {

    @Override
    public UserProfile createUserProfile(String username, UserProfile newProfile) {        
        UserProfile userProfile = new UserProfile();
        
        userProfile.setAdditionalEmail("test@testmail.com");
        userProfile.setAddress("123 Test Address");
        userProfile.setMobile("(123) 456-7890");

        return userProfile;
    }

    @Override
    public UserProfile getUserProfile(String username) {
    	if (username != "") {    		
    		UserProfile userProfile = new UserProfile();
    		
    		userProfile.setAdditionalEmail("test@testmail.com");
    		userProfile.setAddress("123 Test Address");
    		userProfile.setMobile("(123) 456-7890");
    		
    		return userProfile;
    	}
    	throw new UsernameNotFoundException("Profile doesn't exist");
    }

}