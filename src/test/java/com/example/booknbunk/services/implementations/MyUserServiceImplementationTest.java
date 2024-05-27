package com.example.booknbunk.services.implementations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.booknbunk.models.User;
import com.example.booknbunk.repositories.PasswordResetTokenRepository;
import com.example.booknbunk.repositories.RoleRepository;
import com.example.booknbunk.repositories.UserRepository;

class MyUserServiceImplementationTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @InjectMocks
    private MyUserServiceImplementation userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllUserPagesWithSearchTest() {
        String search = "test";
        Pageable pageable = mock(Pageable.class);
        Page<User> page = mock(Page.class);
        when(userRepository.findByUsernameContainsIgnoreCase(search, pageable)).thenReturn(page);

        Page<User> result = userService.findAllUserPagesWithSearch(search, pageable);

        assertEquals(page, result);
    }

    @Test
    public void findUserByIdTest() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        when(userRepository.getReferenceById(userId)).thenReturn(user);

        User result = userService.findUserById(userId);

        assertEquals(user, result);
        verify(userRepository, times(1)).getReferenceById(userId);
    }

    @Test
    void findUserByEmailTest() {
        String email = "test@example.com";
        User user = new User();
        user.setUsername(email);

        when(userRepository.getUserByUsername(email)).thenReturn(user);

        User result = userService.findUserByEmail(email);

        assertEquals(user, result);

        verify(userRepository, times(1)).getUserByUsername(email);
    }


}