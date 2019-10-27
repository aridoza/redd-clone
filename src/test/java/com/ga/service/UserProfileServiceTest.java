package com.ga.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ga.entity.UserProfile;
import com.ga.repository.UserProfileDaoStub;

public class UserProfileServiceTest {

    private UserProfileServiceImpl userProfileService;
    
    private UserProfile userProfile;
    
    private UserProfile newProfile;
    
    // https://www.baeldung.com/junit-assert-exception
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    
    @Before
    public void initializeUserProfile() {
       userProfileService = new UserProfileServiceImpl(new UserProfileDaoStub());
       
       userProfile = new UserProfile();
       
       userProfile.setAdditionalEmail("test@testmail.com");
       userProfile.setAddress("123 Test Address");
       userProfile.setMobile("(123) 456-7890");
    }

    @Test
    public void createUserProfile_AddsUserProfile_Success() {
    	newProfile = userProfileService.createUserProfile("testuser", userProfile);
    	
        assertNotNull(newProfile);
        assertEquals(newProfile.getAdditionalEmail(), userProfile.getAdditionalEmail());
	    assertEquals(newProfile.getAddress(), userProfile.getAddress());
	    assertEquals(newProfile.getMobile(), userProfile.getMobile());
    }
    
    @Test
    public void getUserProfile_ReturnsUserProfile_Success() {
    	newProfile = userProfileService.getUserProfile("testuser");
    	
    	assertNotNull(newProfile);
        assertEquals(newProfile.getAdditionalEmail(), userProfile.getAdditionalEmail());
	    assertEquals(newProfile.getAddress(), userProfile.getAddress());
	    assertEquals(newProfile.getMobile(), userProfile.getMobile());
    }
    
    @Test(expected = UsernameNotFoundException.class)
    public void getUserProfile_ThrowsUsernameNotFoundException_Failure() {
    	newProfile = userProfileService.getUserProfile("");
    	
    	assertNull(newProfile);
        exceptionRule.expect(UsernameNotFoundException.class);
        exceptionRule.expectMessage("Profile doesn't exist");
    }
}