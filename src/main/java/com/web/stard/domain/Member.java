package com.web.stard.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Entity
@NoArgsConstructor(force = true)
@Table(name="Member")
public class Member {

    @Id
    private String id;
    @NotNull
    private String nickname;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private String email;
    @NotNull
    private String phone;

    // 일단 필수 데이터만 정의

    private String city; // 시
    private String district; // 구

    @OneToOne @JoinColumn(name = "profile_id")
    private Profile profile; // 프로필

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authority_id") // Member 테이블에 authority_id 컬럼 추가
    private Authority roles;

    @Builder
    public Member(String id, String name, String email, String password, String phone, String nickname) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nickname = nickname;
    }

}