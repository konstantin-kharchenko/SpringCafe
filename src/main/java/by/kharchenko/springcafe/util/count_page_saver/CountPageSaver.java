package by.kharchenko.springcafe.util.count_page_saver;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Objects;

import static by.kharchenko.springcafe.controller.RequestAttribute.FIRST_PAGE;
import static by.kharchenko.springcafe.controller.RequestAttribute.LAST_PAGE;

public final class CountPageSaver {

    private CountPageSaver() {
    }

    public static void page(Model model, Integer page, Integer pageCount) {
        model.addAttribute("currentPage", page);
        if (pageCount == 0) {
            model.addAttribute("pageCount", 1);
        } else {
            model.addAttribute("pageCount", pageCount);
        }
        if (pageCount == 1) {
            model.addAttribute(FIRST_PAGE, true);
            model.addAttribute(LAST_PAGE, true);
        } else if (Objects.equals(page, pageCount)) {
            model.addAttribute(FIRST_PAGE, true);
            model.addAttribute(LAST_PAGE, false);
        } else if (page == 1) {
            model.addAttribute(FIRST_PAGE, true);
            model.addAttribute(LAST_PAGE, false);
        } else {
            model.addAttribute(FIRST_PAGE, false);
            model.addAttribute(LAST_PAGE, false);
        }
    }
}
