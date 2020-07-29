package com.excilys.java.cdb.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.java.cdb.dto.UserDTO;
import com.excilys.java.cdb.dto.mappers.UserMapper;
import com.excilys.java.cdb.service.UserService;

@Controller
@RequestMapping("/Register")
public class RegisterController {

    private static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public RegisterController (UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showInfo() {
        return "register";
    }

    @PostMapping
    public ModelAndView registerUser(UserDTO userDTO, String confirm) {
        logger.info(userDTO.toString());
        logger.info(confirm);
        if (!userDTO.getUsername().isEmpty() && !userDTO.getPassword().isEmpty() && userDTO.getPassword().equals(confirm)) {
            userDTO.setPassword(passwordEncoder.encode(confirm));

            userService.createUser(UserMapper.mapDtoToUser(userDTO));
        }
        return new ModelAndView("redirect:/ListComputers");
    }
}