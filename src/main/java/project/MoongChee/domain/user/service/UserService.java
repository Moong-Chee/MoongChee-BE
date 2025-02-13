package project.MoongChee.domain.user.service;

import static project.MoongChee.domain.user.domain.LoginStatus.LOGIN;
import static project.MoongChee.domain.user.domain.LoginStatus.REGISTER;

import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.MoongChee.domain.image.domain.Image;
import project.MoongChee.domain.image.dto.request.ImageDto;
import project.MoongChee.domain.image.service.ImageService;
import project.MoongChee.domain.user.domain.Status;
import project.MoongChee.domain.user.domain.User;
import project.MoongChee.domain.user.dto.request.InitRequest;
import project.MoongChee.domain.user.dto.response.SocialLoginResponse;
import project.MoongChee.domain.user.exception.InvalidEmailException;
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
    private final ImageService imageService;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    /*
     * 인증 관련
     */
    @Transactional
    public SocialLoginResponse authenticate(String authCode) {
        GoogleTokenResponse token = authService.getGoogleAccessToken(authCode);
        GoogleUserInfoResponse userInfo = authService.getGoogleUserInfo(token.access_token());

        String email = userInfo.email();

        // 이메일 도메인 검증
        if (!email.endsWith("@gachon.ac.kr")) {
            throw new InvalidEmailException(); // 이메일 도메인이 gachon.ac.kr이 아닌 경우
        }

        if (existUser(email)) {
            return loginUser(userInfo.email());
        }
        return registerUser(userInfo);
    }

    /*
     * 로그인, 회원가입 관련
     */
    private SocialLoginResponse loginUser(String email) {
        User user = find(email);
        return new SocialLoginResponse(user.getId(), LOGIN, generateToken(email));
    }

    private SocialLoginResponse registerUser(GoogleUserInfoResponse userInfo) {
        User user = User.builder()
                .name(userInfo.name())
                .email(userInfo.email())
                .status(Status.WAITING)
                .build();

        userRepository.save(user);

        return new SocialLoginResponse(user.getId(), REGISTER, generateToken(user.getEmail()));
    }

    /*
     * 회원가입 후 초기 정보 입력
     */
    @Transactional
    public void initProfile(InitRequest dto, MultipartFile profileImage, String email) throws IOException {
        User user = find(email);
        Image savedImage = user.getProfileImage();

        if (savedImage == null) {
            savedImage = imageService.save(profileImage, user);
        } else if (profileImage != null) {
            ImageDto imageDto = imageService.getImage(profileImage);
            savedImage.update(imageDto);
        }
        user.initProfile(dto, savedImage);
    }

    /*
     * email 토큰 생성
     */
    private JwtResponse generateToken(String email) {
        return JwtResponse.builder()
                .accessToken(jwtProvider.generateAccessToken(email))
                .refreshToken(jwtProvider.generateRefreshToken())
                .build();
    }

    /*
     * userRepository 관련
     */

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
