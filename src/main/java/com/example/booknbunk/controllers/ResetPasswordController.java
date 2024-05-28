package com.example.booknbunk.controllers;

import com.example.booknbunk.models.PasswordResetToken;
import com.example.booknbunk.models.User;
import com.example.booknbunk.repositories.PasswordResetTokenRepository;
import com.example.booknbunk.services.interfaces.EmailService;
import com.example.booknbunk.services.interfaces.MyUserService;
import com.example.booknbunk.utils.PasswordResetRequest;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/forgotPassword")
public class ResetPasswordController {

    private final MyUserService myUserService;

    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    public ResetPasswordController(MyUserService myUserService, PasswordResetTokenRepository tokenRepository, EmailService emailService) {
        this.myUserService = myUserService;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @GetMapping("/forgotPassword")
    public String showForgotPasswordPage() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPasswordForm(@RequestParam ("email") String email, Model model) throws MessagingException {
        User user = myUserService.findUserByEmail(email);
        if (user == null) {
            model.addAttribute("error", "Email address not found");
            return "forgotPassword";

        }

        String token = UUID.randomUUID().toString();
        myUserService.createPasswordResetTokenForUser(user, token);

        String resetUrl = "http://localhost:8080/forgotPassword/resetPassword?token=" + token;

        String passwordResetBody = "\n" +
                "Can't Get In To Your Account?\n" +
                "Happens to the best of us. Don't worry, we got you fam. If you didn't request this email, you can safely ignore it.\n" +
                "\n" +
                "Your password reset link will expire in 24 hour(s). If you need to, you can request another in 24 hour(s). \n\n";
        String passWordResetSubject = "Book'n'Bunk Password Reset Request";
        emailService.sendEmail(email, passWordResetSubject, passwordResetBody + resetUrl);

        model.addAttribute("successMessage","A Password reset link has been sent your email");
        return "forgotPassword";
    }
    @GetMapping("/resetPassword")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        PasswordResetToken resetToken = tokenRepository.findPasswordResetTokenByToken(token);
        if (resetToken == null || resetToken.getExpirationDateTime().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Invalid or expired token.");
            return "resetPassword";
        }

        model.addAttribute("token", token);
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String handlePasswordReset(@RequestParam("token") String token, @RequestParam("password") String newPassword, Model model) {
        PasswordResetToken resetToken = tokenRepository.findPasswordResetTokenByToken(token);
        if (resetToken == null || resetToken.getExpirationDateTime().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Invalid or expired token.");
            return "resetPassword";
        }

        User user = resetToken.getUser();
        myUserService.updatePassword(user,newPassword);

        model.addAttribute("message", "Password reset successful.");
        return "resetPassword";
    }
}


