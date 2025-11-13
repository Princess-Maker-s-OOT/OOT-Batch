package com.ootbatch.domain.user.entity;

import com.ootbatch.common.entity.BaseEntity;
import com.ootbatch.domain.auth.enums.LoginType;
import com.ootbatch.domain.auth.enums.SocialProvider;
import com.ootbatch.domain.chatparticipatinguser.entity.ChatParticipatingUser;
import com.ootbatch.domain.closet.entity.Closet;
import com.ootbatch.domain.user.enums.UserRole;
import com.ootbatch.domain.userimage.entity.UserImage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    private static final String SOCIAL_LOGIN_ID_PREFIX = "SOCIAL_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 25)
    private String loginId;

    @Column(nullable = false, unique = true, length = 60)
    private String email;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    @Column(nullable = false, length = 60)
    private String username;

    @Column(nullable = true, length = 255)
    private String password;

    @Column(nullable = true, unique = true, length = 30)
    private String phoneNumber;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false, length = 50)
    private String tradeAddress;

    @Column(nullable = true, columnDefinition = "POINT SRID 4326", updatable = false, insertable = false)
    private String tradeLocation;

    // 현재 프로필 이미지 URL(소셜 로그인 또는 직접 업로드)
    @Column(nullable = true, length = 500)
    private String imageUrl;

    // 사용자가 직접 업로드한 이미지
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_image_id")
    private UserImage userImage;

    // 로그인 타입 추가(LOGIN_ID, SOCIAL 구분)
    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    // 소셜 로그인 제공자: GOOGLE, KAKAO, NAVER 등
    @Column(nullable = true, length = 10)
    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;

    // 소셜 ID(소셜 로그인 시 고유 식별자 - Google의 sub)
    @Column(nullable = true, unique = true, length = 100)
    private String socialId;

    // 옷장 연관관계
    @OneToMany(mappedBy = "user")
    private List<Closet> closets = new ArrayList<>();

    // 중간테이블
    @OneToMany(mappedBy = "user")
    private List<ChatParticipatingUser> chatParticipatingUsers = new ArrayList<>();
}
