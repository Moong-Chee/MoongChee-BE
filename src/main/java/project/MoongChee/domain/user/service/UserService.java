package project.MoongChee.domain.user.service;

import static project.MoongChee.domain.user.domain.LoginStatus.LOGIN;
import static project.MoongChee.domain.user.domain.LoginStatus.REGISTER;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.MoongChee.domain.image.domain.Image;
import project.MoongChee.domain.image.dto.request.ImageDto;
import project.MoongChee.domain.image.service.ImageService;
import project.MoongChee.domain.user.domain.Department;
import project.MoongChee.domain.user.domain.Status;
import project.MoongChee.domain.user.domain.User;
import project.MoongChee.domain.user.dto.request.UserInitializeRequest;
import project.MoongChee.domain.user.dto.response.UserProfileResponse;
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
    private final ImageService imageService;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    /*
     * 인증 관련
     */
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

    /*
     * 로그인, 회원가입 관련
     */
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
                .status(Status.WAITING)
                .build();

        userRepository.save(user);

        return new UserSocialLoginResponse(user.getId(), REGISTER, null, generateToken(user.getEmail()));
    }

    /*
     * 회원가입 시 초기 정보 입력
     */
    @Transactional
    public void initProfile(UserInitializeRequest dto, MultipartFile profileImage, String email) throws IOException {
        User user = find(email);
        Image savedImage = user.getProfileImage();
        // 이미지가 없다면 새로 생성해서 저장
        if (savedImage == null) {
            savedImage = imageService.save(profileImage, user);
        } else if (profileImage != null) {
            ImageDto imageDto = imageService.getImage(profileImage);
            savedImage.update(imageDto);
        }
        valid(dto.customId());
        user.initProfile(dto, savedImage);
    }

    /*
     * 구글 계정 기본 프로필 정보 반환
     */
    public UserProfileResponse getProfile(String customId, String email) {
        User user = userRepository.findByCustomId(customId)
                .orElseThrow(UserNotFoundException::new);
        Department department = user.getDepartment();
        long studentNumber = user.getStudentNumber();
        LocalDate birthday = user.getBirthday();

        return UserProfileResponse.from(user, department, studentNumber, birthday);
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
