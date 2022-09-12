use dbsysteminfo

CREATE TABLE tbabertcaixa (
  idabert int IDENTITY(1,1) PRIMARY KEY,
  idusuario varchar(50) NOT NULL,
  DataAbert datetime  NOT NULL, default CURRENT_TIMESTAMP,
  ValorAbert decimal(10,3) NOT NULL,
  AbertStatus varchar(1) NOT NULL,
 )
select * from tbcidade
create table tbcidade (
idcidade int primary key,
cidNome varchar(60) not null,
cidEstado varchar(2) not null,
cidPais varchar(30) not null 
);
drop table tbusuarios
create table tbusuarios (
idusu int primary key auto_increment,
usuNome varchar(50) not null,
usuLogin varchar(15) not null unique,
usuSenha varchar(15) not null,
usuBloqueado varchar(10) not null,
usuTipo varchar(10) not null
);
insert into tbusuarios (usuNome,usuLogin,usuSenha,usuBloqueado,usuTipo)
values('Ederson','Eder','123','Bloqueado','User');
Select * from tbusuarios
create table tbconvenios (
idconv int primary key auto_increment,
convDescricao varchar(100)
);
ALTER TABLE tbusuarios CHANGE usuBloqueado usuBloqueado varchar(15) not null; 

create table tbpessoas (
idpes int primary key auto_increment,
pesDataCad timestamp default current_timestamp,
pesTipo varchar(1) not null,
pesSexo varchar(1) not null,
pesNome varchar(100) not null,
pesDataNasc date,
pesCPF_CNPJ varchar(14) not null,
pesRG_IE varchar(15) not null,
pesFantasia varchar(25),
pesEMAIL varchar(100),
pesTelefone varchar(15),
pesCelular varchar(15),
pesEndereco varchar(40) not null,
pesEndNum varchar(6) not null,
pesEndComp varchar(20),
pesCEP varchar(9) not null,
pesBairro varchar(40),
pesNaturalidade varchar(50),
pesEstadoCivil varchar(1),
pesObservacao varchar(300),
idcidade int not null,
foreign key(idcidade) references tbcidade(idcidade),
idconv int,
foreign key(idconv) references tbconvenios(idconv),
pesSenha varchar(14),
pesAtivo varchar(1)
);

create table tbGrupos (
idgru int primary key auto_increment,
gruDescricao varchar(100)
);
ALTER TABLE tbGrupos CHANGE gruDescricao gruDescricao varchar(100) not null; 

create table tbMarcas (
idMarcas int primary key auto_increment,
MarcasDescricao varchar(100) not null
);
drop TABLE tbProdutos

create table tbCadeias (
idcad int primary key auto_increment,
cadDescricao varchar(100) not null
);

create table tbTipoProduto (
idtippro int primary key auto_increment,
tipproDescricao varchar(100) not null
);

create table tbVersao (
idver int primary key,
verDescricao varchar(20) not null,
verDataatualiza date not null,
verBancoDados varchar(20) not null
);

create table tbEmpresas (
idemp int primary key auto_increment,
empCNPJ varchar(14) not null,
empIE varchar(15) not null,
empIM varchar(15),
empRazaoSocial varchar(100) not null,
empFantasia varchar(100) not null,
empEndereco varchar(100) not null,
empEndNro varchar(10) not null,
empEndComp varchar(50),
empBairro varchar(50) not null,
empCEP varchar(8) not null,
empEmail varchar(100),
empTelefone varchar(15),
empCelular varchar(15),
idcidade int not null,
foreign key(idcidade) references tbcidade(idcidade)

);

create table tbProdutos (
idprod int primary key auto_increment,
prodAtivo varchar(1) not null,
prodCodigo varchar(30) not null,
prodDescricao varchar(100) not null,
prodDataCadastro timestamp default current_timestamp,
prodDataAlteracao date not null,
prodPesoLiquido Numeric(10,3) not null,
prodPesoBruto Numeric(10,3) not null,
prodCusto Numeric(10,3),
prodVendaAvista Numeric(10,3) not null,
prodLucro Numeric(10,3),
prodEstoque Numeric(10,3),
prodEstoqueMin Numeric(10,3),
idtippro int not null,
foreign key(idtippro) references tbTipoProduto(idtippro),
idgru int not null,
foreign key(idgru) references tbGrupos(idgru),
idcad int not null,
foreign key(idcad) references tbCadeias(idcad),
idMarcas int not null,
foreign key(idMarcas) references tbMarcas(idMarcas)
);

create table tbFornProd (
idfornprod int primary key auto_increment,
fornprodCodigo int not null,
idprod int not null,
foreign key(idprod) references tbProdutos(idprod),
idpes int not null,
foreign key(idpes) references tbPessoas(idpes)
);

create table tbPreVenda (
idprv int primary key auto_increment,
prvDataPreVenda timestamp default current_timestamp,
idpes int not null,
foreign key(idpes) references tbPessoas(idpes),
idusu int not null,
foreign key(idusu) references tbUsuarios(idusu),
prvSituacao int not null,
prvObservacao varchar(500),
prvValorTotalBruto Numeric(10,3) not null,
prvValorDescPorc Numeric(10,3),
prvValorDesc Numeric(10,3),
prvValorTotalLiquido Numeric(10,3) not null
);

create table tbitemprevenda(
idprv int not null,
foreign key(idprv) references tbPreVenda(idprv),
idprod int not null,
foreign key(idprod) references tbProdutos(idprod),
itemQuant Numeric(10,3) not null,
itemValorUnit Numeric(10,2) not null,
itemValorDescPorc Numeric(10,2),
itemValorDesc Numeric(10,2),
itemValorTotalUn Numeric(10,2) not null
);