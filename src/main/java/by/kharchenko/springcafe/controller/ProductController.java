package by.kharchenko.springcafe.controller;

import by.kharchenko.springcafe.model.entity.Product;
import by.kharchenko.springcafe.model.exception.ServiceException;
import by.kharchenko.springcafe.model.service.ProductService;
import by.kharchenko.springcafe.util.count_page_saver.CountPageSaver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all-products/{page}")
    public String goAllProducts(@PathVariable("page") Integer page, Model model) throws ServletException {
        try {
            List<Product> products = productService.findByCurrentPage(page);
            Integer pageCount = (int) Math.ceil((1.0 * productService.ordersCount().intValue()) / 10);
            model.addAttribute("products", products);
            CountPageSaver.page(model, page, pageCount);
            return "client/all_products";
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/search-product")
    public String searchProduct(@RequestParam(value = "search", required = false) String name, Model model) {
        if (name == null || name.equals("")) {
            return "redirect:/client/all-products/1";
        }
        Product product = productService.findByName(name);
        model.addAttribute("search_product", product);
        CountPageSaver.page(model, 1, 1);
        return "client/all_products";
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
