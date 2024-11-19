package com.seuprojeto.projeto_web.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.repositories.SinisterRepository;
import com.seuprojeto.projeto_web.requests.SinisterRequest;
import com.seuprojeto.projeto_web.entities.SinisterEntity;

@Service
public class SinisterService {

    @Autowired
    private SinisterRepository sinisterRepository;


    public List<SinisterEntity> findAllSinisters() {
        List<SinisterEntity> sinisterEntity = sinisterRepository.findAll();
        if(sinisterEntity.isEmpty()){
            throw new TableEmptyException("No data registered");
        }
        return sinisterEntity;
    }

    public SinisterRequest findSinisterById(Long id){
        Optional<SinisterEntity> sinisterOptional = sinisterRepository.findById(id);
        if(sinisterOptional.isEmpty()){
            throw new EntityNotFoundException("Sinister with ID " + id + " not found");
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sinisterOptional.get(), SinisterRequest.class);
    }

}
