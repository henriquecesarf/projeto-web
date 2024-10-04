package com.seuprojeto.projeto_web.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seuprojeto.projeto_web.entities.ClientEntity;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.repositories.ClientRepository;
import com.seuprojeto.projeto_web.requests.ClientRequest;
import com.seuprojeto.projeto_web.validators.CPFValidator;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    ModelMapper modelMapper = new ModelMapper();

    public List<ClientEntity> findAllClients() {
        List<ClientEntity> clientEntity = clientRepository.findAll();
        if(clientEntity.isEmpty()){
            throw new TableEmptyException("No data registered");
        }
        return clientEntity;
    }

    public ClientRequest createCategory(ClientRequest clientRequest) {
        if (!CPFValidator.isValidCPF(clientRequest.getCpf())) {
            throw new IllegalArgumentException("CPF inválido!");
        }

        ClientEntity clientEntity = modelMapper.map(clientRequest, ClientEntity.class);
        clientRepository.save(clientEntity);
        return modelMapper.map(clientEntity, ClientRequest.class);
    }

}
