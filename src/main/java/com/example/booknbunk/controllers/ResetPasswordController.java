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

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller
@RequestMapping("/forgotPassword")
public class ResetPasswordController {

    private final MyUserService myUserService;

    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    private final TemplateEngine templateEngine;

    public ResetPasswordController(MyUserService myUserService, PasswordResetTokenRepository tokenRepository, EmailService emailService, TemplateEngine templateEngine) {
        this.myUserService = myUserService;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.templateEngine = templateEngine;
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

        System.out.println("UUUUUUURRRRLLLLL" +resetUrl);

        Context context = new Context();
        context.setVariable("resetUrl", resetUrl);

        String passwordResetBody = templateEngine.process("emailTemplatePassword", context);
        String passWordResetSubject = "Book'n'Bunk Password Reset Request";

        emailService.sendEmailPassword(email, passWordResetSubject, passwordResetBody);

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


