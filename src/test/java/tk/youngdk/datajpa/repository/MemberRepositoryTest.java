package tk.youngdk.datajpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import tk.youngdk.datajpa.domain.Member;
import tk.youngdk.datajpa.domain.Team;
import tk.youngdk.datajpa.dto.MemberDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("저장 테스트")
    @Transactional
    @Commit
    public void testMember() throws Exception {
        System.out.println("memberRepository = " + memberRepository.getClass());
        //given
        Member member = new Member("memberA");

        //when
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
        assertThat(findMember).isEqualTo(member);

    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        System.out.println("all = " + all);
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검
        Long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        Long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void queryMethod() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUserNameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getUserName()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);

    }

    @Test
    public void findHelloBy() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> helloBy = memberRepository.findHelloBy();
        List<Member> top3HelloBy = memberRepository.findTop3HelloBy();

        helloBy.stream()
                .forEach(member -> System.out.println("member = " + member));

        top3HelloBy.stream()
                .forEach(member -> System.out.println("member = " + member));

    }

    @Test
    public void namedQuery() {

        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> findMembers = memberRepository.findByUserName("AAA");

        assertThat(findMembers.get(0)).isEqualTo(member1);
    }

    @Test
    public void queryAnnotation(){

        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> findUser = memberRepository.findUser("AAA", 20);

        assertThat(findUser.get(0)).isEqualTo(member2);
    }

    @Test
    public void findUserNameList(){

        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAB", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<String> userNameList = memberRepository.findUserNameList();

        userNameList.stream()
                .forEach(userName -> System.out.println("userName = " + userName));

    }

    @Test
    public void findMemberDto(){

        Team team1 = new Team("team1");

        teamRepository.save(team1);

        Member member1 = new Member("AAA", 10, team1);
        Member member2 = new Member("AAB", 20, team1);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<MemberDto> memberDtos = memberRepository.findMemberDto();

        memberDtos.stream()
                .forEach(memberDto -> System.out.println("memberDto = " + memberDto));

    }

    @Test
    public void findByNames(){

        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> byNames = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));

        byNames.stream()
                .forEach(member -> System.out.println("member = " + member));

    }

    @Test
    public void returnType(){
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-return-types
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> aaa = memberRepository.findListByUserName("AAA");
        System.out.println("aaa = " + aaa);

        System.out.println("================================");
        System.out.println("================================");
        Member member = memberRepository.findMemberByUserName("AAA");
        System.out.println("member = " + member);

        System.out.println("================================");
        System.out.println("================================");
        Optional<Member> memberOptional = memberRepository.findOptionalByUserName("AAA");

        System.out.println("memberOptional = " + memberOptional);
    }

    @Test
    public void findByPage() {
        //given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 10);
        Member member3 = new Member("member3", 10);
        Member member4 = new Member("member4", 10);
        Member member5 = new Member("member5", 10);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

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

        //then
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();
        int totalPages = page.getTotalPages();

        content.stream()
                .forEach(member -> System.out.println("member = " + member));

        System.out.println("totalElements = " + totalElements);
        System.out.println("totalPages = " + totalPages);

        assertThat(content.size()).isEqualTo(limit);
        assertThat(totalElements).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(offset);
        assertThat(totalPages).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();

        System.out.println("==============================================");
        System.out.println("==============================================");
        System.out.println("==============================================");

        Slice<Member> slice = memberRepository.findSliceByAge(age, pageRequest);

        List<Member> content2 = slice.getContent();

        content2.stream()
                .forEach(member -> System.out.println("member222 = " + member));

        assertThat(content2.size()).isEqualTo(limit);
//        assertThat(slice.getTotalElements).isEqualTo(5);
        assertThat(slice.getNumber()).isEqualTo(offset);
//        assertThat(slice.getTotalPages()).isEqualTo(2);
        assertThat(slice.isFirst()).isTrue();
        assertThat(slice.hasNext()).isTrue();

        System.out.println("==============================================");
        System.out.println("==============================================");
        System.out.println("==============================================");

        List<Member> list = memberRepository.findListByAge(age, pageRequest);

        list.stream()
                .forEach(member -> System.out.println("member = " + member));

    }

    @Test
    public void bulkUpdateSpringJpa(){
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        //when
        int resultCount = memberRepository.bulkAgePlus(20);

        /**
         * 벌크 연산 후 영속성 컨텍스트 초기화
         * Spring Data Jpa 에서 @Modifying(clearAutomatically = true)
         **/
//        em.flush();
//        em.clear();

        //then
        assertThat(resultCount).isEqualTo(3);


        /**
         * 문제접!!!
         * 벌크성 업데이트는 영속성 컨텍스트에 저장하는 것이 아니라
         * 데이터베이스에 바로 쿼리를 날려 버린다
         * 즉 영속성 컨텍스트에 남아있는 데이터와 불일치한다!!!
         * 벌크 연산을 하고 나서 영속성 컨텍스트를 모두 날려줘야한다
         * */
        List<Member> result = memberRepository.findByUserName("member5");
        Member member5 = result.get(0);
        System.out.println("member5 = " + member5);
    }

}

