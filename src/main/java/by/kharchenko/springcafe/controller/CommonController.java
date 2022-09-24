package by.kharchenko.springcafe.controller;


import by.kharchenko.springcafe.model.entity.Role;
import by.kharchenko.springcafe.model.entity.User;
import by.kharchenko.springcafe.model.exception.ServiceException;
import by.kharchenko.springcafe.model.service.UserService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.Optional;

import static by.kharchenko.springcafe.controller.PagePath.*;

@Controller
public class CommonController {
    private UserService userService;

    @Autowired(required = true)
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-in")
    public String goSingInPage() {
        System.out.println("Sign-in Get");
        return "common/sign-in";
    }

    @GetMapping("/registration")
    public String goRegistrationPage(@ModelAttribute("user") User user) {
        return "common/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid User user, BindingResult result, Model model, HttpSession session) throws ServletException {
        if (result.hasErrors()) {
            return REGISTRATION_PAGE;
        }
        try {
            boolean isAdd = userService.add(user);
            if (!isAdd) {
                model.addAttribute("login_msg", "This login is already exists");
                return REGISTRATION_PAGE;
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        session.setAttribute("msg", "Successful registration");
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String goHomePage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        session.setAttribute("main_page", "common/home");
        return "common/home";
    }

    @GetMapping("/language")
    public String goLanguagePage() {
        return LANGUAGE_PAGE;
    }

    @GetMapping("/language_change")
    public String ChangeLanguagePage(HttpSession session) {
        String main = (String) session.getAttribute("main_page");
        if (main.equals(HOME_PAGE)) {
            return "redirect:/home";
        }
        BigInteger id = (BigInteger) session.getAttribute("id_user");
        if (main.equals(MAIN_CLIENT_PAGE)) {
            return "redirect:/client/" + id;
        } else if (main.equals(MAIN_ADMIN_PAGE)) {
            return "redirect:/administrator/" + id;
        }
        return "redirect:/home";
    }

    @PostMapping("/sign-in")
    public String signIn(HttpServletRequest request) throws ServletException {
        System.out.println("Sign-in Post");
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            Optional<Role> optionalRole = userService.authenticate(login, password);
            if (optionalRole.isPresent()) {
                session.setAttribute("role", optionalRole.get());
                Optional<BigInteger> id = userService.idByLogin(login);
                id.ifPresent(bigInteger -> session.setAttribute("id_user", bigInteger));
                Role role = optionalRole.get();
                switch (role) {
                    case CLIENT -> {
                        session.setAttribute("main_page", MAIN_CLIENT_PAGE);
                        return "redirect:/client/" + id.get().toString();
                    }
                    case ADMINISTRATOR -> {
                        session.setAttribute("main_page", MAIN_ADMIN_PAGE);
                        return "redirect:" + MAIN_ADMIN_PAGE;
                    }
                    default -> {
                        return "redirect:" + SIGN_IN_PAGE;
                    }
                }
            } else {
                return "redirect:" + SIGN_IN_PAGE;
            }

        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/exit")
    public String goExitPage(){
        return "common/exit";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/home";
    }

    @GetMapping("/main-page")
    public String goMainPage(HttpSession session){
        BigInteger id = (BigInteger) session.getAttribute("id_user");
        String main = (String) session.getAttribute("main_page");
        if (main.equals(MAIN_CLIENT_PAGE)) {
            return "redirect:/client/" + id;
        } else if (main.equals(MAIN_ADMIN_PAGE)) {
            return "redirect:/administrator/" + id;
        }
        return null;
    }


    @ModelAttribute
    public void msg(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String msg = (String) session.getAttribute("msg");
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.setAttribute("msg", null);
        }
    }
}
