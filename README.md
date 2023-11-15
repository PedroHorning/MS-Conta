# MS-Conta

CREATE DATABASE conta_comando;

-- Criação da tabela contas
CREATE TABLE contas (
    id_conta SERIAL PRIMARY KEY,
    numero_conta VARCHAR(50),
    data_criacao DATE,
    limite DECIMAL(10, 2),
    id_cliente INT,
    id_gerente INT,
    saldo DECIMAL(10, 2)
);

-- Criação da tabela movimentacoes
CREATE TABLE movimentacoes (
    id_movimentacao SERIAL PRIMARY KEY,
    data_hora TIMESTAMP,
    tipo VARCHAR(50),
    valor DECIMAL(10, 2),
    id_conta_origem INT,
    id_conta_destino INT,
    id_cliente_origem INT,
    id_cliente_destino INT
);

-- Criação da tabela gerentes
CREATE TABLE gerentes (
    id_gerente SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(14)
);

-- Criação da tabela clientes
CREATE TABLE clientes (
    id_cliente SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(14)
);
