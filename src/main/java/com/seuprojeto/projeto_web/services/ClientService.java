package com.seuprojeto.projeto_web.services;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.seuprojeto.projeto_web.entities.ClientEntity;
import com.seuprojeto.projeto_web.enums.CnhCategory;
import com.seuprojeto.projeto_web.enums.Sexo;
import com.seuprojeto.projeto_web.exceptions.DuplicateRegisterException;
import com.seuprojeto.projeto_web.exceptions.FieldInvalidException;
import com.seuprojeto.projeto_web.exceptions.FieldNotFoundException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.repositories.ClientRepository;
import com.seuprojeto.projeto_web.requests.ClientRequest;
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private RestTemplate restTemplate;

    public List<ClientRequest> findAllClients() {
        List<ClientEntity> clientEntities = clientRepository.findByStExcluidoFalse();
    
        if (clientEntities.isEmpty()) {
            throw new TableEmptyException("No data registered");
        }
    
        ModelMapper modelMapper = new ModelMapper();

        List<ClientRequest> clientRequests = clientEntities.stream()
                .map(entity -> modelMapper.map(entity, ClientRequest.class))
                .collect(Collectors.toList());
    
        return clientRequests;
    }

    public ClientRequest findClientById(Long id) {

        ClientEntity clientEntity = clientRepository.findByIdAndStExcluidoFalse(id);
        
        if (clientEntity == null) {
            throw new FieldNotFoundException("Client with ID " + id + " not found");
        }
        
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(clientEntity, ClientRequest.class);

    }
    

    public ClientRequest createClient(ClientRequest clientRequest) {

        validateCep(clientRequest.getCep());

        boolean clientExists = clientRepository.findByEmail(clientRequest.getEmail()) != null ||
        clientRepository.findByCpf(clientRequest.getCpf()) != null ||
        clientRepository.findByCnh(clientRequest.getCnh()) != null;
                               
        if (clientExists) {
            throw new DuplicateRegisterException("Cliente já cadastrado.");
        }

        ClientEntity clientEntity = modelMapper.map(clientRequest, ClientEntity.class);
        clientRepository.save(clientEntity);
        return modelMapper.map(clientEntity, ClientRequest.class);
    }

    public void deleteClientbyId(Long id) {
        Optional<ClientEntity> clientOptional = clientRepository.findById(id);
        
        if (clientOptional.isEmpty()) {
            throw new FieldNotFoundException("Cliente com ID " + id + " não encontrado");
        }

        ClientEntity clientEntity = clientOptional.get();

        // Pseudonimização dos dados
        clientEntity.setName("*****");
        clientEntity.setSurname("*****");
        clientEntity.setCpf("XXX.XXX.XXX-XX");
        clientEntity.setEmail("pseudonimizado@email.com");
        clientEntity.setSexo(Sexo.X);
        clientEntity.setDtNascimento(LocalDate.of(1900, 1, 1));
        clientEntity.setCnh("XXXXXXXXXXX");
        clientEntity.setCnhDtMaturity(LocalDate.of(1900, 1, 1));
        clientEntity.setCep("XXXXX-XXX");
        clientEntity.setAddress("Endereço Pseudonimizado");
        clientEntity.setComplement("Complemento Pseudonimizado");

        clientEntity.setStExcluido(true);

        // Salva a entidade pseudonimizada
        clientRepository.save(clientEntity);
    }

    public ClientRequest updateClientById(Long id, ClientRequest clientRequest) {
        Optional<ClientEntity> clientOptional = clientRepository.findById(id);
        
        if (clientOptional.isEmpty()) {
            throw new FieldNotFoundException("Cliente com ID " + id + " não encontrado");
        }

        ClientEntity clientEntity = clientOptional.get();

        modelMapper.map(clientRequest, clientEntity);

        clientRepository.save(clientEntity);

        return modelMapper.map(clientEntity, ClientRequest.class);
    }

    public ClientRequest partialUpdateClientById(Long id, Map<String, Object> updates) {
        Optional<ClientEntity> clientOptional = clientRepository.findById(id);
        
        if (clientOptional.isEmpty()) {
            throw new FieldNotFoundException("Cliente com ID " + id + " não encontrado");
        }

        ClientEntity clientEntity = clientOptional.get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    clientEntity.setName((String) value);
                    break;
                case "surname":
                    clientEntity.setSurname((String) value);
                    break;
                case "cpf":
                    clientEntity.setCpf((String) value);
                    break;
                case "email":
                    clientEntity.setEmail((String) value);
                    break;
                case "sexo":
                    clientEntity.setSexo(Sexo.valueOf((String) value));
                    break;
                case "dtNascimento":
                    clientEntity.setDtNascimento(LocalDate.parse((String) value));
                    break;
                case "cnh":
                    clientEntity.setCnh((String) value);
                    break;
                case "cnhCategory":
                    clientEntity.setCnhCategory(CnhCategory.valueOf((String) value));
                    break;
                case "cnhDtMaturity":
                    clientEntity.setCnhDtMaturity(LocalDate.parse((String) value));
                    break;
                case "cep":
                    clientEntity.setCep((String) value);
                    break;
                case "address":
                    clientEntity.setAddress((String) value);
                    break;
                case "country":
                    clientEntity.setCountry((String) value);
                    break;
                case "city":
                    clientEntity.setCity((String) value);
                    break;
                case "state":
                    clientEntity.setState((String) value);
                    break;
                case "complement":
                    clientEntity.setComplement((String) value);
                    break;
            }
        });

        clientRepository.save(clientEntity);
        return modelMapper.map(clientEntity, ClientRequest.class);
    }

    public void validateCep(String cep) {

        if (!Pattern.matches("\\d{8}", cep)) {
            throw new FieldInvalidException("O Cep deve conter apenas 8 caracteres, sendo eles números");
        }

        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getBody().contains("\"erro\": \"true\"")) {
            throw new FieldInvalidException("CEP inválido: " + cep);
        }
    }

}
