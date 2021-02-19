package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ExamMergeMain {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        Member member = createMember("memberA", "회원1");

        member.setUsername("회원명 변경");

        mergeMember(member);
    }

    static Member createMember(String id, String username) {
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();;

        tx1.begin();

        Member member = new Member();
        member.setUsername(username);
        member.setId(id);

        em1.persist(member);

        tx1.commit();

        em1.close();

        return member;
    }

    static void mergeMember(Member member) {
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx2.begin();
        Member mergedMember = em2.merge(member);
        tx2.commit();

        System.out.println("member = " + member.getUsername());

        System.out.println("mergedMember = " + mergedMember.getUsername());

        System.out.println("em2 contain member = " + em2.contains(member));
        System.out.println("em2 contain mergedMember = " + em2.contains(mergedMember));

        member.setUsername("zzzzz");
        mergedMember.setUsername("asdf");
        mergedMember.setAge(1000);

        tx2.begin();
        Member member2 = em2.find(Member.class, member.getId());
        System.out.println("after tx, jpa context still connected?"  + member2.getUsername());
        tx2.commit(); // 데이터베이스에 반영!

        em2.close();
    }
}
