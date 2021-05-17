package jpabook.model.entity;

import jpabook.model.entity.enums.DeliveryStatus;
import jpabook.model.entity.enums.OrderStatus;
import jpabook.model.entity.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@SequenceGenerator(
        name = "ORDERS_SEQ_GEN",
        sequenceName = "ORDERS_SEQ"
)
@Getter
@Setter
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ_GEN")
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn//(name = "MEMBER_ID")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)//, mappedBy = "order")
    @JoinColumn(name = "ORDER_ID")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID", unique = true)
    private Delivery delivery;

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        this.delivery.setOrder(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        if(orderItemList.contains(orderItem)) return;
        //orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setMember(Member member) {
        if(this.member != null) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        this.member.getOrders().add(this);
    }

    // 주문 생성
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(var o : orderItems) {
            order.addOrderItem(o);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(new Date());
        return order;
    }

    // 주문 취소
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMPLETED) {
            throw new RuntimeException("이미 배송이 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(var o : orderItemList)
            o.cancel();
    }

    // 조회
    public int getTotalPrice() {
        int totalPrice = 0;
        for(var o : this.orderItemList)
            totalPrice += o.getTotalPrice();
        return totalPrice;
    }
}
