package com.seuprojeto.projeto_web.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seuprojeto.projeto_web.entities.ClientEntity;
import com.seuprojeto.projeto_web.entities.OptionalEntity;
import com.seuprojeto.projeto_web.entities.RentalEntity;
import com.seuprojeto.projeto_web.entities.RentalSinister;
import com.seuprojeto.projeto_web.entities.SinisterEntity;
import com.seuprojeto.projeto_web.entities.VehicleEntity;
import com.seuprojeto.projeto_web.exceptions.DuplicateRegisterException;
import com.seuprojeto.projeto_web.exceptions.EntityNotFoundException;
import com.seuprojeto.projeto_web.exceptions.FieldInvalidException;
import com.seuprojeto.projeto_web.exceptions.TableEmptyException;
import com.seuprojeto.projeto_web.repositories.ClientRepository;
import com.seuprojeto.projeto_web.repositories.OptionalRepository;
import com.seuprojeto.projeto_web.repositories.RentalRepository;
import com.seuprojeto.projeto_web.repositories.RentalSinisterRepository;
import com.seuprojeto.projeto_web.repositories.SinisterRepository;
import com.seuprojeto.projeto_web.repositories.VehicleRepository;
import com.seuprojeto.projeto_web.requests.RentalCheckoutRequest;
import com.seuprojeto.projeto_web.requests.RentalRequest;

