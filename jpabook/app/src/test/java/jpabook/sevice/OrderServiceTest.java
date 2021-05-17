package jpabook.sevice;

import jpabook.domain.OrderSearch;
import jpabook.model.entity.Address;
import jpabook.model.entity.Member;
import jpabook.model.entity.Order;
import jpabook.model.entity.enums.OrderStatus;
import jpabook.model.entity.item.Book;
import jpabook.model.entity.item.Item;
import jpabook.model.entity.item.NotEnoughStockException;
import jpabook.repository.OrderRepository;
import jpabook.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void orderItem() throws Exception {
        // given
        Member member = createMember("회원1");
        Item item = createBook("jpabook", 100, 10);
        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItemList().size(), "주문한 상품의 종류는 1개");
        assertEquals(100 * 2, getOrder.getTotalPrice(), "100 * 2");
        assertEquals(8, item.getStockQuantity(), "10 - 2 = 8");
    }

    @Test
    public void exceedOrderExceptionTest() {
        // given
        Member member = createMember("회원1");
        Item item = createBook("jpabooka", 101, 11);
        int count = 100;

        // when, then
        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), count));
    }

    @Test
    public void orderCancelTest() {
        // given
        Member member = createMember("회원1");
        Item item = createBook("jpabook", 100, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderRepository.findOne(orderId).cancel();

        // then
        assertEquals(10, item.getStockQuantity(), "주문시 아이템의 수량이 회복된다.");
        assertEquals(OrderStatus.CANCEL, orderRepository.findOne(orderId).getStatus(), "주문 취소시 상태는 cancel");
    }

    @Test
    public void searchOrderTest() {
        Member member1 = createMember("회원1");
        Member member2 = createMember("회원2");
        Item item1 = createBook("b1", 12, 10);
        Item item2 = createBook("b2", 1, 13);
        Long orderId1 = orderService.order(member1.getId(), item1.getId(), 2);
        Long orderId2 = orderService.order(member2.getId(), item2.getId(), 3);

        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName("회원1");
        orderSearch.setOrderStatus(OrderStatus.ORDER);

        // when
        List<Order> orders = orderService.findOrders(orderSearch);

        // then
        assertEquals(1, orders.size());
        assertEquals(1, orders.get(0).getOrderItemList().size());
        assertEquals(24, orders.get(0).getTotalPrice());
        assertEquals("회원1", orders.get(0).getMember().getName());
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}
