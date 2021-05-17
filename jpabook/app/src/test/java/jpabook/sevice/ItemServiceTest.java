package jpabook.sevice;

import jpabook.model.entity.item.Book;
import jpabook.model.entity.item.Item;
import jpabook.repository.ItemRepository;
import jpabook.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ItemServiceTest {
    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void saveItemTest() {
        // given
        Item b = new Book();
        String name = "asdf";
        int price = 100;
        int quantity = 2;
        b.setName(name);
        b.setPrice(price);
        b.setStockQuantity(quantity);

        // when
        itemService.saveItem(b);

        // then
        List<Item> items = itemRepository.findByName(name);
        assertEquals(name, items.get(0).getName());
        assertEquals(price, items.get(0).getPrice());
        assertEquals(quantity, items.get(0).getStockQuantity());
    }

}
