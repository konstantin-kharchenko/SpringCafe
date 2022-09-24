package by.kharchenko.springcafe.controller;

import by.kharchenko.springcafe.model.entity.Order;
import by.kharchenko.springcafe.model.entity.Product;
import by.kharchenko.springcafe.model.entity.User;
import by.kharchenko.springcafe.model.exception.ServiceException;
import by.kharchenko.springcafe.model.service.ClientService;
import by.kharchenko.springcafe.model.service.OrderService;
import by.kharchenko.springcafe.model.service.ProductService;
import by.kharchenko.springcafe.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static by.kharchenko.springcafe.controller.PagePath.MAIN_CLIENT_PAGE;


/*
* This controller for client
*/
@Controller
@RequestMapping("/client")
public class ClientController {
    private UserService userService;
    private ClientService clientService;
    private OrderService orderService;
    private ProductService productService;

    @Autowired(required = true)
    public void setUserService(UserService userService, ClientService clientService, OrderService orderService, ProductService productService) {
        this.userService = userService;
        this.clientService = clientService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String client(@PathVariable("id") BigInteger id, Model model) throws ServletException {
        try {
            User user = userService.findById(id);
            model.addAttribute("user", user);
            List<Order> orderList = orderService.findQuickToReceive(user);
            model.addAttribute("orders", orderList);
            List<Product> newProducts = productService.findNew();
            model.addAttribute("newProducts", newProducts);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return MAIN_CLIENT_PAGE;
    }

    @GetMapping("/profile/{id}")
    public String goProfile(@PathVariable("id") BigInteger id, Model model) throws ServletException {
        try {
            User user = userService.findById(id);
            model.addAttribute("user", user);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return "client/profile";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult result, HttpSession session) throws ServletException {
        try {
            if (result.hasErrors()) {
                return "client/profile";
            }
            boolean isUpdate = userService.update(user);
            if (!isUpdate) {
                session.setAttribute("msg", "This login is already exists");
                return "redirect:/client/profile/" + user.getIdUser();
            }
            return "redirect:/client/" + user.getIdUser();
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
    @PostMapping("/change_photo")
    public String changePhoto(@RequestParam("file") MultipartFile file, HttpSession session) throws ServletException {
        BigInteger id = (BigInteger) session.getAttribute("id_user");
        try {
            boolean isAdd = clientService.updatePhoto(file, id);
            if (!isAdd) {
                session.setAttribute("msg", "invalid photo file");
            }
            return "redirect:/client/profile/" + id;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/add-client-account")
    public String goClientAccount() {
        return "client/add_account";
    }

    @PostMapping("/add-client-account")
    public String addClientAccount(@RequestParam("account") BigDecimal clientAccount, HttpSession session, Model model) {
        BigInteger idUser = (BigInteger) session.getAttribute("id_user");
        boolean match = clientService.addClientAccount(clientAccount, idUser);
        if (match) {
            return "redirect:/client/" + idUser;
        } else {
            model.addAttribute("msg", "Incorrect Client Account");
            return "client/add_account";
        }

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
