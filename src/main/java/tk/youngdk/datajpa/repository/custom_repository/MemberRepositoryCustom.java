package tk.youngdk.datajpa.repository.custom_repository;

import tk.youngdk.datajpa.domain.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
