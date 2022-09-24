package by.kharchenko.springcafe.controller;

import by.kharchenko.springcafe.model.entity.Product;
import by.kharchenko.springcafe.model.exception.ServiceException;
import by.kharchenko.springcafe.model.service.OrderService;
import by.kharchenko.springcafe.model.service.ProductService;
import by.kharchenko.springcafe.util.count_page_saver.CountPageSaver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class BasketController {
    private OrderService orderService;
    private final ProductService productService;

    public BasketController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/add-from-basket/{id}")
    public String addFromBasket(@PathVariable("id") BigInteger id, HttpSession session) throws ServletException {
        try {
            Set<BigInteger> basketProducts = (Set<BigInteger>) session.getAttribute("basketProductsId");
            BigInteger idUser = (BigInteger) session.getAttribute("id_user");
            if (basketProducts == null) {
                return "redirect:/client/" + idUser;
            }
            orderService.addProductsInOrder(id, basketProducts);
            session.setAttribute("basketProductsId", null);
            return "redirect:/client/" + idUser;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

    }

    @GetMapping("/add-from-basket/{id}/{page}")
    public String addFromBasket(@PathVariable("id") BigInteger id, @PathVariable("page") Integer page, HttpSession session) throws ServletException {
        try {
            Set<BigInteger> basketProducts = (Set<BigInteger>) session.getAttribute("basketProductsId");
            if (basketProducts == null) {
                return "redirect:/client/all-orders/" + page;
            }
            orderService.addProductsInOrder(id, basketProducts);
            session.setAttribute("basketProductsId", null);
            return "redirect:/client/all-orders/" + page;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

    }

    @GetMapping("/add-to-basket/{id}")
    public String addToBasket(@PathVariable("id") BigInteger id, HttpSession session) throws ServletException {
        Set<BigInteger> basketProducts = (Set<BigInteger>) session.getAttribute("basketProductsId");
        if (basketProducts == null) {
            basketProducts = new HashSet<>();
        }
        basketProducts.add(id);
        session.setAttribute("basketProductsId", basketProducts);
        BigInteger idUser = (BigInteger) session.getAttribute("id_user");
        return "redirect:/client/" + idUser;

    }

    @GetMapping("/add-to-basket/{id}/{page}")
    public String addToBasket(@PathVariable("id") BigInteger id, @PathVariable("page") Integer page, HttpSession session, Model model) throws ServletException {
        Set<BigInteger> basketProducts = (Set<BigInteger>) session.getAttribute("basketProductsId");
        if (basketProducts == null) {
            basketProducts = new HashSet<>();
        }
        basketProducts.add(id);
        session.setAttribute("basketProductsId", basketProducts);
        Integer pageCount = (int) Math.ceil((1.0 * productService.ordersCount().intValue()) / 10);
        CountPageSaver.page(model, page, pageCount);
        return "redirect:/client/all-products/" + page;

    }

    @GetMapping("/basket")
    public String goBasket(Model model, HttpSession session) throws ServletException {
        try {
            Set<BigInteger> basketProducts = (Set<BigInteger>) session.getAttribute("basketProductsId");
            if (basketProducts == null || basketProducts.size() == 0) {
                model.addAttribute("basketProducts", new ArrayList<Product>());
                return "client/basket";
            }
            List<Product> products = productService.findProductsByIdList(basketProducts.stream().toList());
            model.addAttribute("basketProducts", products);
            return "client/basket";
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/delete-from-basket/{id}")
    public String deleteFromBasket(@PathVariable("id") BigInteger id, HttpSession session) throws ServletException {
        Set<BigInteger> basketProducts = (Set<BigInteger>) session.getAttribute("basketProductsId");
        basketProducts.remove(id);
        session.setAttribute("basketProductsId", basketProducts);
        return "redirect:/client/basket";
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
