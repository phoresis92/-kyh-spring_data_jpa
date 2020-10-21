package tk.youngdk.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.youngdk.datajpa.domain.Member;
import tk.youngdk.datajpa.dto.MemberDto;
import tk.youngdk.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        System.out.println("MemberController.findMember");

        Member member = memberRepository.findById(id).get();
        return member.getUserName();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        System.out.println("MemberController.findMember2");

        /*
        도메인 클래스 컨버터로 엔티티를 파라미터로 받으면,
        이 엔티티는 단순 조회용으로만 사용해야한다.

        트랜잭션이 없는 범위에서 엔티티를 조회했으므로,
        엔티티를 변경해도 DB에 반영되지 않는다.
        */

        return member.getUserName();
    }

    @PostMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5) Pageable pageable) {
        Page<Member> all = memberRepository.findAll(pageable);

//        return all.map(member -> new MemberDto(member.getId(), member.getUserName(), null/*member.getTeam().getTeamName()*/));
//        return all.map(member -> new MemberDto(member));
        return all.map(MemberDto::new);
//        return all;
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {

            memberRepository.save(new Member("user" + i, i));
        }
    }
}
