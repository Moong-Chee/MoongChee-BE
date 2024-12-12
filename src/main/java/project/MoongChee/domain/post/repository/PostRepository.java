package project.MoongChee.domain.post.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import project.MoongChee.domain.post.entity.Post;
import project.MoongChee.domain.post.entity.PostStatus;
import project.MoongChee.domain.user.domain.User;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Optional<Post> findByPostIdAndAuthor(Long postId, User author);

    List<Post> findByPostStatusNot(PostStatus status);

    List<Post> findByAuthorAndPostStatusNot(User author, PostStatus status);

    List<Post> findByAuthorAndPostStatus(User author, PostStatus status);
}
