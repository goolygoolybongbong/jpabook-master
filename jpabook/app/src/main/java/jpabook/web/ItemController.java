package jpabook.web;

import jpabook.model.entity.item.Book;
import jpabook.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm() {
        return "/items/createItemForm";
    }

    @PostMapping("/items/new")
    public void create(@RequestBody Book item) {
        itemService.saveItem(item);
        //return "redirect:/items";
    }
}
