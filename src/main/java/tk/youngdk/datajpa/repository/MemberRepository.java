package tk.youngdk.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.youngdk.datajpa.domain.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUserNameAndAgeGreaterThan (String userName, int age);

    List<Member> findHelloBy();

    List<Member> findTop3HelloBy();
}
