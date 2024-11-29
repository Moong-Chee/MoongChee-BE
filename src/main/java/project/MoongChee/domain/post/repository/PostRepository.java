package project.MoongChee.domain.post.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.MoongChee.domain.post.entity.Post;
import project.MoongChee.domain.user.domain.User;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostIdAndAuthor(Long postId, User author);
}
