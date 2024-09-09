package com.omniscient.omniscientback.mypage.model;

import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
public class ProfileDTO {
    private Integer id;
    private String name;
    private String jobTitle;
    private String email;
    private String phone;
    private Integer age;
    private String address;
    private List<String> certificates;

    @JsonIgnore
    private byte[] profileImage;

    // Integer 타입의 id를 받는 setter
    public void setId(Integer id) {
        this.id = id;
    }

    // String 타입의 id를 받는 setter (기존 코드)
    public void setId(String id) {
        if (id != null && !id.equals("null")) {
            this.id = Integer.parseInt(id);
        } else {
            this.id = null;
        }
    }
}