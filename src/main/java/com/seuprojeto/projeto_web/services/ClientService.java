package com.seuprojeto.projeto_web.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;  // Import para o cache
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.seuprojeto.projeto_web.entities.ClientEntity;
import com.seuprojeto.projeto_web.enums.CnhCategory;
import com.seuprojeto.projeto_web.enums.Sexo;
import com.seuprojeto.projeto_web.exceptions.DuplicateRegisterException;
import com.seuprojeto.projeto_web.exceptions.FieldInvalidException;
import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.repositories.ClientRepository;
import com.seuprojeto.projeto_web.repositories.RentalRepository;
import com.seuprojeto.projeto_web.requests.ClientRequest;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RentalRepository rentalRepository;

    @Cacheable(value = "clients", key = "'all_clients'")  // Cache para todos os clientes
    public List<ClientRequest> findAllClients() {
        List<ClientEntity> clientEntities = clientRepository.findByStExcluidoFalse();

        if (clientEntities.isEmpty()) {
            throw new TableEmptyException("No data registered");
        }

        return clientEntities.stream()
                .map(entity -> modelMapper.map(entity, ClientRequest.class))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "clients", key = "#id")  // Cache para cliente por ID
    public ClientRequest findClientById(Long id) {

        ClientEntity clientEntity = clientRepository.findByIdAndStExcluidoFalse(id);

        if (clientEntity == null) {
            throw new EntityNotFoundException("Client with ID " + id + " not found");
        }

        return modelMapper.map(clientEntity, ClientRequest.class);
    }

    @CacheEvict(value = "clients", allEntries = true)
    public ClientRequest createClient(ClientRequest clientRequest) {
        validateCep(clientRequest.getCep());

        boolean clientExists = clientRepository.findByEmail(clientRequest.getEmail()) != null ||
                clientRepository.findByCpf(clientRequest.getCpf()) != null;
                clientRepository.findByCnh(clientRequest.getCnh());

        if (clientExists) {
            throw new DuplicateRegisterException("Customer already registered.");
        }

        ClientEntity clientEntity = modelMapper.map(clientRequest, ClientEntity.class);
        clientRepository.save(clientEntity);
        return modelMapper.map(clientEntity, ClientRequest.class);
    }

    @CacheEvict(value = "clients", allEntries = true)
    public void deleteClientbyId(Long id) {
        Optional<ClientEntity> clientOptional = clientRepository.findById(id);

        if (clientOptional.isEmpty()) {
            throw new EntityNotFoundException("Customer with ID " + id + " not found");
        }

        // Verifica se o cliente possui locações ativas
        if (rentalRepository.existsByClientIdAndIsActiveTrue(id)) {
            throw new FieldInvalidException("Unable to remove client with active lease.");
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
        clientEntity.setAddress("Pseudonymized Address");
        clientEntity.setComplement("Pseudonymized Address");

        clientEntity.setStExcluido(true);

        // Salva a entidade pseudonimizada
        clientRepository.save(clientEntity);
    }

    @Cacheable(value = "clients", key = "#id")  // Cache para atualização parcial
    public ClientRequest updateClientById(Long id, ClientRequest clientRequest) {
        Optional<ClientEntity> clientOptional = clientRepository.findById(id);

        if (clientOptional.isEmpty()) {
            throw new EntityNotFoundException("Customer with ID " + id + " not found");
        }

        ClientEntity clientEntity = clientOptional.get();

        modelMapper.map(clientRequest, clientEntity);

        clientRepository.save(clientEntity);

        return modelMapper.map(clientEntity, ClientRequest.class);
    }

    @CachePut(value = "clients", key = "#client.id") // Cache para atualização parcial
    public ClientRequest partialUpdateClientById(Long id, Map<String, Object> updates) {
        Optional<ClientEntity> clientOptional = clientRepository.findById(id);

        if (clientOptional.isEmpty()) {
            throw new EntityNotFoundException("Customer with ID " + id + " not found");
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
            throw new FieldInvalidException("The zip code must contain only 8 characters, which must be numbers.");
        }

        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getBody().contains("\"erro\": \"true\"")) {
            throw new FieldInvalidException("Invalid zip code: " + cep);
        }
    }
}
