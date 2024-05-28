package com.example.booknbunk.services.implementations;

import com.example.booknbunk.models.PasswordResetToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
import java.util.*;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.booknbunk.models.Role;

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
        String email = "test@gmail.com";
        User user = new User();
        user.setUsername(email);

        when(userRepository.getUserByUsername(email)).thenReturn(user);

        User result = userService.findUserByEmail(email);

        assertEquals(user, result);

        verify(userRepository, times(1)).getUserByUsername(email);
    }
    @Test
    public void addUserTest() {
        User user = new User();
        user.setPassword("hejsan123");
        List<UUID> roleIds = Arrays.asList(UUID.randomUUID());

        Role role = new Role();
        role.setId(roleIds.get(0));

        when(roleRepository.findAllById(roleIds)).thenReturn(Collections.singletonList(role));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        MyUserServiceImplementation userServiceWithEncoder = new MyUserServiceImplementation(roleRepository, userRepository, passwordResetTokenRepository);

        userServiceWithEncoder.addUser(user, roleIds);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(1, savedUser.getRoles().size());
        assertTrue(savedUser.getRoles().contains(role));
        assertTrue(passwordEncoder.matches("hejsan123", savedUser.getPassword()));
        assertTrue(savedUser.isEnabled());
    }

    @Test
    public void updateUserTest(){
        User user = new User();
        user.setPassword("hejsan123");
        List<UUID> roleIds = Arrays.asList(UUID.randomUUID());

        Role role = new Role();
        role.setId(roleIds.get(0));

        when(roleRepository.findAllById(roleIds)).thenReturn(Collections.singletonList(role));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        MyUserServiceImplementation userServiceWithEncoder = new MyUserServiceImplementation(roleRepository,userRepository, passwordResetTokenRepository);

        userServiceWithEncoder.updateUser(user, roleIds);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(1, savedUser.getRoles().size());
        assertTrue(savedUser.getRoles().contains(role));
        assertTrue(passwordEncoder.matches("hejsan123", savedUser.getPassword()));
    }
    @Test
    public void updatePasswordTest(){
        User user = new User();
        String newPassword = "helloWorld123";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        MyUserServiceImplementation userServiceWithEncoder = new MyUserServiceImplementation(roleRepository, userRepository, passwordResetTokenRepository);

        userServiceWithEncoder.updatePassword(user, newPassword);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertTrue(passwordEncoder.matches(newPassword, savedUser.getPassword()));
    }

    @Test
    public void createPasswordResetTokenForUserTest(){
        User user = new User();
        String token = "resetToken";
        LocalDateTime expiry = LocalDateTime.now().plusHours(24);

        userService.createPasswordResetTokenForUser(user, token);

        ArgumentCaptor<PasswordResetToken> tokenCaptor = ArgumentCaptor.forClass(PasswordResetToken.class);
        verify(passwordResetTokenRepository).save(tokenCaptor.capture());

        PasswordResetToken savedToken = tokenCaptor.getValue();
        assertNotNull(savedToken);
        assertEquals(token, savedToken.getToken());
        assertEquals(user, savedToken.getUser());
        //Den tolerar en sekunds skillnad iom. att testet tar lite tid att utföras
        assertTrue(Duration.between(expiry, savedToken.getExpirationDateTime()).get(ChronoUnit.SECONDS) <= 1);
    }

    @Test
    public void getPasswordResetTokenTest(){
        String token = "resetToken";
        PasswordResetToken expectedToken = new PasswordResetToken();
        expectedToken.setToken(token);
        when(passwordResetTokenRepository.findPasswordResetTokenByToken(token)).thenReturn(expectedToken);

        PasswordResetToken actualToken = userService.getPasswordResetToken(token);

        assertEquals(expectedToken, actualToken);
    }
}