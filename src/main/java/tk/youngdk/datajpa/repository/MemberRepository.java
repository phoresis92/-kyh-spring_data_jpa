package tk.youngdk.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import tk.youngdk.datajpa.domain.Member;
import tk.youngdk.datajpa.dto.MemberDto;

import javax.persistence.Entity;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUserNameAndAgeGreaterThan (String userName, int age);

    List<Member> findHelloBy();

    List<Member> findTop3HelloBy();

//    @Query(name = "Member.findByUserName")용
//    없어도 엔티티에 NamedQuery에서 메서드이름과 동일한 쿼리를 찾아서 적용한다
    // 1. @Query 어노테이션적용
    // 2. JpaRepository<Entity, Id> Entity 클래스에 등록된 NamedQuery 적용
    // 3. 메서드 쿼리 적
//    List<Member> findByUserName(@Param("username") String username);

    // 에플리케이션 로딩 시점에 쿼리를 파싱하여 오류를 반환한다.
    // 이름 없는 NamedQuery라고 생각하면 편하다
    @Query("select m from Member m where m.userName = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.userName from Member m")
    List<String> findUserNameList();

    @Query("select new tk.youngdk.datajpa.dto.MemberDto(m.id, m.userName, t.teamName) from Member m left join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.userName in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUserName(String userName); // 컬렉션
    Member findMemberByUserName(String userName); // 단건
    Optional<Member> findOptionalByUserName(String userName); // Optional

    // 카운트 쿼리를 따로 구현하여 성능 최적화 하자
    @Query(
            value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m"
    )
    Page<Member> findPageByAge(int age, Pageable pageable);
    Slice<Member> findSliceByAge(int age, Pageable pageable);
    List<Member> findListByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team t")
    List<Member> findFetchByAll();

    @Override
    @EntityGraph(attributePaths = "team")
    List<Member> findAll();

    @Query("select m from Member m")
    @EntityGraph(attributePaths = "team")
    List<Member> findMemberEntityGraph();

//    @EntityGraph(attributePaths = "team")
    @EntityGraph("Member.all")
    List<Member> findByUserName(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUserName(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUserName(String username);

}
