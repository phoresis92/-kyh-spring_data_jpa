package tk.youngdk.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tk.youngdk.datajpa.domain.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUserNameAndAgeGreaterThan (String userName, int age);

    List<Member> findHelloBy();

    List<Member> findTop3HelloBy();

//    @Query(name = "Member.findByUserName")용
//    없어도 엔티티에 NamedQuery에서 메서드이름과 동일한 쿼리를 찾아서 적용한다
    // 1. @Query 어노테이션적용
    // 2. JpaRepository<Entity, Id> Entity 클래스에 등록된 NamedQuery 적용
    // 3. 메서드 쿼리 적
    List<Member> findByUserName(@Param("username") String username);
}
