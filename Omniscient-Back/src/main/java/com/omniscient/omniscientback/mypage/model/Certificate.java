package com.omniscient.omniscientback.mypage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Certificate 엔티티 클래스
 * 사용자의 자격증 정보를 나타냅니다.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor  // 파라미터가 없는 디폴트 생성자를 자동으로 생성한다.
// 클래스에 명시적으로 선언된 생성자가 없더라도 인스턴스를 생성할 수 있다.
@AllArgsConstructor // 클래스의 모든 필드 값을 파라미터로 받는 생성자를 자동으로 생성한다.
// 이 어노테이션을 사용하면, 클래스의 모든 필드를 한 번에 초기화할 수 있다.
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 자격증 고유 식별자

    private String name;  // 자격증 이름

    private String date;  // 자격증 취득일 (문자열 형식)

    private String issuer;  // 발급 기관

    private String number;  // 자격증 번호

    private Boolean isActive = true;  // 자격증 상태 (활성화/비활성화)

    // 기본 생성자, getter, setter 메소드는 Lombok에 의해 자동 생성됩니다.
}