package jpabook.start;

import javax.persistence.*;

/*
    한 Member는 여러 종류의 Product를 가질 수 있다.
    한 Product는 여러 명의 Member가 가질 수 있다. 하지만 Product는 어느 Member가 갖는지 알 필요 없다.
    다 대 다 Member
 */

@Entity(name = "ORDERS")
public class Order {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column
    private int orderAmount;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }
}
