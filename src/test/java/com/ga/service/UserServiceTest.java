package com.ga.service;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.ga.config.JwtUtil;
import com.ga.dao.UserDao;
import com.ga.entity.User;

public class UserServiceTest {

    @Mock
    UserDao userDao;
    
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @InjectMocks
    private User user;
    
    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

}