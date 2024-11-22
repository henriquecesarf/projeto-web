CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    privilege VARCHAR(5) NOT NULL
);

CREATE TABLE IF NOT EXISTS sinister (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    identification VARCHAR(255) NOT NULL UNIQUE,
    gravity ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL
);

CREATE TABLE IF NOT EXISTS optional (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    value_location DECIMAL(10, 2) NOT NULL,
    value_declared DECIMAL(10, 2) NOT NULL,
    qtd_available INT NOT NULL
);

CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    fine_1_to_4_days DECIMAL(10, 2) NOT NULL,
    fine_5_to_9_days DECIMAL(10, 2) NOT NULL,
    fine_10_days_or_more DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE client (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    cnpj VARCHAR(14) UNIQUE,
    email VARCHAR(255) UNIQUE NOT NULL,
    sexo ENUM('M', 'F', 'X') NOT NULL,
    dt_nascimento DATE NOT NULL,
    cnh VARCHAR(15) UNIQUE NOT NULL,
    cnh_category ENUM('A', 'B', 'C', 'D', 'E') NOT NULL,
    cnh_dt_maturity DATE NOT NULL,
    cep VARCHAR(10) NOT NULL,
    address VARCHAR(255) NOT NULL,
    country VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    complement VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    st_excluido BOOLEAN DEFAULT FALSE
);