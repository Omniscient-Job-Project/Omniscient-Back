package com.omniscient.omniscientback.api.categoryapi.service;

import com.omniscient.omniscientback.api.categoryapi.model.CategoryDTO;
import com.omniscient.omniscientback.api.categoryapi.model.CategoryEntity;
import com.omniscient.omniscientback.api.categoryapi.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void saveCategory(CategoryDTO categoryDTO) {
        try {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setJmfldnm(categoryDTO.getJmfldnm());
            categoryEntity.setInfogb(categoryDTO.getInfogb());
            categoryEntity.setContents(categoryDTO.getContents());
            categoryEntity.setObligfldcd(categoryDTO.getObligfldcd());
            categoryEntity.setObligfldnm(categoryDTO.getObligfldnm());
            categoryEntity.setMdobligfldcd(categoryDTO.getMdobligfldcd());
            categoryEntity.setMdobligfldnm(categoryDTO.getMdobligfldnm());

            categoryRepository.save(categoryEntity);
            System.out.println("Saved CategoryEntity: " + categoryEntity);
        } catch (Exception e) {
            System.err.println("Error saving category: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<CategoryEntity> getAllCategory() { return categoryRepository.findAll(); }
    public Optional<CategoryEntity> getCategoryById(Integer id) { return categoryRepository.findById(id); }
}
