-- inserção de dados automatica ao rodar a aplicação

-- inserção de usuarios
INSERT INTO users (id, name, privilege) 
VALUES 
(1, 'supervisor', 'admin');
INSERT INTO users (id, name, privilege) 
VALUES 
(2, 'funcionario', 'user');

-- inserção de sinistros
INSERT INTO sinister (identification, gravity)
VALUES 
('Veículo C/Seguro Avariado, ', 'LOW'),
('Veículo S/Seguro Avariado, ', 'MEDIUM'),
('Veículo C/Seguro Perda Total, ', 'HIGH'),
('Veículo S/Seguro Perda Total, ', 'HIGH'),
('Veículo Furtado', 'HIGH'),
('Veículo apreendido', 'HIGH'),
('Perda de opcional', 'LOW');

--inserção de opcionais
INSERT INTO optional (name, value_location, value_declared, qtd_available) VALUES
('Cadeirinha para Bebê', 35.00, 250.00, 10),
('Assento Infantil', 20.00, 150.00, 10),
('Caixa De Transporte de Animais', 30.00, 200.00, 10);

INSERT INTO category (name, fine_1_to_4_days, fine_5_to_9_days, fine_10_days_or_more, created_at) VALUES
    ('HATCH', 10.50, 20.00, 30.00, NOW()),
    ('SEDAN', 15.00, 25.50, 35.00, NOW()),
    ('PICK-UP', 20.75, 30.25, 40.50, NOW());

INSERT INTO client (name, surname, cpf, cnpj, email, sexo, dt_nascimento, cnh, cnh_category, cnh_dt_maturity, cep, address, country, city, state, complement, created_at, st_excluido) 
VALUES 
    ('João', 'Silva', '12345678909', null, 'joao.silva@email.com', 'M', '1980-05-15', '1234567890', 'B', '2030-12-01', '01001000', 'Rua A, 123', 'Brasil', 'São Paulo', 'SP', 'Apto 101', NOW(), FALSE),
    ('Maria', 'Oliveira', '23456789018', '12345678000195', 'maria.oliveira@email.com', 'F', '1990-08-25', '2345678901', 'A', '2025-05-10', '20040030', 'Av. B, 456', 'Brasil', 'Rio de Janeiro', 'RJ', 'Casa', NOW(), FALSE),
    ('Carlos', 'Santos', '34567890100', null, 'carlos.santos@email.com', 'M', '1985-02-11', '3456789012', 'C', '2024-03-20', '30140071', 'Rua C, 789', 'Brasil', 'Belo Horizonte', 'MG', 'Bloco 2, Apto 204', NOW(), FALSE);