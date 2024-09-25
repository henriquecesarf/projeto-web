CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    privilege VARCHAR(5) NOT NULL
);

CREATE TABLE sinister (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    identification VARCHAR(255) NOT NULL,
    gravity ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL
);

CREATE TABLE optional (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    value_location DECIMAL(10, 2) NOT NULL,
    value_declared DECIMAL(10, 2) NOT NULL,
    qtd_available INT NOT NULL
);
