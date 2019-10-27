package com.ga.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.ga.entity.UserProfile;
import com.ga.service.UserProfileServiceStub;

public class UserProfileControllerTest {

    private UserProfileController userProfileController;
    
    private UserProfile userProfile;
    
    @Before
    public void initializeUserProfileController() {
        userProfileController = new UserProfileController();
        userProfileController.setUserProfileService(new UserProfileServiceStub());
        
        userProfile = new UserProfile();
        
    	userProfile.setAdditionalEmail("test@testmail.com");
    	userProfile.setAddress("123 Test Address");
    	userProfile.setMobile("(123) 456-7890");
    }
    
    @Test
    public void createUserProfile_SaveUserProfile_Success() {
        UserProfile newProfile = userProfileController.createUserProfile("testuser", userProfile);
        
        assertNotNull(newProfile);
    	assertEquals(newProfile.getAdditionalEmail(), userProfile.getAdditionalEmail());
    	assertEquals(newProfile.getAddress(), userProfile.getAddress());
    	assertEquals(newProfile.getMobile(), userProfile.getMobile());
    }
    
    @Test
    public void getUserProfile_GetUserProfile_Success() {
    	UserProfile newProfile = userProfileController.getUserProfile("testuser");
    	
    	assertNotNull(newProfile);
    	assertEquals(newProfile.getAdditionalEmail(), userProfile.getAdditionalEmail());
    	assertEquals(newProfile.getAddress(), userProfile.getAddress());
    	assertEquals(newProfile.getMobile(), userProfile.getMobile());
    }
    
}