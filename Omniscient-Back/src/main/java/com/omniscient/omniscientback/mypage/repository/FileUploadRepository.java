package com.omniscient.omniscientback.mypage.repository;

import com.omniscient.omniscientback.mypage.model.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 파일 업로드 정보에 대한 데이터베이스 작업을 처리하는 레포지토리
 */
@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Integer> {

    /**
     * 활성화된 모든 파일을 조회합니다.
     *
     * @return 활성화된 파일 목록
     */
    List<FileUpload> findAllByActiveTrue();

    /**
     * 주어진 ID의 활성화된 파일을 조회합니다.
     *
     * @param id 파일 ID
     * @return 활성화된 파일 (Optional)
     */
    Optional<FileUpload> findByIdAndActiveTrue(Integer id);

    /**
     * 주어진 파일 이름의 활성화된 파일을 조회합니다.
     *
     * @param fileName 파일 이름
     * @return 활성화된 파일 (Optional)
     */
    Optional<FileUpload> findByFileNameAndActiveTrue(String fileName);

    /**
     * 활성화된 파일의 총 개수를 반환합니다.
     *
     * @return 활성화된 파일의 총 개수
     */
    Integer countByActiveTrue();
}