package jpabook.model;

import jpabook.model.entity.Category;
import jpabook.model.entity.CategoryItemId;
import jpabook.model.entity.Item;

import javax.persistence.*;

@Entity
@IdClass(CategoryItemId.class)
public class CategoryItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Id
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
