package ru.spring.shop.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.api.security.UserDto;
import ru.spring.shop.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login-form";
    }

    @GetMapping("/registration")
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
            userService.findByName(username);
            model.addAttribute("userDto", userDto);
            model.addAttribute("registrationError", "Username already exists");
            return "auth/registration-form";
        } catch (UsernameNotFoundException ignored) {

        }
        userService.register(userDto);
        model.addAttribute("username", username);
        return "auth/registration-confirmation";
    }

    //todo добавить метод обработки кода подтверждения
}
