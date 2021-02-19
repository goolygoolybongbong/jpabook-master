/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package jpabook;

import jpabook.model.entity.*;
import jpabook.model.entity.enums.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class App {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            logic(em);
            objectExplorerText(em);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
        entityManagerFactory.close();
    }

    public static void logic(EntityManager em) {
        Item i = new Item();
        i.setName("name");
        i.setPrice(100);
        em.persist(i);

        //em.clear();
    }

    public static void objectExplorerText(EntityManager em) {
        // given
        Item itemPut = new Item();
        //itemPut.setId(1L);
        itemPut.setPrice(1000);
        itemPut.setName("testItem");
        itemPut.setStockQuantity(1000);

        Member memberPut = new Member();
        //memberPut.setId(1L);
        memberPut.setName("test");
        memberPut.setCity("testCity");
        memberPut.setStreet("testStreet");
        memberPut.setZipcode("testZipcode");

        Order orderPut = new Order();
        //orderPut.setId(1L);
        orderPut.setStatus(OrderStatus.ORDER);
        orderPut.setMember(memberPut);
        orderPut.setOrderDate(new Date());

        OrderItem orderItemPut = new OrderItem();
        //orderItemPut.setId(1L);
        orderItemPut.setItem(itemPut);
        orderItemPut.setOrder(orderPut);
        orderItemPut.setOrderPrice(1000);
        orderItemPut.setCount(1);

        // when
        em.persist(itemPut);
        em.persist(memberPut);
        em.persist(orderPut);
        em.persist(orderItemPut);

        em.flush();

        // then
        Long ordId = itemPut.getId();
        Order orderGet = em.find(Order.class, 1L);
        Member memberGet = orderGet.getMember();
        Order orderGetFromMemberGet = memberGet.getOrders().get(0);
        OrderItem orderItemGet = orderGet.getOrderItemList().get(0);
        Item itemGet = orderItemGet.getItem();
    }
}