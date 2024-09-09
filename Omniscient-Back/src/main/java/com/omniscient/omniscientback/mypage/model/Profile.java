package com.omniscient.omniscientback.mypage.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String name;
    private String jobTitle;
    private String email;
    private String phone;
    private Integer age;
    private String address;

    @ElementCollection
    private List<String> certificates = new ArrayList<>();

    @Lob
    private byte[] profileImage;
}