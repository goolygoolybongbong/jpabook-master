package jpabook.model.entity;

import jpabook.model.entity.item.Item;

import javax.persistence.*;

@Entity
//@IdClass(CategoryItemId.class)
@SequenceGenerator(
        name = "CATEGORY_ITEM_SEQ_GEN",
        sequenceName = "CATEGORY_ITME_SEQ"
)
public class CategoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORY_ITEM_SEQ_GEN")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        if(this.category != null) {
            this.category.getCategoryItems().remove(this);
        }
        this.category = category;
        this.category.getCategoryItems().add(this);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        if(this.item != null) {
            this.item.getCategoryItems().remove(this);
        }
        this.item = item;
        this.category.getCategoryItems().add(this);
    }
}
