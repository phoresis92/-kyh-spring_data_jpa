package tk.youngdk.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.youngdk.datajpa.domain.Member;
import tk.youngdk.datajpa.dto.MemberDto;
import tk.youngdk.datajpa.repository.MemberRepository;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final MemberRepository memberRepository;
    @GetMapping("/hello")
    @Transactional
    public Page<MemberDto> hello(){

        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 10);
        Member member3 = new Member("member3", 10);
        Member member4 = new Member("member4", 10);
        Member member5 = new Member("member5", 10);

        memberRepository.saveAll(Arrays.asList(member1, member2, member3, member4, member5));

        int age = 10;
        int offset = 0;
        int limit = 3;

        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "userName"));

        //when
        Page<Member> page = memberRepository.findPageByAge(age, pageRequest);
//        long totalCount = memberRepository.totalCount(age);

        /**
         * 엔티티 그대로 반환 하지 말자!!!
         * */
        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUserName(), null));

        return map;

//        return "hello";
    }

}
