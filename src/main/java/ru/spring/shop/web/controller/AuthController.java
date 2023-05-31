package ru.spring.shop.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.api.security.dto.ConfirmationTokenDto;
import ru.api.security.dto.UserDto;
import ru.spring.shop.service.ConfirmationService;
import ru.spring.shop.service.UserService;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final ConfirmationService confirmationService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login-form";
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/registration-form";
    }

    @PostMapping("/register")
    public String handleRegistrationForm(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/registration-form";
        }
        final String username = userDto.getUsername();
        try {
            userService.findUserDtoByName(username);
            model.addAttribute("userDto", userDto);
            model.addAttribute("registrationError", "Username: " + username + " already exists");
            return "auth/registration-form";
        } catch (NoSuchElementException ignored) {
            userService.register(userDto);
            confirmationService.createToken(username);
            model.addAttribute("username", username);
            return "auth/registration-confirmation";
        }
    }

    @GetMapping("/confirm_registration")
    public String confirmationForm(@RequestParam(name = "username") String username, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("confirmationToken", new ConfirmationTokenDto());
        return "auth/confirmation-form";
    }

    @PostMapping("/confirm_registration")
    public String handleConfirmationForm(@Valid ConfirmationTokenDto tokenDto, @RequestParam(name = "username") String username, BindingResult bindingResult, Model model) {
        model.addAttribute("username", username);
        if (bindingResult.hasErrors()) {
            return "auth/confirmation-form";
        }
        try {
            UserDto userDto = userService.findUserDtoByName(username);
            try {
                if (confirmationService.confirmRegistration(tokenDto.getToken(), userDto.getUsername())) {
                    return "auth/registration-success";
                } else {
                    model.addAttribute("confirmationError", "Invalid confirmation code. Try to enter code again.");
                    return "auth/registration-denied";
                }
            } catch (NoSuchElementException noSuchElementException) {
                noSuchElementException.printStackTrace();
                model.addAttribute("confirmationError", "Invalid confirmation code. Try to enter code again.");
                return "auth/registration-denied";
            }
        } catch (UsernameNotFoundException usernameNotFoundException) {
            usernameNotFoundException.printStackTrace();
            model.addAttribute("confirmationError", usernameNotFoundException.getMessage() + " Try to register again.");
            return "auth/registration-denied";
        }
    }

    //todo добавить метод обработки кода подтверждения
}
