package com.omniscient.omniscientback.api.jobApi;

import com.omniscient.omniscientback.api.jobApi.controller.JobabaApiController;
import com.omniscient.omniscientback.api.jobApi.model.JobTotalDTO;
import com.omniscient.omniscientback.api.jobApi.model.JobabaEntity;
import com.omniscient.omniscientback.api.jobApi.service.JobabaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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
    public void 잡아바api전체조회테스트() throws Exception {
        mockMvc.perform(get("/api/v1/jobaba/jobinfo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input: param is required.")); // 수정된 오류 메시지 확인
    }


    @Test
    public void 잡아바디테일조회테스트() throws Exception {
        String jobId = "someJobId"; // 테스트할 Job ID

        JobabaEntity jobabaEntity = new JobabaEntity();
        jobabaEntity.setJobabaCompanyName("Test Company");
        jobabaEntity.setJobabaInfoTitle("Test Title");

        when(jobabaService.getJobById(jobId)).thenReturn(Optional.of(jobabaEntity));

        mockMvc.perform(get("/api/v1/jobaba/jobinfo/{Id}", jobId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"jobabaCompanyName\":\"Test Company\", \"jobabaInfoTitle\":\"Test Title\"}"));
    }


}
