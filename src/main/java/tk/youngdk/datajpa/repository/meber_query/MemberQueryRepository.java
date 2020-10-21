package tk.youngdk.datajpa.repository.meber_query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tk.youngdk.datajpa.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
    private final EntityManager em;

    List<Member> findAllMembers() {
        return em.createQuery("select  m from Member m")
                .getResultList();
    }
}
