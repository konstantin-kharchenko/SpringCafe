package by.kharchenko.springcafe.controller;

import by.kharchenko.springcafe.model.entity.Client;
import by.kharchenko.springcafe.model.entity.Order;
import by.kharchenko.springcafe.model.exception.ServiceException;
import by.kharchenko.springcafe.model.service.ClientService;
import by.kharchenko.springcafe.model.service.OrderService;
import by.kharchenko.springcafe.util.count_page_saver.CountPageSaver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@Controller
public class OrderController {

    private OrderService orderService;
    private ClientService clientService;

    public OrderController(OrderService orderService, ClientService clientService) {
        this.orderService = orderService;
        this.clientService = clientService;
    }

    @GetMapping("/delete-order-from-main/{id}")
    public String deleteOrderFromMain(@PathVariable("id") BigInteger id, HttpSession session) throws ServletException {
        try {
            orderService.delete(id);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        BigInteger idUser = (BigInteger) session.getAttribute("id_user");
        return "redirect:/client/" + idUser;
    }

    @GetMapping("/delete-order-from-all/{id}")
    public String deleteOrderFromAll(@PathVariable("id") BigInteger id) throws ServletException {
        try {
            orderService.delete(id);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return "redirect:/client/all-orders/1";
    }

    @GetMapping("/create-order")
    public String goCreateOrder(@ModelAttribute("order") Order order) {
        return "client/create_order";
    }

    @PostMapping("/create-order")
    public String createOrder(@ModelAttribute("order") @Valid Order order, BindingResult result, Model model, HttpSession session) throws ServletException {
        try {
            if (result.hasErrors()) {
                return "client/create_order";
            }
            BigInteger id = (BigInteger) session.getAttribute("id_user");
            Client client = clientService.findByUserId(id);
            order.setClient(client);
            orderService.add(order);
            return "redirect:/client/" + id;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/change-order/{id}")
    public String goChangeOrder(@PathVariable("id") BigInteger id, Model model) throws ServletException {
        try {
            model.addAttribute("order", orderService.findById(id));
            return "client/change_order";
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }


    @GetMapping("/change-order/{id}/{page}")
    public String goChangeOrder(@PathVariable("id") BigInteger id, @PathVariable("page") Integer page, Model model) throws ServletException {
        try {
            model.addAttribute("order", orderService.findById(id));
            model.addAttribute("currentPage", page);
            return "client/change_order";
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @PostMapping("/update-order/{id}")
    public String updateOrder(@PathVariable("id") BigInteger id, @ModelAttribute("order") @Valid Order order, BindingResult result, @RequestParam(value = "currentPage", required = false) Integer page, Model model, HttpSession session) throws ServletException {
        try {
            order.setIdOrder(id);
            if (result.hasErrors()) {
                return "client/change_order";
            }
            BigInteger idUser = (BigInteger) session.getAttribute("id_user");
            Client client = clientService.findByUserId(idUser);
            order.setClient(client);
            orderService.update(order);
            if (page != null) {
                return "redirect:/client/all-orders/" + page;
            }
            return "redirect:/client/" + idUser;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/delete-from-order/{idOrder}/{idProduct}")
    public String deleteFromOrder(@PathVariable("idOrder") BigInteger idOrder, @PathVariable("idProduct") BigInteger idProduct, HttpSession session) throws ServletException {
        try {
            orderService.deleteProductFromOrderById(idOrder, idProduct);
            BigInteger idUser = (BigInteger) session.getAttribute("id_user");
            return "redirect:/client/" + idUser;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/delete-from-order/{idOrder}/{idProduct}/{page}")
    public String deleteFromOrder(@PathVariable("idOrder") BigInteger idOrder, @PathVariable("idProduct") BigInteger idProduct, @PathVariable("page") Integer page, HttpSession session) throws ServletException {
        try {
            orderService.deleteProductFromOrderById(idOrder, idProduct);
            return "redirect:/client/all-orders/" + page;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }


    @GetMapping("/all-orders/{page}")
    public String allOrders(@PathVariable("page") Integer page, Model model) throws ServletException {
        try {
            List<Order> orders = orderService.findByCurrentPage(page);
            Integer pageCount = (int) Math.ceil((1.0 * orderService.ordersCount().intValue()) / 10);
            model.addAttribute("orders", orders);
            CountPageSaver.page(model, page, pageCount);
            return "client/all_orders";
        } catch (ServiceException e) {
            throw new ServletException(e);
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
