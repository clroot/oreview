package io.clroot.oreview.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String email;
    private Boolean verifiedEmail = false;
    private Boolean agreeEmailNoti = false;

    @Builder
    public Member(String username, String email, Boolean agreeEmailNoti) {
        this.username = username;
        this.email = email;
        this.verifiedEmail = false;
        this.agreeEmailNoti = agreeEmailNoti;
    }
}
