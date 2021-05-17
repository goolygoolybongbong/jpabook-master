package jpabook.web;

import jpabook.model.entity.item.Item;
import jpabook.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final ItemService itemService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/items")
    public String itemList(Model model) {
        model.addAttribute("items", itemService.findItems());

        return "items";
    }
}
