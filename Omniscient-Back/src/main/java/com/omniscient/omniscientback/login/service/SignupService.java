package com.omniscient.omniscientback.login.service;
import com.omniscient.omniscientback.login.model.SignupDTO;
import com.omniscient.omniscientback.login.model.UserEntity;
import com.omniscient.omniscientback.login.model.UserRole;
import com.omniscient.omniscientback.login.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Service
public class SignupService {


    private static final Logger logger = LoggerFactory.getLogger(SignupService.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private EmailService emailService;
    private Map<String, VerificationCode> verificationCodes = new HashMap<>();


    @Autowired
    public SignupService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;

    }

    // 모든 필드의 유효성 검증
    public boolean isAllFieldsValid(SignupDTO signupDTO) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        // 비밀번호는 최소 4자 이상이어야 함
        boolean isPasswordValid = signupDTO.getPassword() != null && signupDTO.getPassword().length() >= 4;


        return signupDTO.getUserId() != null && !signupDTO.getUserId().trim().isEmpty() &&
                signupDTO.getUsername() != null && !signupDTO.getUsername().trim().isEmpty() &&
                isPasswordValid && // 비밀번호 검증 로직
                signupDTO.getPassword() != null && !signupDTO.getPassword().trim().isEmpty() &&
                signupDTO.getEmail() != null && signupDTO.getEmail().matches(emailRegex) &&
                signupDTO.getPhoneNumber() != null && !signupDTO.getPhoneNumber().trim().isEmpty();

    }



    // 회원가입
    public boolean signup(SignupDTO signupDTO) {
        // 모든 필드 빈 값 검증
        if (!isAllFieldsValid(signupDTO)) {
            return false; // 모든 필드가 유효하지 않으면 false 반환
        }

        String userId = signupDTO.getUserId();
        String username = signupDTO.getUsername();
        String password = signupDTO.getPassword();
        String email = signupDTO.getEmail();
        String phoneNumber = signupDTO.getPhoneNumber();

        // 아이디 중복 체크
        if (userRepository.existsByUserId(userId)) {
            return false; // 중복된 아이디가 존재하면 false 반환
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // UserEntity 생성
        UserEntity userEntity = new UserEntity.Builder()
                .userId(userId)
                .username(username)
                .password(encodedPassword)
                .role(UserRole.USER) // 기본 역할 설정
                .userStatus(true) // 기본 사용자 상태 설정
                .phoneNumber(phoneNumber)
                .email(email)
                .refreshToken("") // 기본값 설정 (null 아님)
                .build();


        userRepository.save(userEntity);
        return true; // 회원가입 성공 시 true 반환
    }

    // 관리자 회원가입
    public boolean adminSignup(SignupDTO signupDTO) {
        logger.info("Validating fields for admin signup: {}", signupDTO);
        // 필수 필드 검증
        if (signupDTO.getUserId() == null || signupDTO.getPassword() == null || signupDTO.getUsername() == null) {
            return false;
        }

        String userId = signupDTO.getUserId();
        String password = signupDTO.getPassword();
        String username = signupDTO.getUsername();

        // 아이디 중복 체크
        if (userRepository.existsByUserId(signupDTO.getUserId())) {
            return false; // 이미 존재하는 경우 false 반환
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // UserEntity 생성
        UserEntity userEntity = new UserEntity.Builder()
                .userId(signupDTO.getUserId()) // signupDTO에서 사용자 ID 가져오기
                .username(signupDTO.getUsername()) // signupDTO에서 사용자 이름 가져오기
                .password(encodedPassword)
                .role(UserRole.ADMIN) // 관리자 역할 설정
                .userStatus(true) // 기본 사용자 상태 설정
                .build();

        try {
            userRepository.save(userEntity); // 사용자 저장
        } catch (Exception e) {
            // 로그 기록 및 예외 처리
            e.printStackTrace();
            return false; // 저장 실패 시 false 반환
        }

        return true;
    }






    // 로그인
    public boolean authenticate(String userId, String password) {
        System.out.println("Authenticating admin: " + userId);
        // userRepository에서 userId로 사용자를 찾음
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElse(null);

        if (userEntity != null && userEntity.getPassword() != null) {
            boolean passwordMatch = passwordEncoder.matches(password, userEntity.getPassword());
            boolean isAdmin = userEntity.getRole() == UserRole.ADMIN;
            System.out.println("Password match: " + passwordMatch + ", Is Admin: " + isAdmin);
            return passwordMatch && isAdmin;
        }

        System.out.println("User not found or password is null");
        return false;
    }


    // Refresh 토큰 무효화 메서드
    public boolean invalidateRefreshToken(String userId) {
        return userRepository.findByUserId(userId)
                .map(user -> {
                    user.setRefreshToken(""); // 토큰 무효화
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    //관리자 로그인
    public boolean authenticateAdmin(String userId, String password) {
        // userRepository에서 userId로 사용자를 찾음
        UserEntity userEntity = userRepository.findByUserId(userId).orElse(null);

        // 사용자 정보가 존재하는지 확인
        if (userEntity != null && userEntity.getPassword() != null) {
            // 입력된 비밀번호와 데이터베이스에 저장된 암호화된 비밀번호 비교
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                // 사용자 역할이 ADMIN인지 확인
                return userEntity.getRole() == UserRole.ADMIN;
            }
        }

        // 관리자가 아니거나 비밀번호가 잘못된 경우 인증 실패
        return false;
    }





    // 아이디/비밀번호 찾기
    // 이메일 인증 코드 생성
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    // 이메일 인증 코드 전송
    public void sendVerificationEmail(String email, String code) {
        String subject = "이메일 인증 코드";
        String text = "귀하의 인증 코드는 " + code + " 입니다. 이 코드는 30분 동안 유효합니다.";
        emailService.sendSimpleMessage(email, subject, text);
    }

    // 아이디 찾기 이메일 전송
    public void sendIdRecoveryEmail(String email, String userId) {
        String subject = "아이디 찾기";
        String text = "귀하의 아이디는 " + userId + " 입니다.";
        emailService.sendSimpleMessage(email, subject, text);
    }

    // 인증 코드 검증
    public void saveVerificationCode(String email, String code) {
        verificationCodes.put(email, new VerificationCode(code, LocalDateTime.now().plusMinutes(30)));
    }

    public boolean verifyCode(String email, String code) {
        VerificationCode savedCode = verificationCodes.get(email);
        if (savedCode != null && savedCode.getCode().equals(code) && LocalDateTime.now().isBefore(savedCode.getExpiryTime())) {
            verificationCodes.remove(email); // 인증 성공 시 코드를 삭제하여 재사용 방지
            return true;
        }
        return false;
    }

    // 인증 코드 전송 메서드
    public boolean sendVerificationCode(String email) {

        if (!isEmailRegistered(email)) {
            return false; // 등록된 이메일이 아니면 false 반환
        }
        String code = generateVerificationCode();
        sendVerificationEmail(email, code);
        saveVerificationCode(email, code);
        return true;
    }

    // 이메일 등록 여부 확인
    public boolean isEmailRegistered(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // 아이디 찾기
    public String findUserId(String email) {
        return userRepository.findByEmail(email)
                .map(UserEntity::getUserId)
                .orElse(null);
    }
    // 비밀번호 재설정
    public boolean resetPassword(String email, String newPassword) {
        // 비밀번호 유효성 검증 추가
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (newPassword == null || !newPassword.matches(passwordRegex)) {
            return false; // 비밀번호가 조건에 맞지 않으면 false 반환
        }

        return userRepository.findByEmail(email)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    //인증 코드 관리
    private static class VerificationCode {
        private String code;
        private LocalDateTime expiryTime;

        public VerificationCode(String code, LocalDateTime expiryTime) {
            this.code = code;
            this.expiryTime = expiryTime;
        }

        public String getCode() {
            return code;
        }

        public LocalDateTime getExpiryTime() {
            return expiryTime;
        }
    }
}



//    @Transactional
//    public boolean deactivateAccount(String userId) {
//        try {
//            // 1. 사용자 조회
//            UserEntity user = userRepository.findById(Integer.valueOf(userId))
//                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId));
//
//            // 2. 사용자 계정 비활성화
//            user.setActive(false);
//            // 3. 계정 비활성화 시간 기록
//            user.setDeactivatedAt(LocalDateTime.now());
//            // 4. 개인정보 보호를 위한 데이터 익명화
//            user.setEmail("deactivated_" + userId + "@example.com");
//            user.setPhoneNumber(null);
//            // 5. 변경된 사용자 정보 저장
//            userRepository.save(user);
//            // 6. 비활성화 성공 반환
//            return true;
//        } catch (Exception e) {
//            // 예외 처리: 로그 기록 및 적절한 예외 전환
//            // 예: Logger.error("계정 비활성화 중 오류 발생: ", e);
//            throw new RuntimeException("계정 비활성화 중 오류가 발생했습니다: " + e.getMessage());
//        }
//    }








