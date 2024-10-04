package com.seuprojeto.projeto_web.services;

import java.util.List;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seuprojeto.projeto_web.entities.ClientEntity;
import com.seuprojeto.projeto_web.exceptions.FieldNotFoundException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.repositories.ClientRepository;
import com.seuprojeto.projeto_web.requests.ClientRequest;
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

    public ClientRequest findClientById(Long id){
        Optional<ClientEntity> clientOptional = clientRepository.findById(id);
        if(clientOptional.isEmpty()){
            throw new FieldNotFoundException("Client with ID " + id + " not found");
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(clientOptional.get(), ClientRequest.class);
    }

    public ClientRequest createCategory(ClientRequest clientRequest) {
        ClientEntity clientEntity = modelMapper.map(clientRequest, ClientEntity.class);
        clientRepository.save(clientEntity);
        return modelMapper.map(clientEntity, ClientRequest.class);
    }

}
