package project.MoongChee.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.MoongChee.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
