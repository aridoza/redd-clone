package com.ga.service;

import com.ga.entity.UserProfile;

public class UserProfileServiceStub implements UserProfileService {

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
       UserProfile userProfile = new UserProfile();
       
       userProfile.setAdditionalEmail("test@testmail.com");
       userProfile.setAddress("123 Test Address");
       userProfile.setMobile("(123) 456-7890");
    	return userProfile;
    }
}