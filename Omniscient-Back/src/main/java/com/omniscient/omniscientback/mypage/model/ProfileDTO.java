package com.omniscient.omniscientback.mypage.model;

import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ProfileDTO 클래스
 * 프로필 정보를 전송하기 위한 데이터 전송 객체입니다.
 */
@Data
public class ProfileDTO {
    private Integer id;  // 프로필 고유 식별자
    private String name;  // 사용자 이름
    private String jobTitle;  // 직책
    private String email;  // 이메일 주소
    private String phone;  // 전화번호
    private Integer age;  // 나이
    private String address;  // 주소
    private List<String> certificates;  // 자격증 목록
    private Boolean status;  // 프로필 상태 (활성화/비활성화)

    @JsonIgnore
    private byte[] profileImage;  // 프로필 이미지 (JSON 직렬화에서 제외)

    /**
     * Integer 타입의 id를 설정합니다.
     * @param id 설정할 id 값
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * String 타입의 id를 Integer로 변환하여 설정합니다.
     * @param id 설정할 id 값 (문자열)
     */
    public void setId(String id) {
        if (id != null && !id.equals("null")) {
            this.id = Integer.parseInt(id);
        } else {
            this.id = null;
        }
    }

    // Lombok의 @Data 어노테이션에 의해 다른 필드들의 getter와 setter가 자동 생성됩니다.
}