package jpabook.model.entity;

import jpabook.model.entity.enums.OrderStatus;
import jpabook.model.entity.item.Item;

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
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        if(this.member != null) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        this.member.getOrders().add(this);
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
