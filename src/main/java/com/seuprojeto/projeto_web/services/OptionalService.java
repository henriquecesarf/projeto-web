package com.seuprojeto.projeto_web.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import com.seuprojeto.projeto_web.entities.OptionalEntity;
import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.repositories.OptionalRepository;
import com.seuprojeto.projeto_web.requests.OptionalRequest;

@Service
@EnableCaching
public class OptionalService {
    @Autowired
    private OptionalRepository optionalRepository;

    @Cacheable(value = "optionals", key = "'all_optionals'")
    public List<OptionalEntity> findAllOptionals() {
        List<OptionalEntity> optionalEntity = optionalRepository.findAll();
        if (optionalEntity.isEmpty()) {
            throw new TableEmptyException("No data registered");
        }
        return optionalEntity;
    }

    @Cacheable(value = "optionals", key = "#id")
    public OptionalRequest findOptionalById(Long id) {
        Optional<OptionalEntity> optionalOptional = optionalRepository.findById(id);
        if (optionalOptional.isEmpty()) {
            throw new EntityNotFoundException("Optional with ID " + id + " not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(optionalOptional.get(), OptionalRequest.class);
    }

    @CacheEvict(value = "optionals", allEntries = true)
    public OptionalEntity createOptional(OptionalEntity optionalEntity) {
        return optionalRepository.save(optionalEntity);
    }

    @CacheEvict(value = "optionals", key = "#id")
    public void deleteOptionalById(Long id) {
        Optional<OptionalEntity> optionalOptional = optionalRepository.findById(id);
        if (optionalOptional.isEmpty()) {
            throw new EntityNotFoundException("Optional with ID " + id + " not found");
        }
        optionalRepository.deleteById(id);
    }

    @CachePut(value = "optionals", key = "#optionalEntity.id")
    public OptionalEntity updateOptional(Long id, OptionalEntity optionalEntity) {
        Optional<OptionalEntity> existingOptional = optionalRepository.findById(id);
        if (existingOptional.isEmpty()) {
            throw new EntityNotFoundException("Optional with ID " + id + " not found");
        }
        optionalEntity.setId(id);  // Ensure the entity's ID remains consistent
        return optionalRepository.save(optionalEntity);
    }
}
