package com.omniscient.omniscientback.curation.service;

import com.omniscient.omniscientback.curation.model.Curation;
import com.omniscient.omniscientback.curation.repository.CurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CurationService {

    private CurationRepository repository;

    @Autowired
    public CurationService(CurationRepository repository) {
        this.repository = repository;
    }

    // 전체조회 서비스
    public List<Curation> findCuration(){
        // 1번 관리자가 선택한 큐레이션 번호를 조회함
        //  ex)1,2,3,4
        List<Integer> list = Arrays.asList(1,2,3,4);

        // 2번 1번에서 조회한 값을 기준으로 curation 데이터를 조회한다.
        List<Curation> results =  repository.findAllById(list);

        // 검증로직 추가 예정
        return results;
    }

    // id 값으로 조회
    public Optional<Curation> findById(Integer id) {
        return repository.findById(id);
    }

    // 저장
    public Curation save(Curation curation) {
        return repository.save(curation);
    }

    // 삭제 status 값 변경으로 바꾸기
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    // 검색 기능 추가
    public List<Curation> searchPosts(String term) {
        return repository.findByCurationTitleContainingOrCurationContentsContaining(term, term);
    }



}
