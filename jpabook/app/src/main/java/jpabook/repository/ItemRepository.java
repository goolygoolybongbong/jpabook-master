package jpabook.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.model.entity.item.Item;
import jpabook.generated.model.entity.item.QItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

    public List<Item> findByName(String name) {
        JPAQueryFactory jqf = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        return jqf.selectFrom(qItem).where(qItem.name.eq(name)).fetch();
    }
}
