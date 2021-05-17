package jpabook.model.entity;

import jpabook.model.entity.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table//(name = "ORDER_ITEM")
@SequenceGenerator(
        name = "ORDER_ITEM_SEQ_GEN",
        sequenceName = "ORDER_ITEM_SEQ"
)
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_ITEM_SEQ_GEN")
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn//(name = "ORDER_ID")
    private Order order;

    /*@OneToOne
    @JoinColumn
    private Delivery delivery;*/

    private int orderPrice;
    private int count;

    public OrderItem() {}

    public void setOrder(Order order) {
        if(this.order != null) {
            this.order.getOrderItemList().remove(this);
        }
        this.order = order;
        this.order.getOrderItemList().add(this);
    }

    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    // 주문 취소
    public void cancel() {
       this.item.addStock(this.count);
    }

    // 전체 가격 조회
    public int getTotalPrice() {
        return this.orderPrice * this.count;
    }
}
