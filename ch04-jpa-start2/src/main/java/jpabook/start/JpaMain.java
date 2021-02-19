package jpabook.start;

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
            registerMember(em);
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
            graphExploration(em);
            biDirection(em);
            productMember(em);
            findProductMember(em);
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

        /*t1.getMembers().add(m1);
        t1.getMembers().add(m2);*/

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
}
