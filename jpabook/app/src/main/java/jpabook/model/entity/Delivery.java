package jpabook.model.entity;

import jpabook.model.entity.enums.DeliveryStatus;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "DELIVERY_SEQ_GEN",
        sequenceName = "DELIVERY_SEQ"
)
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY_SEQ_GEN")
    private Long id;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public Delivery() {

    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Delivery(Address address) {
        this.address = address;
    }
}
