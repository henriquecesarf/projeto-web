# **LuggyCar - API de Aluguel de Carros**

## **Descrição do Projeto**

**LuggyCar** é um projeto desenvolvido pelos alunos do curso de **Sistemas de Informação** da faculdade **UNIME**, como parte da matéria **Web 2**, ministrada pelo professor **Paulo Reis**.  
A API simula uma plataforma de aluguel de carros, com funcionalidades para gerenciar clientes, veículos, locações, sinistros, opcionais e outros serviços oferecidos por locadoras de veículos.

Documentação com o pedido da API
https://dear-creature-6bf.notion.site/Projeto-Web-2-2024-2-0c6c1c28636549c0b5287e058371c714

---

## **Tecnologias Utilizadas**
- **Java**
- **Spring Boot**
- **Docker**
- **Redis**
- **MariaDB**
- **PhpMyAdmin**
- **Swagger** (para documentação e testes de API)
- **JUnit** e **Mockito** (para testes unitários)

---

### **Pré-requisitos**
- **Java 17** instalado.
- **Apache** Maven
- **Docker** instalado.
- Um leitor de API (ex.: **Postman**, **Insomnia**).

## **Banco de Dados**
O banco de dados é inicializado automaticamente com dados de exemplo ao rodar a aplicação. Isso inclui:

- **Usuários** (com diferentes privilégios).
- **Sinistros** (com identificações e gravidade).
- **Opcionais** (itens adicionais para locação).
- **Categorias de veículos**.
- **Clientes** (com informações como CPF, CNH, e dados de contato).

Esses dados já estarão disponíveis no banco assim que a API for iniciada.

---

## **Endpoints Disponíveis**

### **/categories**
- **GET**: Recuperar todas as categorias de veículos.
- **POST**: Criar uma nova categoria de veículo.

### **/client**
- **GET**: Recuperar todos os clientes.
- **POST**: Criar um novo cliente.
- **GET** `/client/{id}`: Recuperar detalhes de um cliente específico pelo ID.

### Estrutura Json do cliente
Para criar ou atualizar uma cliente, utilize o seguinte modelo JSON:

```json
   {
   	{
        "name": "João",
        "surname": "Silva",
        "cpf": "123456789",
        "cnpj": null,
        "email": "joao.silva@email.com",
        "sexo": "M",
        "dtNascimento": [
            1980,
            5,
            15
        ],
        "cnh": "1234567890",
        "cnhCategory": "B",
        "cnhDtMaturity": [
            2030,
            12,
            1
        ],
        "cep": "01001000",
        "address": "Rua A, 123",
        "country": "Brasil",
        "city": "São Paulo",
        "state": "SP",
        "complement": "Apto 101"
    }
}
```

### **/optionals**
- **GET**: Recuperar todos os opcionais.
- **POST**: Criar um novo opcional.

### **/sinisters**
- **GET**: Recuperar todos os sinistros.
- **POST**: Criar um novo sinistro.

### **/rentals**
- **GET**: Recuperar todas as locações.
- **POST**: Criar uma nova locação de veículo.

### Estrutura Json da locação
Para criar ou atualizar uma locação, utilize o seguinte modelo JSON:

```json
{
    "clientId": 1,
    "vehicleId": 1,
    "optionals": [
        {
            "optionalId": 1,
            "quantity": 1
        },
        {
            "optionalId": 3,
            "quantity": 1
        }
    ],
    "rentalDateTimeStart": "2024-11-10T10:00",
    "totalDays": 5,
    "depositAmount": 200.00,
    "initialMileage": 10000
}
```

### **/vehicles**
- **GET**: Recuperar todos os veículos.
- **POST**: Criar um novo veículo.

### Estrutura Json do veiculo
Para criar ou atualizar um veiculo, utilize o seguinte modelo JSON:
```json
{
  {
  "name": "HB20",
  "manufacturer": "HYUNDAI",
  "version": "1.0",
  "urlFipe": "https://www.icarros.com.br/tabela-fipe/hyundai/hb20",
  "plate": "LTT6539",
  "color": "Cinza",
  "exchange": "MANUAL",
  "km": 0,
  "capacityPassengers": 5,
  "volumeLoad": 100,
  "accessories": [
    "Multimidia",
    "Teto solar"
  ],
  "valuedaily": 100.0,
  "categoryId": 1
}

```

## Passo a passo para executar o projeto
#### Recomendamos 
* O uso de IntelliJ IDEA 

#### 1. **Clonar o repositório**
   - Para clonar o projeto, use o comando `git clone` com o URL do repositório.
     ```bash
     git clone https://github.com/henriquecesarf/projeto-web
     ```
   - Navegue para o diretório do projeto:
     ```bash
     cd projeto-web
     ```

#### 2. **Compilar o projeto**
   - Antes de executar o projeto, você deve garantir que ele está compilado corretamente. No IntelliJ procure pelo icone do maven ou no diretório do projeto, execute o comando Maven para compilar:
     ```bash
     mvn clean install
     ```
   - Isso irá baixar todas as dependências.

   
### 3. **Comandos no Terminal**
- Para iniciar a contrução do container no docker e iniciar a aplicação:
  ```bash
  docker-compose up --build
  docker-compose down
  ```

#### 5. **Conclusão**
   - Acesse a aplicação pelo navegador ou use ferramentas como `curl` ou Postman para fazer requisições à API.

---


A documentação detalhada para cada um dos endpoints pode ser acessada utilizando **Swagger**, que já está configurado para sua conveniência.
- http://localhost:8080/swagger-ui/index.html#/

---

## Contribuição

* [Henrique Cesar](https://github.com/henriquecesarf)
* [Alisson Jacó](https://github.com/alisonvmx)
* [Robert Wilson](https://github.com/roberttmag)
* [Milton Neto](https://github.com/koobotenMil)
* [Rodrigo](https://github.com/Digo2088)
* [Gabriel](https://github.com/GabrielinCode)

Orientador
* [Paulo Reis](https://github.com/PHPauloReis)
---
