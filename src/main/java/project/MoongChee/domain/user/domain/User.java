package project.MoongChee.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.MoongChee.domain.image.domain.Image;
import project.MoongChee.domain.post.entity.Post;
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

    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status; // 사용자 상태

    @OneToOne(mappedBy = "user")
    private Image profileImage;

    private String phoneNumber;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Department department;

    private long studentNumber;

    @ManyToMany
    @JoinTable(name = "likes", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> likes = new ArrayList<>();

    public void initProfile(UserInitializeRequest dto, Image profileImage) {
        this.customId = dto.customId();
        this.phoneNumber = dto.phoneNumber();
        this.birthday = dto.birthday();
        this.studentNumber = dto.studentNumber();
        this.department = dto.department();
        this.profileImage = profileImage;
    }

    public boolean isInactive() {
        return this.status != Status.ACCEPTED;
    }

    public void changeActive(Status newStatus) {
        this.status = newStatus;
    }

    public void addLike(Post post) {
        if (!likes.contains(post)) {
            likes.add(post);
        }
    }

    public void deleteLike(Post post) {
        likes.remove(post);
    }
}
