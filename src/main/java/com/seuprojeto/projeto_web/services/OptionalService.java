package com.seuprojeto.projeto_web.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seuprojeto.projeto_web.entities.OptionalEntity;
import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.repositories.OptionalRepository;
import com.seuprojeto.projeto_web.requests.OptionalRequest;

@Service
public class OptionalService {
    @Autowired
    private OptionalRepository optionalRepository;

    public List<OptionalEntity> findAllOptionals() {
        List<OptionalEntity> optionalEntity = optionalRepository.findAll();
        if(optionalEntity.isEmpty()){
            throw new TableEmptyException("No data registered");
        }
        return optionalEntity;
    }

    public OptionalRequest findOptionalById(Long id){
        Optional<OptionalEntity> optionalOptional = optionalRepository.findById(id);
        if(optionalOptional.isEmpty()){
            throw new EntityNotFoundException("Optional with ID " + id + " not found");
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(optionalOptional.get(), OptionalRequest.class);
    }

}
