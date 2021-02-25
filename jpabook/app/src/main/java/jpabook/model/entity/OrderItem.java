package jpabook.model.entity;

import jpabook.model.entity.item.Item;

import javax.persistence.*;

@Entity
@Table//(name = "ORDER_ITEM")
@SequenceGenerator(
        name = "ORDER_ITEM_SEQ_GEN",
        sequenceName = "ORDER_ITEM_SEQ"
)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_ITEM_SEQ_GEN")
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn//(name = "ITEM_ID")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn//(name = "ORDER_ID")
    private Order order;

    @OneToOne
    @JoinColumn
    private Delivery delivery;

    private int orderPrice;
    private int count;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        if(this.order != null) {
            this.order.getOrderItemList().remove(this);
        }
        this.order = order;
        this.order.getOrderItemList().add(this);
    }
}
