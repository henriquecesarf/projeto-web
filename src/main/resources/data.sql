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
('Colisão frontal em rodovia', 'HIGH'),
('Batida traseira leve em área urbana', 'LOW'),
('Capotamento', 'MEDIUM'),
('Atropelamento de pedestre', 'HIGH'),
('Colisão lateral em cruzamento', 'MEDIUM');

--inserção de opcionais
INSERT INTO optional (name, value_location, value_declared, qtd_available) VALUES
('Cadeirinha para Bebê', 35.00, 250.00, 10),
('Assento Infantil', 20.00, 150.00, 10),
('Caixa De Transporte de Animais', 30.00, 200.00, 10);

INSERT INTO category (name, fine1To4Days, fine5To9Days, fine10DaysOrMore, createdAt) VALUES
    ('HATCH', 10.50, 20.00, 30.00, NOW()),
    ('SEDAN', 15.00, 25.50, 35.00, NOW()),
    ('PICK-UP', 20.75, 30.25, 40.50, NOW());
