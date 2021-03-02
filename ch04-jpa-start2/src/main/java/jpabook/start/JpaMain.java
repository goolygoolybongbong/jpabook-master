package jpabook.start;

import jpabook.start.family.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author holyeye
 */
public class JpaMain {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {


            tx.begin(); //트랜잭션 시작
            //logic(em);  //비즈니스 로직
            //boardTest(em);
            jqplSave(em);
            //registerMember(em);
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        em = emf.createEntityManager();
        tx = em.getTransaction();
        try {
            tx.begin();
            //graphExploration(em);
//            biDirection(em);
//            productMember(em);
//            findProductMember(em);
//            compositeDiscriminateKeyTest(em);
            jqplQuery(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    public static void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        //등록
        em.persist(member);

        //수정
        member.setAge(20);

        //한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        //목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());

        //삭제
        em.remove(member);

    }

    public static void boardTest(EntityManager em) {
        for(int i = 1; i <= 7; i++) {
            Board b = new Board();
            b.setData("test data" + i);
            em.persist(b);
        }
    }

    public static void biDirection(EntityManager em) {
        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();

        if(members.size() == 0) {
            System.out.println("member size == 0!!!!!!!!!!!!!!!!");
        }
        for(Member member : members) {
            System.out.println("member id : " + member.getId());
            System.out.println("member name : " + member.getUsername());
        }
    }

    public static void graphExploration(EntityManager em) {
        Member m1 = em.find(Member.class, "1");
        Team t = m1.getTeam();
        System.out.println("m1 name : " + m1.getUsername());
        System.out.println("team name : " + t.getName());
    }

    public static void registerMember(EntityManager em) {
        Team t1 = new Team();
        t1.setId("team1");
        t1.setName("팀1");
        em.persist(t1);

        Member m1 = new Member();
        m1.setId("1");
        m1.setUsername("m1 name");
        m1.setTeam(t1);
        Member m2 = new Member();
        m2.setId("2");
        m2.setUsername("m2 name");
        m2.setTeam(t1);

        t1.getMembers().add(m1);
        t1.getMembers().add(m2);

        em.persist(m1);
        em.persist(m2);
    }

    public static void productMember(EntityManager em) {
        Member m1 = new Member();
        m1.setId("member12");
        m1.setUsername("회원1");
        em.persist(m1);

        Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품A");
        em.persist(productA);

        MemberProduct mp = new MemberProduct();
        mp.setMember(m1);
        mp.setProduct(productA);
        mp.setOrderAmount(2);

        em.persist(mp);

        System.out.println("asdfffffffffff" + m1.getUsername());
    }

    public static void findProductMember(EntityManager em) {
        MemberProductId mpid = new MemberProductId();
        mpid.setMember("member12");
        mpid.setProduct("productA");

        MemberProduct memberProduct = em.find(MemberProduct.class, mpid);

        Member member = memberProduct.getMember();
        Product product = memberProduct.getProduct();

        System.out.println("member = " + member.getUsername());
        System.out.println("product = " + product.getName());
        System.out.println("amount = " + memberProduct.getOrderAmount());
    }

    public static void compositeDiscriminateKeyTest(EntityManager em) {
        // parent
        String parentId = "testParentId";
        String parentName = "testParentName";

        Parent parent = new Parent();
        parent.setId(parentId);
        parent.setName(parentName);

        //em.persist(parent);


        // child
        String childRawId = "testChildId";
        String childName = "testChildName";

        ChildId childId = new ChildId();
        childId.setParentId(parent.getId());
        childId.setId(childRawId);

        Child child = new Child();
        child.setChildId(childId);
        child.setParent(parent);
        child.setName(childName);

        //em.persist(child);


        // grandchild
        String grandChildRawId = "testGrandChildId";
        String grandChildName = "testGrandChildName";

        GrandchildId grandchildId = new GrandchildId();
        grandchildId.setChildId(childId);
        grandchildId.setId(grandChildRawId);

        Grandchild grandchild = new Grandchild();
        grandchild.setId(grandchildId);
        grandchild.setChild(child);
        grandchild.setName(grandChildName);

        em.persist(grandchild);


        // find persisted entity
        Parent foundParent = em.find(Parent.class, parentId);
        Child foundChild = em.find(Child.class, childId);
        Grandchild foundGrandchild = em.find(Grandchild.class, grandchildId);

        // check persisted values
        String tmp;
        tmp = String.format("Found parent id : %s, name : %s", foundParent.getId(), foundParent.getName());
        System.out.println(tmp);

        tmp = String.format("Found Child id : %s, name : %s", foundChild.getChildId().getId(), foundChild.getName());
        System.out.println(tmp);

        tmp = String.format("Found grandchild id : %s, name : %s", foundGrandchild.getId().getId(), foundGrandchild.getName());
        System.out.println(tmp);

        // check object inheritance?
        if(foundParent == foundChild.getParent()) {
            System.out.println("foundParent == foundChild.getParent()");
        } else {
            System.out.println("foundParent != foundChild.getParent()");
        }

        if(foundChild == foundGrandchild.getChild()) {
            System.out.println("foundChild == foundGrandchild.getChild()");
        } else {
            System.out.println("foundChild != foundGrandchild.getChild()");
        }

    }

    public static void jqplSave(EntityManager em) {
        Product p1 = new Product();
        p1.setId("p1");
        p1.setName("asdf");
        p1.setStockAmount(99);
        Product p2 = new Product();
        p2.setId("p2");
        p2.setName("fdsa");
        p2.setStockAmount(1);

        Order o1 = new Order();
        o1.setProduct(p1);
        o1.setOrderAmount(100);

        Order o2 = new Order();
        o2.setProduct(p2);
        o2.setOrderAmount(2);

        em.persist(p1);
        em.persist(p2);
        em.persist(o1);
        em.persist(o2);
    }

    public static void jqplQuery(EntityManager em) {
        //String query = "select o from ORDERS o where o.orderAmount > ALL(select p.stockAmount from Product p where o.product = p)";
        String query = "select o from ORDERS o where o IN(select o from Product p where o.product = p)";
        List orders = em.createQuery(query).getResultList();
        for(Object o : orders) {
            System.out.println(((Order)o).getOrderAmount());
        }
    }
}
