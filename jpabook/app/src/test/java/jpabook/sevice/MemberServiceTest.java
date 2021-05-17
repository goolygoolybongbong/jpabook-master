package jpabook.sevice;

import jpabook.model.entity.Member;
import jpabook.repository.MemberRepository;
import jpabook.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void joinTest() {
        // Given
        Member member = new Member();
        member.setName("kim");

        // When
        Long saveId = memberService.join(member);

        // Then
        assertEquals(member.getId(), memberRepository.findOne(saveId).getId());
    }

    @Test
    public void duplicateExceptionTest() {
        // Given
        Member member = new Member();
        member.setName("aa");
        Member member1 = new Member();
        member1.setName("aa");

        // When
        memberService.join(member);

        // Then
        Exception exception = assertThrows(IllegalStateException.class, () -> memberService.join(member1));
        assertEquals("이미 존재하는 회원입니다.", exception.getMessage());
    }
}
