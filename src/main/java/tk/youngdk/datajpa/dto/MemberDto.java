package tk.youngdk.datajpa.dto;

import lombok.Data;
import tk.youngdk.datajpa.domain.Member;

@Data
public class MemberDto {

    private Long id;
    private String userName;
    private String teamName;

    public MemberDto(Long id, String userName, String teamName) {
        this.id = id;
        this.userName = userName;
        this.teamName = teamName;
    }

    public MemberDto(Member member) {
        id = member.getId();
        userName = member.getUserName();
//        teamName
    }
}
