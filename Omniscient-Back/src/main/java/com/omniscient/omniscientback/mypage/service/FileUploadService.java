package com.omniscient.omniscientback.mypage.service;

import com.omniscient.omniscientback.mypage.model.FileUpload;
import com.omniscient.omniscientback.mypage.model.FileUploadDTO;
import com.omniscient.omniscientback.mypage.repository.FileUploadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileUploadService {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);

    @Value("${spring.file.upload-dir}")
    private String uploadDir;

    @Value("${spring.file.base-url}")
    private String fileBaseUrl;

    private Path fileStorageLocation;
    private final FileUploadRepository fileUploadRepository;

    @Autowired
    public FileUploadService(FileUploadRepository fileUploadRepository) {
        this.fileUploadRepository = fileUploadRepository;
    }

    public void init() {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("업로드된 파일을 저장할 디렉토리를 생성할 수 없습니다.", ex);
        }
    }

    @Transactional
    public FileUploadDTO uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 비어있습니다.");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

        try {
            if (fileName.contains("..")) {
                throw new IllegalArgumentException("파일명에 부적절한 문자가 포함되어 있습니다: " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            FileUpload fileUpload = new FileUpload();
            fileUpload.setFileName(uniqueFileName);
            fileUpload.setOriginalFileName(fileName);
            fileUpload.setContentType(file.getContentType());
            fileUpload.setSize((int) file.getSize());
            fileUpload.setUploadDateTime(LocalDateTime.now());
            fileUpload.setActive(true);

            FileUpload savedFileUpload = fileUploadRepository.save(fileUpload);
            logger.info("파일 업로드 성공: {}", uniqueFileName);
            return convertToDTO(savedFileUpload);
        } catch (IOException ex) {
            logger.error("파일 {} 저장 중 오류 발생", fileName, ex);
            throw new IOException("파일 " + fileName + " 저장 중 오류가 발생했습니다.", ex);
        }
    }

    @Transactional(readOnly = true)
    public List<FileUploadDTO> getAllActiveFiles() {
        List<FileUpload> activeFiles = fileUploadRepository.findAllByActiveTrue();
        logger.info("활성화된 파일 {} 개 조회됨", activeFiles.size());
        return activeFiles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FileUploadDTO getFileById(Integer id) {
        FileUpload fileUpload = fileUploadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + id + "인 파일을 찾을 수 없습니다."));
        logger.info("ID {}인 파일 조회됨: {}", id, fileUpload.getFileName());
        return convertToDTO(fileUpload);
    }

    @Transactional
    public FileUploadDTO updateFile(Integer id, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("업데이트할 파일이 비어있습니다.");
        }

        FileUpload existingFile = fileUploadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + id + "인 파일을 찾을 수 없습니다."));

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

        try {
            if (fileName.contains("..")) {
                throw new IllegalArgumentException("파일명에 부적절한 문자가 포함되어 있습니다: " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Files.deleteIfExists(this.fileStorageLocation.resolve(existingFile.getFileName()));

            existingFile.setFileName(uniqueFileName);
            existingFile.setOriginalFileName(fileName);
            existingFile.setContentType(file.getContentType());
            existingFile.setSize((int) file.getSize());
            existingFile.setUploadDateTime(LocalDateTime.now());

            FileUpload updatedFileUpload = fileUploadRepository.save(existingFile);
            logger.info("ID {}인 파일 업데이트 성공: {}", id, uniqueFileName);
            return convertToDTO(updatedFileUpload);
        } catch (IOException ex) {
            logger.error("파일 {} 업데이트 중 오류 발생", fileName, ex);
            throw new IOException("파일 " + fileName + " 업데이트 중 오류가 발생했습니다.", ex);
        }
    }

    @Transactional
    public void deactivateFile(Integer id) {
        FileUpload fileUpload = fileUploadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + id + "인 파일을 찾을 수 없습니다."));
        fileUpload.setActive(false);
        fileUploadRepository.save(fileUpload);
        logger.info("ID {}인 파일 비활성화 성공", id);
    }

    public Resource loadFileAsResource(String fileName) throws IOException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                logger.error("파일을 찾을 수 없습니다: {}", fileName);
                throw new IOException("파일을 찾을 수 없습니다: " + fileName);
            }
        } catch (MalformedURLException ex) {
            logger.error("파일 URL 생성 중 오류 발생: {}", fileName, ex);
            throw new IOException("파일을 찾을 수 없습니다: " + fileName, ex);
        }
    }

    private FileUploadDTO convertToDTO(FileUpload fileUpload) {
        FileUploadDTO dto = new FileUploadDTO(
                fileUpload.getId(),
                fileUpload.getFileName(),
                fileUpload.getOriginalFileName(),
                fileUpload.getContentType(),
                fileUpload.getSize(),
                fileUpload.getUploadDateTime(),
                fileUpload.isActive()
        );
        dto.setFileUrl(createFileUrl(fileUpload.getFileName()));
        return dto;
    }

    private String createFileUrl(String fileName) {
        return ServletUriComponentsBuilder.fromUriString(fileBaseUrl)
                .path("/")
                .path(fileName)
                .toUriString();
    }
}