package project.MoongChee.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.MoongChee.domain.user.dto.request.UserInitializeRequest;
import project.MoongChee.global.common.domain.BaseTimeEntity;

@Entity
// mysql에서 user 테이블이 존재 하기 때문에 다른 이름으로 지정
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String customId;

    private LocalDate birth;

    private String email;

    private String name;

    private String profileImageUrl;

    private String phoneNumber;

    private String birthday;

    @Enumerated(EnumType.STRING)
    private Department department;

    private long studentNumber;

    public void initProfile(UserInitializeRequest dto) {
        this.birth = dto.birth();
        this.customId = dto.customId();
    }
}
