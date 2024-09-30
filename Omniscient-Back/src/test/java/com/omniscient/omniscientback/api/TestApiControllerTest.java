package com.omniscient.omniscientback.api;

import com.omniscient.omniscientback.api.testapi.controller.TestApiController;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
//시험일정 조회 테스틐  코드
public class TestApiControllerTest {

    private MockMvc mockMvc;

    @Value("${api_grade.key}")
    private String apiTestKey; // API 키 주입

    @PostConstruct
    public void init() {
        System.out.println("API Key: " + apiTestKey);
    }

    @InjectMocks
    private TestApiController testApiController;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(testApiController).build();
    }

    @Test
    void 외부_ApiKey키가_true일때() {
        // 'apiTestKey'에 주입된 값이 올바른지 확인
        assertThat(apiTestKey).isEqualTo("testApiKey");
    }

    @Test
    void 외부_ApiKey가_null_일때() {
        // 실제 값이 null 일때
        assertThatThrownBy(() -> {
            if (apiTestKey == null) {
                throw new NullPointerException("API 키가 null 값입니다.");
            }
        }).isInstanceOf(NullPointerException.class)
                .hasMessageContaining("API 키가 null 값입니다.");
    }


    @Test
    void 외부_ApiKey가_잘못_주입_됐을때() {
        // 기대하는 값과 실제 값이 다를 때
        assertThatThrownBy(() -> {
            // apiTestKey가 기대하는 값과 일치하지 않을 경우
            if (!apiTestKey.equals("testApiKey")) {
                throw new AssertionError("API 키가 올바르지 않습니다. 실제 값: " + apiTestKey);
            }
        }).isInstanceOf(AssertionError.class)
                .hasMessageContaining("API 키가 올바르지 않습니다.");
    }


}
