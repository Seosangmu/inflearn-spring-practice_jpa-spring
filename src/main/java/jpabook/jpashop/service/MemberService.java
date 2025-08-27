package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기전용에는 readOnly = true주면 JPA가 불필요한 작업을 생략해준다. findMembers, findOne적용
@RequiredArgsConstructor // final이 붙은 필드의 생성자를 자동으로 만들어준다.
public class MemberService {

    //@Autowired 필드주입은 좋지않다. TEST등등 에서 => 생성자 주입으로 변경 @RequiredArgsConstructor
    private final MemberRepository memberRepository;

    /**
     * 회의 가입
     */
    @Transactional // 트랜젝션안에서 데이터변경이 이루어져야한다. readOnly = false, 읽기 전용이 아니기때문에
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 한명 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
