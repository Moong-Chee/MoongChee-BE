package project.MoongChee.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.MoongChee.domain.user.domain.Status;
import project.MoongChee.domain.user.domain.User;

@Service
@RequiredArgsConstructor
public class UserUpdateService {
    @Transactional
    public void accept(User user) {
        user.changeActive(Status.ACCEPTED); // 상태를 ACTIVE로 변경
    }
}
