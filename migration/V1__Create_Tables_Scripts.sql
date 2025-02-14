-- Criação da tabela categoria_produto
CREATE TABLE categoria_produto
(
    id               UUID PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
    nome             VARCHAR(50)             NOT NULL,
    data_criacao     TIMESTAMP DEFAULT NOW() NOT NULL,
    data_atualizacao TIMESTAMP DEFAULT NOW() NOT NULL,
    e_ativo          BOOLEAN   DEFAULT TRUE  NOT NULL
);

CREATE UNIQUE INDEX uk_categoria_produto_nome ON categoria_produto(nome) WHERE e_ativo;

-- Inserção de categorias na tabela categoria_produto
INSERT INTO categoria_produto (id, nome)
VALUES ('4ce30a87-5654-486b-bed6-88c6f83f491a', 'Acompanhamento'),
       ('2ae01e62-6805-4095-9bc3-9b9081517b87', 'Lanche'),
       ('e397f412-9c76-4fb5-b029-7c3a99b7e982', 'Bebida'),
       ('d5b5a378-3862-4436-bdcc-29d8c8a2ee65', 'Sobremesa');

-- -- --


-- Criação da tabela produto
CREATE TABLE produto (
                         id UUID PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
                         nome TEXT NOT NULL,
                         descricao TEXT NOT NULL,
                         id_categoria UUID NOT NULL,
                         preco DECIMAL(10,2) NOT NULL DEFAULT 00.00,
                         data_criacao TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                         data_atualizacao TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                         e_ativo BOOL NOT NULL DEFAULT TRUE,
                         CONSTRAINT fk_produto_id_categoria_produto FOREIGN KEY (id_categoria) REFERENCES categoria_produto(id)
);

CREATE INDEX ik_produto_nome ON produto(nome);
CREATE INDEX ik_produto_descricao ON produto(descricao);
CREATE INDEX ik_produto_id_categoria ON produto(id_categoria);
CREATE UNIQUE INDEX uk_produto_nao_duplicado ON produto(nome, descricao, id_categoria, preco) WHERE e_ativo;

INSERT INTO produto (id, nome, descricao, id_categoria, preco) VALUES
                                                                   ('d98620ab-094e-4702-a066-42c8f39caaaa', 'Hamburguer', 'Pão, queijo, alface, tomate, carne.', '4ce30a87-5654-486b-bed6-88c6f83f491a', 30.00),
                                                                   ('7f72e22e-aee6-4dc6-a7b3-5b36f944e4a5', 'Pizza 4 queijos', 'Queijo muçarela, gorgonzola, parmesão, catupiry.', '2ae01e62-6805-4095-9bc3-9b9081517b87', 50.00),
                                                                   ('f027fc02-6e4b-4f7d-8cbc-762da1dff4df', 'Porção de tilápia', '500 gramas de peixe', 'e397f412-9c76-4fb5-b029-7c3a99b7e982', 40.00);

-- -- --

-- Criação da tabela cliente
CREATE TABLE cliente (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    cpf VARCHAR(11) UNIQUE,
    data_criacao TIMESTAMP DEFAULT now(),
    data_atualizacao TIMESTAMP DEFAULT now()
);

INSERT INTO cliente (id, nome, email, cpf)
    VALUES ('e389406d-5531-4acf-a354-be5cc46a8cd4', 'Teste Usuário', 'teste.usuario@email.com', '12022425022');


-- -- --


-- Criação da tabela temporária pagamento
CREATE TABLE pagamento (
                           id UUID PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
                           cd_pedido VARCHAR(255) NULL,
                           preco DECIMAL(10,2) NOT NULL DEFAULT 00.00,
                           data_criacao TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Criação da tabela pedido
CREATE TABLE pedido (
                        id UUID PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
                        codigo VARCHAR(5) NOT NULL,
                        status TEXT NOT NULL,
                        id_cliente UUID NOT NULL,
                        preco DECIMAL(10,2) NOT NULL DEFAULT 00.00,
                        observacao VARCHAR(255) NULL,
                        cd_pagamento VARCHAR(255) NULL,
                        data_criacao TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                        data_atualizacao TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                        CONSTRAINT fk_pedido_id_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);
CREATE INDEX ik_pedido_status ON pedido(status);

-- Criação da tabela produto_pedido
CREATE TABLE produto_pedido (
                                id UUID PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
                                quantidade INTEGER NOT NULL DEFAULT 0,
                                observacao VARCHAR(255) NULL,
                                id_pedido UUID NOT NULL,
                                id_produto UUID NOT NULL,
                                data_criacao TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                data_atualizacao TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                CONSTRAINT fk_produto_pedido_id_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id),
                                CONSTRAINT fk_produto_pedido_id_produto FOREIGN KEY (id_produto) REFERENCES produto(id)
);

