package project.MoongChee.domain.user.service;

import static project.MoongChee.domain.user.domain.LoginStatus.LOGIN;
import static project.MoongChee.domain.user.domain.LoginStatus.REGISTER;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.MoongChee.domain.user.domain.User;
import project.MoongChee.domain.user.dto.request.UserInitializeRequest;
import project.MoongChee.domain.user.dto.response.UserSocialLoginResponse;
import project.MoongChee.domain.user.exception.DuplicateCustomIdException;
import project.MoongChee.domain.user.exception.UserNotFoundException;
import project.MoongChee.domain.user.repository.UserRepository;
import project.MoongChee.global.auth.jwt.JwtProvider;
import project.MoongChee.global.auth.jwt.JwtResponse;
import project.MoongChee.global.auth.oauth.AuthService;
import project.MoongChee.global.auth.oauth.dto.GoogleTokenResponse;
import project.MoongChee.global.auth.oauth.dto.GoogleUserInfoResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;


    @Transactional
    public UserSocialLoginResponse authenticate(String authCode) {
        GoogleTokenResponse token = authService.getGoogleAccessToken(authCode);
        GoogleUserInfoResponse userInfo = authService.getGoogleUserInfo(token.access_token());

        String email = userInfo.email();

        // 가입된 유저라면 로그인
        if (existUser(email)) {
            return loginUser(userInfo.email());
        }
        // 아니라면 회원가입
        return registerUser(userInfo);
    }

    @Transactional
    public void initProfile(UserInitializeRequest dto, String email) {
        User user = find(email);
        valid(dto.customId());
        user.initProfile(dto);
    }

    private UserSocialLoginResponse loginUser(String email) {
        User user = find(email);
        String customId = null;
        if (user.getCustomId() != null) {
            customId = user.getCustomId();
        }
        return new UserSocialLoginResponse(user.getId(), LOGIN, customId, generateToken(email));
    }

    private UserSocialLoginResponse registerUser(GoogleUserInfoResponse userInfo) {
        User user = User.builder()
                .name(userInfo.name())
                .email(userInfo.email())
                .build();

        userRepository.save(user);

        return new UserSocialLoginResponse(user.getId(), REGISTER, null, generateToken(user.getEmail()));
    }

    private JwtResponse generateToken(String email) {
        return JwtResponse.builder()
                .accessToken(jwtProvider.generateAccessToken(email))
                .refreshToken(jwtProvider.generateRefreshToken())
                .build();
    }

    private void valid(String customId) {
        if (userRepository.existsByCustomId(customId)) {
            throw new DuplicateCustomIdException();
        }
    }

    public User find(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public User find(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public boolean existUser(String email) {
        return userRepository.existsByEmail(email);
    }
}
