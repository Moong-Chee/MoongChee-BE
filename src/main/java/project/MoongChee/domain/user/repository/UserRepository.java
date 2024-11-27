package project.MoongChee.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.MoongChee.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    boolean existsByCustomId(String customId);

    Optional<User> findByEmail(String email);

}
