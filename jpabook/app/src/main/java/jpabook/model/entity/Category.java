package jpabook.model.entity;

import jpabook.model.CategoryItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "CATEGORY_SEQ_GENERATOR",
        sequenceName = "CATEGORY_SEQ"
)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CATEGORY_SEQ")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    private List<Item> items;

    // Category 계층 구조 : 한 부모 카테고리가 여러 자식 카테고리를 갖는다.
    // 자식 카테고리는 하나의 부모 카테고리를 갖고
    @ManyToOne
    @JoinColumn(name="PARENT_ID")
    private Category parent;

    // 부모 카테고리는 여러개의 자식 카테고리를 갖는다.
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}