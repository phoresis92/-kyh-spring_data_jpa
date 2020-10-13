package tk.youngdk.datajpa.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "userName"})
public class Member {
    public Member(String userName) {
        this.userName = userName;
    }

    @Id @GeneratedValue
    private Long id;

    private String userName;

//    public void changeUserName(String userName) {
//        this.userName = userName;
//
//    }

}