-- Inserção de produtos para pedidos na tabela produto
INSERT INTO produto
(id, nome, descricao, id_categoria, preco)
VALUES
    ('e389406d-5531-4acf-a354-be5cc46a8ca1', 'Hamburguer', 'Pão, queijo, alface, tomate, carne.', '4ce30a87-5654-486b-bed6-88c6f83f491a', 25.00),
    ('e389406d-5531-4acf-a354-be5cc46a8ca2', 'Pizza 4 queijos', 'Queijo muçarela, gorgonzola, parmesão, catupiry.', '2ae01e62-6805-4095-9bc3-9b9081517b87', 45.00),
    ('e389406d-5531-4acf-a354-be5cc46a8ca3', 'Porção de tilápia', '500 gramas de peixe', 'e397f412-9c76-4fb5-b029-7c3a99b7e982', 35.00);

-- Inserção de pedido na tabela pedido
INSERT INTO pedido
(id, codigo, status, id_cliente, preco, observacao)
VALUES
    ('e389406d-5531-4acf-a354-be5cc46a8cb1', 'XK8JL', 'RECEBIDO', 'e389406d-5531-4acf-a354-be5cc46a8cd4', 100.00, 'Trazer bem embalado'),
    ('e389406d-5531-4acf-a354-be5cc46a8cb2', 'I7LXA', 'ABERTO', 'e389406d-5531-4acf-a354-be5cc46a8cd4', 110.00, 'Estarei na frente da casa'),
    ('e389406d-5531-4acf-a354-be5cc46a8cb3', 'M0I2W', 'CANCELADO', 'e389406d-5531-4acf-a354-be5cc46a8cd4', 120.00, null);

-- Inserção de produtos no pedido na tabela produto_pedido
INSERT INTO produto_pedido
(id, quantidade, observacao, id_pedido, id_produto)
VALUES
    ('e389406d-5531-4acf-a354-be5cc46a8cc1',1, 'Mal passado', 'e389406d-5531-4acf-a354-be5cc46a8cb1', 'e389406d-5531-4acf-a354-be5cc46a8ca1'),
    ('e389406d-5531-4acf-a354-be5cc46a8cc2',2, 'Bem passado', 'e389406d-5531-4acf-a354-be5cc46a8cb1', 'e389406d-5531-4acf-a354-be5cc46a8ca2'),
    ('e389406d-5531-4acf-a354-be5cc46a8cc3',3, 'Bem gelado', 'e389406d-5531-4acf-a354-be5cc46a8cb1', 'e389406d-5531-4acf-a354-be5cc46a8ca3'),
    ('e389406d-5531-4acf-a354-be5cc46a8cc4',2, 'Mal passado', 'e389406d-5531-4acf-a354-be5cc46a8cb2', 'e389406d-5531-4acf-a354-be5cc46a8ca1'),
    ('e389406d-5531-4acf-a354-be5cc46a8cc5',1, 'Bem passado', 'e389406d-5531-4acf-a354-be5cc46a8cb2', 'e389406d-5531-4acf-a354-be5cc46a8ca2'),
    ('e389406d-5531-4acf-a354-be5cc46a8cc6',2, 'Bem gelado', 'e389406d-5531-4acf-a354-be5cc46a8cb2', 'e389406d-5531-4acf-a354-be5cc46a8ca3'),
    ('e389406d-5531-4acf-a354-be5cc46a8cc7',3, 'Mal passado', 'e389406d-5531-4acf-a354-be5cc46a8cb3', 'e389406d-5531-4acf-a354-be5cc46a8ca1'),
    ('e389406d-5531-4acf-a354-be5cc46a8cc8',1, 'Bem passado', 'e389406d-5531-4acf-a354-be5cc46a8cb3', 'e389406d-5531-4acf-a354-be5cc46a8ca2'),
    ('e389406d-5531-4acf-a354-be5cc46a8cc9',2, 'Bem gelado', 'e389406d-5531-4acf-a354-be5cc46a8cb3', 'e389406d-5531-4acf-a354-be5cc46a8ca3');

-- Inserção de pagamento do pedido na tabela pagamento
INSERT INTO pagamento
    (cd_pedido, preco)
VALUES
    ('e389406d-5531-4acf-a354-be5cc46a8cb1', 500);