@Service
public class RentalService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private OptionalRepository optionalRepository;
    @Autowired
    private SinisterRepository sinisterRepository ;
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private RentalSinisterRepository rentalSinisterRepository;
    
    ModelMapper modelMapper = new ModelMapper();
    ObjectMapper objectMapper = new ObjectMapper();

    public List<RentalRequest> findAllRentals() {
        List<RentalEntity> rentalEntities = rentalRepository.findAll();
    
        if (rentalEntities.isEmpty()) {
            throw new TableEmptyException("No data registered");
        }
        
        // Converte cada RentalEntity para RentalRequest e trata o campo 'optionals'
        List<RentalRequest> rentalRequests = rentalEntities.stream()
                .map(entity -> {
                    RentalRequest rentalRequest = modelMapper.map(entity, RentalRequest.class);
                    // Converte o campo 'optionals' de JSON para lista
                    rentalRequest.setOptionals(convertJsonToOptionals(entity.getOptionals()));
                    return rentalRequest;
                })
                .collect(Collectors.toList());
    
        return rentalRequests;
    }

    public RentalRequest findRentalById(Long id) {
        Optional<RentalEntity> rentalEntityOptional = rentalRepository.findById(id);
        
        if (rentalEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Rental with ID: " + id + " not found");
        }
        
        RentalEntity rentalEntity = rentalEntityOptional.get();

        RentalRequest rentalRequest = modelMapper.map(rentalEntity, RentalRequest.class);
    
        // Deserializa o campo optionals de JSON para lista
        rentalRequest.setOptionals(convertJsonToOptionals(rentalEntity.getOptionals()));

        return rentalRequest;
    }

    public RentalRequest createRental(RentalRequest rentalRequest) {
        RentalEntity rental = new RentalEntity();

        // Verificando se o cliente existe
        rental.setClient(clientRepository.findById(rentalRequest.getClientId())
            .orElseThrow(() -> new EntityNotFoundException("Client not found")));

        // Verificando se a CNH do cliente está vencida
        if (isCnhExpired(clientRepository.findById(rentalRequest.getClientId()))) {
            throw new DuplicateRegisterException("Client's CNH is expired");
        }

        if (isClientHasActiveRental(rentalRequest.getClientId())) {
            // Lógica se o cliente já possui aluguel ativo
            throw new DuplicateRegisterException("The client already has an active rental or has values pending.");
        }

        // Verificando se o veículo existe
        rental.setVehicle(vehicleRepository.findById(rentalRequest.getVehicleId())
            .orElseThrow(() -> new EntityNotFoundException("Vehicle not found")));

        // Verificando se o veículo existe
        rental.setVehicle(vehicleRepository.findByIdAndAvailableTrue(rentalRequest.getVehicleId())
        .orElseThrow(() -> new DuplicateRegisterException("Vehicle is blocked")));

        if (isVehicleRented(rentalRequest.getVehicleId())) {
            // Lógica se o veículo já estiver alugado
            throw new DuplicateRegisterException("Vehicle is already rented.");
        }

        List<RentalRequest.OptionalRequest> optionals = rentalRequest.getOptionals();

        double totalOptionalItemsValue = calculateValueTotalOptionals(rentalRequest.getOptionals());
        double totalAmount = totalOptionalItemsValue + (rental.getVehicle().getValuedaily() * rentalRequest.getTotalDays());
        
        if (optionals != null && !optionals.isEmpty()) {

            // Convertendo a lista de opcionais para JSON
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String optionalsJson = objectMapper.writeValueAsString(optionals);
                rental.setOptionals(optionalsJson);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to convert Optionals to JSON", e);
            }
        }

        for (RentalRequest.OptionalRequest optional : optionals) {
            OptionalEntity optionalEntity = optionalRepository.findById(optional.getOptionalId())
                .orElseThrow(() -> new EntityNotFoundException("Optional with ID: " + optional.getOptionalId() + " not found"));

            if (optional.getQuantity() <= 0) {
                throw new FieldInvalidException("Optional quantity must be greater than or equal to 0");
            }

            if (optionalEntity.getQtdAvailable() - optional.getQuantity() < 0) {
                throw new DuplicateRegisterException("Quantity of optional " + optional.getOptionalId() + " insufficient in stock.");
            }
            
        }

        rental.setRentalDateTimeStart(rentalRequest.getRentalDateTimeStart());
        rental.setDailyRate(rental.getVehicle().getValuedaily());
        rental.setTotalDays(rentalRequest.getTotalDays());
        rental.setTotalAmount(totalAmount);
        rental.setDepositAmount(rentalRequest.getDepositAmount());
        rental.setTotalOptionalItemsValue(calculateValueTotalOptionals(rentalRequest.getOptionals()));
        rental.setPlateVehicle(vehicleRepository.findPlateById(rentalRequest.getVehicleId()));
        rental.setInitialMileage(rentalRequest.getInitialMileage());
        rental.setActive(rentalRequest.isActive());
    
        rentalRepository.save(rental);
        
        updateOptionalQuantities(optionals, "subtraction");

        return rentalRequest;
    }

    public void deleteRentalbyId(Long id) {
        RentalEntity rentalEntity = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found."));

        boolean isRentalActive = rentalRepository.existsByIdAndIsActiveTrue(id);

        if (isRentalActive) {
            throw new DuplicateRegisterException("It is not possible to delete a rental active.");
        }


        rentalRepository.delete(rentalEntity);
    }

    public RentalRequest updateRentalById(Long id, RentalRequest rentalRequest) {
        RentalEntity rentalEntity = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental with ID " + id + " not found"));
    
        // Atualizando informações da locação
        rentalEntity.setClient(clientRepository.findById(rentalRequest.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found")));
        rentalEntity.setVehicle(vehicleRepository.findById(rentalRequest.getVehicleId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found")));
    
        if (rentalRequest.getOptionals() != null && !rentalRequest.getOptionals().isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String optionalsJson = objectMapper.writeValueAsString(rentalRequest.getOptionals());
                rentalEntity.setOptionals(optionalsJson);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to convert Optionals to JSON", e);
            }
        }
    
        rentalEntity.setRentalDateTimeStart(rentalRequest.getRentalDateTimeStart());
        // rentalEntity.setRentalDateTimeEnd(rentalRequest.getRentalDateTimeEnd());
        rentalEntity.setDailyRate(rentalEntity.getVehicle().getValuedaily());
        rentalEntity.setTotalDays(rentalRequest.getTotalDays());
        // rentalEntity.setTotalAmount(rentalRequest.getTotalAmount());
        rentalEntity.setDepositAmount(rentalRequest.getDepositAmount());
        // rentalEntity.setTotalOptionalItemsValue(rentalRequest.getTotalOptionalItemsValue());
        rentalEntity.setPlateVehicle(vehicleRepository.findPlateById(rentalRequest.getVehicleId()));
        rentalEntity.setInitialMileage(rentalRequest.getInitialMileage());
        // rentalEntity.setReturnMileage(rentalRequest.getReturnMileage());
        rentalEntity.setActive(rentalRequest.isActive());
    
        rentalRepository.save(rentalEntity);
        return rentalRequest;
    }
    
    public RentalRequest partialUpdateRentalById(Long id, RentalRequest rentalRequest) {
        RentalEntity rentalEntity = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental with ID " + id + " not found"));
    
        // Atualizando apenas campos específicos, se presentes
        if (rentalRequest.getClientId() != null) {
            rentalEntity.setClient(clientRepository.findById(rentalRequest.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("Client not found")));
        }
    
        if (rentalRequest.getVehicleId() != null) {
            rentalEntity.setVehicle(vehicleRepository.findById(rentalRequest.getVehicleId())
                    .orElseThrow(() -> new EntityNotFoundException("Vehicle not found")));
        }
    
        // Atualizar opcionais se a lista não estiver vazia
        if (rentalRequest.getOptionals() != null && !rentalRequest.getOptionals().isEmpty()) {
            for (RentalRequest.OptionalRequest optional : rentalRequest.getOptionals()) {
                if (!optionalRepository.existsById(optional.getOptionalId())) {
                    throw new EntityNotFoundException("Optional with ID: " + optional.getOptionalId() + " not found");
                }
            }
    
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String optionalsJson = objectMapper.writeValueAsString(rentalRequest.getOptionals());
                rentalEntity.setOptionals(optionalsJson);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to convert Optionals to JSON", e);
            }
            
        }
    
        // Atualizando outros campos se presentes
        if (rentalRequest.getRentalDateTimeStart() != null) {
            rentalEntity.setRentalDateTimeStart(rentalRequest.getRentalDateTimeStart());
        }
        if (rentalRequest.getTotalDays() != null) {
            rentalEntity.setTotalDays(rentalRequest.getTotalDays());
        }
        if (rentalRequest.getDepositAmount() != null) {
            rentalEntity.setDepositAmount(rentalRequest.getDepositAmount());
        }
        if (rentalRequest.getInitialMileage() != null) {
            rentalEntity.setInitialMileage(rentalRequest.getInitialMileage());
        }
        rentalEntity.setActive(rentalRequest.isActive());
    
        rentalRepository.save(rentalEntity);

        RentalRequest rental = modelMapper.map(rentalEntity, RentalRequest.class);

        // Deserializa o campo optionals de JSON para lista
        rental.setOptionals(convertJsonToOptionals(rentalEntity.getOptionals()));
    
        return rental;
    }

    private void updateOptionalQuantities(List<RentalRequest.OptionalRequest> optionals, String operation) {
        for (RentalRequest.OptionalRequest optional : optionals) {

            Optional<OptionalEntity> optionalEntity = optionalRepository.findById(optional.getOptionalId());

            int newQuantity = operation == "subtraction" ? optionalEntity.get().getQtdAvailable() - optional.getQuantity() :  optionalEntity.get().getQtdAvailable() + optional.getQuantity();

            optionalEntity.get().setQtdAvailable(newQuantity);

            optionalRepository.save(optionalEntity.get()); // Salva a atualização da quantidade
        }
    }

    private boolean isVehicleRented(Long id) {
        // Método para verificar se o veículo tem uma locação ativa
        Optional<VehicleEntity> vehicleOptional = vehicleRepository.findById(id);
        
        // Verifique se o veículo existe
        if (vehicleOptional.isPresent()) {
            VehicleEntity vehicle = vehicleOptional.get();
            // Chama o método que verifica se o veículo está alugado e ativo
            return rentalRepository.existsByVehicleAndIsActiveTrue(vehicle);
        } else {
            // Se o veículo não foi encontrado, retorna false
            return false;
        }
    }

    private boolean isClientHasActiveRental(Long id) {
        if(rentalRepository.existsByClientIdAndIsActiveTrue(id)){
            if(clientRepository.existsByIdAndCnpjNull(id)){
                return true;
            }else{
                return false;
            }
        }else{
            if(clientRepository.existsByIdAndCnpjNull(id)){
                return rentalRepository.findAllByClientIdAndIsActiveFalse(id)
                .stream()
                .filter(r -> (r.getAmountPaid() - r.getTotalAmount()) < 0)
                .findAny()
                .isPresent();
            }else{
                return false;
            }
        }

    }

    private boolean isCnhExpired(Optional<ClientEntity> client) {
            // Verificando se a CNH do cliente está vencida
            LocalDate currentDate = LocalDate.now();
            return client.get().getCnhDtMaturity().isBefore(currentDate);
    }

    public List<RentalEntity> getRentalHistoryById(Long id) {
        if (id == null || id <= 0) {
            throw new FieldInvalidException("Invalid client ID");
        }
        
        List<RentalEntity> historyRental = rentalRepository.findByClientId(id);
        
        if (historyRental.isEmpty()) {
            throw new TableEmptyException("No rental history found for this client.");
        }
        
        return historyRental;
    }

    public void adicionarSinistroARental(Long rentalId, Long sinisterId) {
        // Obtém as entidades de Rental e Sinister
        RentalEntity rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found"));
        SinisterEntity sinister = sinisterRepository.findById(sinisterId)
                .orElseThrow(() -> new EntityNotFoundException("Sinister not found"));
    
        // Verifica se o sinistro já está associado à locação
        boolean exists = rentalSinisterRepository.existsByRentalAndSinister(rental, sinister);
        if (exists) {
            throw new DuplicateRegisterException("Sinister already associated with this rental");
        }
        
        // Cria a associação RentalSinister
        RentalSinister rentalSinister = new RentalSinister();
        rentalSinister.setRental(rental);
        rentalSinister.setSinister(sinister);
        
        // Salva a nova associação no banco de dados
        rentalSinisterRepository.save(rentalSinister);
    }

    private double calculateValueTotalOptionals(List<RentalRequest.OptionalRequest> optionals) {
        double total = 0.0;
        
        for (RentalRequest.OptionalRequest optional : optionals) {
            // Busca o valor do opcional pelo ID e verifica se existe
            Double valuePerUnit = optionalRepository.findValueLocationById(optional.getOptionalId()).orElse(0.0);
            
            // Multiplica o valor pela quantidade especificada e soma ao total
            total += valuePerUnit * optional.getQuantity();
        }
        
        return total;
    }

    public String checkoutRental(Long rentalId, RentalCheckoutRequest rentalCheckoutRequest, int operation) throws DuplicateRegisterException {

        // System.out.println(rentalSinisterRepository.existsByRentalIdAndSinisterId(rentalId, 7));        
        RentalEntity rental = rentalRepository.findById(rentalId)
        .orElseThrow(() -> new EntityNotFoundException("Rental with ID " + rentalId + " not found"));

        if(!rental.isActive()){
            throw new DuplicateRegisterException("Rental with ID " + rentalId + " has already been completed");
        }

        boolean existRentalWithSinister = rentalSinisterRepository.existsByRentalId(rentalId);

        boolean existRentalWithSinisterSeven = rentalSinisterRepository.existsByRentalIdAndSinisterId(rentalId, 7);


        if (existRentalWithSinister && rentalCheckoutRequest.getValuesSinisters() == null) {
            throw new FieldInvalidException("One or more sinisters registered in the rental were identified, please include the valuesSinisters field in the body of the request with the total value of the registered sinisters");
        }
        if (!existRentalWithSinister && rentalCheckoutRequest.getValuesSinisters() != null) {
            throw new FieldInvalidException("To add sinister values ​​to rental, it is necessary to register the sinister to rental");
        }
        if (existRentalWithSinisterSeven && 
            (rentalCheckoutRequest.getLostOptionals() == null || rentalCheckoutRequest.getLostOptionals().isEmpty())) {
            throw new FieldInvalidException("Loss of optional was identified in the rental, please add a list with the ID and quantity of the lost optional to the body of the request");
        }
        List<RentalCheckoutRequest.LostOptionalRequest> optionals = rentalCheckoutRequest.getLostOptionals();

        List<RentalCheckoutRequest.LostOptionalRequest> rentalOptionals = new ArrayList<>();
        
        if (optionals != null && !optionals.isEmpty()) {

            try {
                rentalOptionals = objectMapper.readValue(rental.getOptionals(), new TypeReference<List<RentalCheckoutRequest.LostOptionalRequest>>() {});
                // Agora rentalOptionals é uma lista de objetos LostOptionalRequest
            } catch (IOException e) {
                throw new RuntimeException("Failed to convert JSON to Optionals", e);
            } 

            for (RentalCheckoutRequest.LostOptionalRequest optional : optionals) {
                int rentalOptionalQuantity;
                // Verificar se o ID do optional está presente em rentalOptionals
                RentalCheckoutRequest.LostOptionalRequest matchedOptional = rentalOptionals.stream()
                .filter(rentalOptional -> rentalOptional.getOptionalId().equals(optional.getOptionalId()))
                .findFirst()
                .orElse(null); // Pode retornar null se não encontrar o ID

                if (matchedOptional != null) {
                    // Se encontrar, armazene o quantity de rentalOptionals na variável
                    rentalOptionalQuantity = matchedOptional.getQuantity();
                } else {
                    throw new EntityNotFoundException("Optional with ID " + optional.getOptionalId() + " not found in rentalOptionals.");
                }

                OptionalEntity optionalEntity = optionalRepository.findById(optional.getOptionalId())
                    .orElseThrow(() -> new EntityNotFoundException("Optional with ID: " + optional.getOptionalId() + " not found"));
    
                if (optional.getQuantity() <= 0) {
                    throw new FieldInvalidException("Optional quantity must be greater than or equal to 0");
                }
    
                if (rentalOptionalQuantity - optional.getQuantity() < 0) {
                    throw new DuplicateRegisterException("Quantity of optional " + optionalEntity.getId() + " must be less than or equal to the optional of the rental.");
                }                
            }
        }

        if(operation == 1){
            rental.setAmountPaid(rentalCheckoutRequest.getAmountPaid());
            rental.setRentalDateTimeEnd(rentalCheckoutRequest.getRentalDateTimeEnd());
            rental.setReturnMileage(rentalCheckoutRequest.getReturnMileage());

            String resultJson = calculateFinalValue(
                rental,
                rentalCheckoutRequest,
                existRentalWithSinister ? rentalCheckoutRequest.getValuesSinisters() : 0.0
            );

            try {
                // Converte o JSON retornado em um objeto JsonNode
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(resultJson);

                // Extrai o valor total do JSON
                Double totalAmount = jsonNode.get("totalAmount").asDouble();

                // Define o valor total na entidade
                rental.setTotalAmount(totalAmount);

                // Lógica adicional para salvar ou processar a locação pode ser incluída aqui

            } catch (Exception e) {
                // Tratamento de exceções (JSON malformado, etc.)
                e.printStackTrace();
                throw new RuntimeException("Error parsing JSON from calculateFinalValue");
            }        
            rental.setActive(false);

            boolean hasNonLowLevelSinisters = rental.getRentalSinisters().stream()
            .anyMatch(rentalSinister -> !rentalSinister.getSinister().getGravity().equals("LOW"));

            if (hasNonLowLevelSinisters) {
                rental.getVehicle().setAvailable(false);
            }

            rentalRepository.save(rental);

            for (RentalCheckoutRequest.LostOptionalRequest optionalRental : rentalOptionals) {
                if(existRentalWithSinisterSeven){
                    for (RentalCheckoutRequest.LostOptionalRequest optional : optionals) {
                        if(optionalRental.getOptionalId() == optional.getOptionalId()){
                            Optional<OptionalEntity> optionalEntity = optionalRepository.findById(optional.getOptionalId());
                            optionalEntity.get().setQtdAvailable(optionalEntity.get().getQtdAvailable() + optionalRental.getQuantity() - optional.getQuantity());
                            optionalRepository.save(optionalEntity.get());
                        }
                    }
                }else{
                    Optional<OptionalEntity> optionalEntity = optionalRepository.findById(optionalRental.getOptionalId());
                    optionalEntity.get().setQtdAvailable(optionalEntity.get().getQtdAvailable() + optionalRental.getQuantity());
                    optionalRepository.save(optionalEntity.get());
                }
            }

            return "Rental finished with success";

        }else{
            String resultJson = calculateFinalValue(
                rental,
                rentalCheckoutRequest,
                existRentalWithSinister ? rentalCheckoutRequest.getValuesSinisters() : 0.0
            );

            try {
                // Converte o JSON retornado em um objeto JsonNode
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(resultJson);

                // String details = jsonNode.get("details").asText();

                return jsonNode.toString();
            } catch (Exception e) {
                // Tratamento de exceções (JSON malformado, etc.)
                e.printStackTrace();
                throw new RuntimeException("Error parsing JSON from calculateFinalValue");
            }  
        }
    }

    // Método para converter JSON em lista de OptionalRequest
    public List<RentalRequest.OptionalRequest> convertJsonToOptionals(String jsonOptionals) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonOptionals, new TypeReference<List<RentalRequest.OptionalRequest>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to Optionals", e);
        }
    }

    public String calculateFinalValue(RentalEntity rental, RentalCheckoutRequest rentalCheckoutRequest, Double valueSinister) {

        // Verifica se dataCheckin é anterior a dataCheckout
        if (rentalCheckoutRequest.getRentalDateTimeEnd().isBefore(rental.getRentalDateTimeStart())) {
            throw new DuplicateRegisterException("The return date cannot be earlier than the initial date of the vehicle rental");
        }

        Long daysUsedByClient = ChronoUnit.DAYS.between(rental.getRentalDateTimeStart(), rentalCheckoutRequest.getRentalDateTimeEnd());
    
        Long lateDays = daysUsedByClient - rental.getTotalDays();
        Double totalAmount;
        Double fineCategory = 0.0;
        Double valueDailyDelay = 0.0;
        Double valueDailyExtras = 0.0;
    
        if (lateDays <= 0) {
            totalAmount = rental.getTotalAmount();
        } else {
            fineCategory = switch (lateDays.intValue()) {
                case 1, 2, 3, 4 -> rental.getVehicle().getCategory().getFine1To4Days();
                case 5, 6, 7, 8, 9 -> rental.getVehicle().getCategory().getFine5To9Days();
                default -> rental.getVehicle().getCategory().getFine10DaysOrMore();
            };
    
            valueDailyDelay = rental.getDailyRate() * 0.01 * lateDays;
            valueDailyExtras = rental.getDailyRate() * lateDays;
            totalAmount = fineCategory + valueDailyDelay + valueDailyExtras + rental.getTotalAmount() + valueSinister;
        }
    
        // Criar objeto de resposta com valores detalhados
        CalculationResponse response = new CalculationResponse(
            totalAmount,
            new CalculationDetails(rental.getTotalDays(), lateDays, rental.getDailyRate(), rental.getTotalOptionalItemsValue(), fineCategory, valueDailyDelay, valueDailyExtras, valueSinister)
        );
    
        // Converter para JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(response);
        } catch (IOException e) {
            e.printStackTrace();
            return "{}"; // Retorna JSON vazio em caso de erro
        }
    }
    
    // Classe para os detalhes
    class CalculationDetails {
        private Integer contractedDays;
        private Long latedDays;
        private Double valueDaily;
        private Double valueOptionals;
        private Double fineCategory;
        private Double fineForEachDayLate;
        private Double valueDailyExtras;
        private Double valueSinister;

        public CalculationDetails(Integer contractedDays, Long latedDays, Double valueDaily, Double valueOptionals, Double fineCategory, Double fineForEachDayLate, Double valueDailyExtras, Double valueSinister) {
            this.contractedDays = contractedDays;
            this.latedDays = latedDays;
            this.valueDaily = valueDaily;
            this.valueOptionals = valueOptionals;
            this.fineCategory = fineCategory;
            this.fineForEachDayLate = fineForEachDayLate;
            this.valueDailyExtras = valueDailyExtras;
            this.valueSinister = valueSinister;
        }

        public Integer getContractedDays() {
            return contractedDays;
        }

        public Long getLatedDays() {
            return latedDays;
        }

        public Double getValueDaily() {
            return valueDaily;
        }

        public Double getValueOptionals() {
            return valueOptionals;
        }

        public Double getFineCategory() {
            return fineCategory;
        }

        public Double getFineForEachDayLate() {
            return fineForEachDayLate;
        }

        public Double getValueDailyExtras() {
            return valueDailyExtras;
        }

        public Double getValueSinister() {
            return valueSinister;
        }
    }

    // Classe para a resposta principal
    class CalculationResponse {
        private Double totalAmount;
        private CalculationDetails details;

        public CalculationResponse(Double totalAmount, CalculationDetails details) {
            this.totalAmount = totalAmount;
            this.details = details;
        }

        // Getters
        public Double getTotalAmount() {
            return totalAmount;
        }

        public CalculationDetails getDetails() {
            return details;
        }
    }
}
