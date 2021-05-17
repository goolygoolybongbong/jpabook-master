package jpabook.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.domain.OrderSearch;
import jpabook.model.entity.Order;
import jpabook.generated.model.entity.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> getAllOrder() {
        JPAQueryFactory jqf = new JPAQueryFactory(em);
        QOrder order = QOrder.order;

        return jqf.selectFrom(order).fetch();
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        JPAQueryFactory jqf = new JPAQueryFactory(em);
        QOrder order = QOrder.order;

        return jqf.selectFrom(order)
                .where(order.status.eq(orderSearch.getOrderStatus())
                                .and(order.member.name.eq(orderSearch.getMemberName())))
                .limit(1000)
                .fetch();
    }
}
