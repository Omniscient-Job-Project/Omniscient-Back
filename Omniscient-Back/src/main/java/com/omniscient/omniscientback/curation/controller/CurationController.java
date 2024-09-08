package com.omniscient.omniscientback.curation.controller;

import com.omniscient.omniscientback.curation.model.Curation;
import com.omniscient.omniscientback.curation.model.CurationDTO;
import com.omniscient.omniscientback.curation.service.CurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/curation")
@CrossOrigin(origins = "http://localhost:8083/curation") // 프론트엔드 서버 주소
public class CurationController {

    private CurationService curationService;

    @Autowired
    public CurationController(CurationService curationService) {
        this.curationService = curationService;
    }

    // 등록
    @PostMapping("/save")
    public ResponseEntity<Curation> saveCuration(@RequestBody CurationDTO dto) {
        Curation item = new Curation();
        item.setCurationId(dto.getCurationId());
        item.setUserId(dto.getUserId());
        item.setCommentsId(dto.getCommentsId());
        item.setCurationTitle(dto.getCurationTitle());
        item.setCurationContents(dto.getCurationContents());
        item.setImagePath(dto.getImagePath());
        item.setCurationDate(dto.getCurationDate());
        item.setViewCount(dto.getViewCount());
        item.setCurationStatus(dto.isCurationStatus());

        Curation savedItem = curationService.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<Curation>> getAllItems() {
        List<Curation> items = curationService.findCuration();
        return ResponseEntity.ok(items);
    }

    // id 값으로 상세조회
    @GetMapping("/{id}")
    public ResponseEntity<Curation> getItemById(@PathVariable Integer id) {
        Optional<Curation> item = curationService.findById(id);

        if (item.isPresent()) {
            return new ResponseEntity<>(item.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
