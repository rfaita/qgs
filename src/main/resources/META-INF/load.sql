insert into empresa  (id, empresa ) values (1, 'Provedor 1');
insert into empresa  (id, empresa ) values (2, 'Provedor 2');
insert into empresa  (id, empresa ) values (3, 'Provedor 3');

insert into usuario (id, usuario, email, celular, cpf, senha, role, pontos) values (1, 'rfaita', 'rfaita@gmail.com', '+5519999096187', '34438746817', 'rfaita', 'QUEST',0);
insert into usuario (id, usuario, email, celular, cpf, senha, role, pontos) values (2, 'rfaita2', 'rfaita@gmail.com', '+5519999096187', '34438746817', 'rfaita', 'QUEST',0);
insert into usuario (id, usuario, email, celular, cpf, senha, role, pontos) values (3, 'rfaita3', 'rfaita@gmail.com', '+5519999096187', '34438746817', 'rfaita', 'QUEST',0);
insert into usuario (id, usuario, email, celular, cpf, senha, role, pontos) values (4, 'rfaita4', 'rfaita@gmail.com', '+5519999096187', '34438746817', 'rfaita', 'QUEST',0);

insert into usuarioprovedor (id, usuario, senha, email, idprovedor, role, nome) values (1, 'admin', 'admin', 'admin@admin.com', 3, 'admin', 'Um morto muito loko');
insert into usuarioprovedor (id, usuario, senha, email, idprovedor, role, nome, avatar) values (2, 'user', 'user', 'user@admin.com', 1, 'user', 'Um morto muito loko2', 'data:image/jpeg;base64,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');
insert into usuarioprovedor (id, usuario, senha, email, idprovedor, role, nome,sobrenome,sexo,avatar) values (3, 'admin2', 'admin', 'rfaita@gmail.com', 1, 'admin', 'Rafael', 'Faita','M','data:image/jpeg;base64,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');

insert into epi  (id, epi, ativo, descricao, idempresa) values (1, 'Capa do batman', true, 'Teste', 1);
insert into epi  (id, epi, ativo, descricao, idempresa) values (2, 'Cinto do batman', false, 'Teste', 1);
insert into epi  (id, epi, ativo, descricao, idempresa) values (3, 'Cueca do superhomem', true, 'Teste', 1);
insert into epi  (id, epi, ativo, descricao, idempresa) values (4, 'Calcinha do robem', true, 'Teste', 1);

insert into tipomaterial  (id, tipomaterial) values (1, 'Tipo A');
insert into tipomaterial  (id, tipomaterial) values (2, 'Tipo B');
insert into tipomaterial  (id, tipomaterial) values (3, 'Tipo C');
insert into tipomaterial  (id, tipomaterial) values (4, 'Tipo D');
insert into tipomaterial  (id, tipomaterial) values (5, 'Tipo E');

insert into tipoatributo(id, tipoatributo) values (1, 'Tipo A');
insert into tipoatributo(id, tipoatributo) values (2, 'Tipo B');
insert into tipoatributo(id, tipoatributo) values (3, 'Tipo C');
insert into tipoatributo(id, tipoatributo) values (4, 'Tipo D');
insert into tipoatributo(id, tipoatributo) values (5, 'Tipo E');