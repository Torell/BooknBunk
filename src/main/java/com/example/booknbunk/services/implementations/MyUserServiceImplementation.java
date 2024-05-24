package com.example.booknbunk.services.implementations;

import com.example.booknbunk.models.PasswordResetToken;
import com.example.booknbunk.models.Role;
import com.example.booknbunk.models.User;
import com.example.booknbunk.repositories.PasswordResetTokenRepository;
import com.example.booknbunk.repositories.RoleRepository;
import com.example.booknbunk.repositories.UserRepository;
import com.example.booknbunk.services.interfaces.MyUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MyUserServiceImplementation implements MyUserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public MyUserServiceImplementation(RoleRepository roleRepository, UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public void addUser(User user, List<UUID> roles) {

        Collection<Role> rolesToAdd = new ArrayList<>();
        roleRepository.findAllById(roles).forEach(rolesToAdd::add);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(user.getPassword());

        user.setRoles(rolesToAdd);
        user.setPassword(hash);
        user.setEnabled(true);

        userRepository.save(user);
    }


    @Override
    public Page<User> findAllUserPagesWithSearch(String search, Pageable pageable) {
        return userRepository.findByUsernameContainsIgnoreCase(search,pageable);
    }

    @Override
    public User findUserById(UUID id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public void updateUser(User user, List<UUID> roles) {

        Collection<Role> rolesToAdd = new ArrayList<>();
        roleRepository.findAllById(roles).forEach(rolesToAdd::add);
        if (!isBCryptHashed(user.getPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash = encoder.encode(user.getPassword());
            user.setPassword(hash);
        }
        user.setRoles(rolesToAdd);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(User user, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(password);
        user.setPassword(hash);
        userRepository.save(user);
    }

    private boolean isBCryptHashed(String password) {
        // Check if the password starts with a BCrypt prefix
        return password != null && password.matches("^\\$2[ayb]\\$.{56}$");
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(token,user, LocalDateTime.now().plusHours(24));
        passwordResetTokenRepository.save(passwordResetToken);
    }


    @Override
    public User findUserByEmail(String email) {
        return userRepository.getUserByUsername(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findPasswordResetTokenByToken(token);
    }


}
