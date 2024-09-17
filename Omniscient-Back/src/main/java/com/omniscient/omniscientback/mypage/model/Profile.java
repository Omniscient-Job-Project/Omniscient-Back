package com.omniscient.omniscientback.mypage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

/**
 * Profile 엔티티 클래스
 * 사용자 프로필 정보를 나타냅니다.
 */
@Entity
@NoArgsConstructor  // 파라미터가 없는 디폴트 생성자를 자동으로 생성한다.
                    //클래스에 명시적으로 선언된 생성자가 없더라도 인스턴스를 생성할 수 있다.
@AllArgsConstructor // 클래스의 모든 필드 값을 파라미터로 받는 생성자를 자동으로 생성한다.
                    // 이 어노테이션을 사용하면, 클래스의 모든 필드를 한 번에 초기화할 수 있다.
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 프로필 고유 식별자
    private String name;  // 사용자 이름
    private String jobTitle;  // 직책
    private String email;  // 이메일 주소
    private String phone;  // 전화번호
    private Integer age;  // 나이
    private String address;  // 주소
    private Boolean status = true;  // 프로필 상태 (활성화/비활성화)

    @ElementCollection
        // 1대 N 관계를 사용하기 위해
    private List<String> certificates = new ArrayList<>();  // 자격증 목록

    public List<String> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<String> certificates) {
        this.certificates = certificates;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", certificates=" + certificates +
                '}';
    }
}