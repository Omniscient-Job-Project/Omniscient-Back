package com.omniscient.omniscientback.api.jobApi;

import com.omniscient.omniscientback.api.jobApi.controller.JobabaApiController;
import com.omniscient.omniscientback.api.jobApi.model.JobTotalDTO;
import com.omniscient.omniscientback.api.jobApi.model.JobabaEntity;
import com.omniscient.omniscientback.api.jobApi.service.JobabaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JobabaApiControllerTest {

    private MockMvc mockMvc;

    @Mock
    private JobabaService jobabaService;

    @InjectMocks
    private JobabaApiController jobabaApiController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jobabaApiController).build();
    }

    @Test
    public void 잡아바api전체조회테스트() throws Exception { // /api/v1/jobaba/jobinfo 엔드포인트로 GET 요청을 보내고, 그 결과가 400 Bad Request로 반환되는지를 확인하는 테스트 코드
        mockMvc.perform(get("/api/v1/jobaba/jobinfo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input: param is required."));
    }

}