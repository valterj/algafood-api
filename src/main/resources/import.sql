insert into cozinha (id, nome)values (1, 'Americana')
insert into cozinha (id, nome)values (2, 'Tailandesa')
insert into cozinha (id, nome)values (3, 'Indiana')
insert into cozinha (id, nome)values (4, 'Brasileira')

insert into estado (id, nome) values (1, 'Minas Gerais');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Ceará');
insert into estado (id, nome) values (4, 'Santa Catarina');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);
insert into cidade (id, nome, estado_id) values (6, 'Joinville', 4);

insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao) values ('MacDonalds', '0', 1, '89 221-150', 'Rua João Colin', '150', 'Santo Antônio', 6, utc_timestamp, utc_timestamp)
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Tempero Mineiro', '5.99', 4, utc_timestamp, utc_timestamp)
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Burger King', '1.99', 4, utc_timestamp, utc_timestamp)

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento values (1, 1), (1, 2), (2, 1), (2, 3), (3, 1), (3, 2), (3 ,3)

