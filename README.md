# API de Controle de Despesas (Java + JDBC)

## 📌 Sobre o Projeto

Este projeto é uma **API REST para gerenciamento de despesas pessoais**, desenvolvida com o objetivo principal de **entender na prática como funciona o acesso a banco de dados em Java utilizando JDBC**, sem o uso de frameworks como Spring.

A aplicação permite realizar operações básicas de um sistema financeiro simples, como **cadastro, listagem, atualização e remoção de despesas e categorias**.

O foco principal deste projeto é **compreender o funcionamento do backend Java “por baixo dos panos”**, incluindo:

* Servlets
* JDBC
* Manipulação de JSON
* Estrutura de arquitetura em camadas

Atualmente o projeto está **em desenvolvimento**.
A parte de **frontend ainda será implementada**, pois neste momento o foco do estudo está no **funcionamento do backend em Java**.

---

## 🚀 Funcionalidades

### Categorias

* Criar categoria
* Listar todas as categorias
* Buscar categoria por ID
* Atualizar categoria
* Remover categoria

### Despesas

* Criar despesa
* Listar todas as despesas
* Buscar despesa por ID
* Atualizar despesa
* Remover despesa

---

## 🛠 Tecnologias Utilizadas

* **Java**
* **Servlets**
* **JDBC**
* **Jackson (JSON)**
* **PostgreSQL**
* **Apache Tomcat**

---

## 📂 Estrutura do Projeto

O projeto segue uma estrutura de arquitetura em camadas para organização do código.

controller
  ├ CategoriaServlet
  └ DespesaServlet

service
  └ DespesaService

repository
  ├ CategoriaRepository
  └ DespesaRepository

model
  ├ Categoria
  └ Despesa

util
  └ ConexaoDB

---  

### Descrição das camadas

**controller**
Responsável por expor os endpoints da API e receber as requisições HTTP.

**service**
Contém regras de negócio da aplicação.

**repository**
Responsável pela comunicação direta com o banco de dados utilizando JDBC.

**model**
Representa as entidades do sistema.

**util**
Classes utilitárias, como a conexão com o banco de dados.

---

## 📡 Endpoints da API

### Categorias

| Método | Endpoint           | Descrição                 |
| ------ | ------------------ | ------------------------- |
| GET    | `/categorias`      | Lista todas as categorias |
| GET    | `/categorias/{id}` | Busca categoria por ID    |
| POST   | `/categorias`      | Cria uma nova categoria   |
| PUT    | `/categorias`      | Atualiza uma categoria    |
| DELETE | `/categorias/{id}` | Remove uma categoria      |

---

### Despesas

| Método | Endpoint             | Descrição               |
| ------ | -------------------- | ----------------------- |
| GET    | `/api/despesas`      | Lista todas as despesas |
| GET    | `/api/despesas/{id}` | Busca despesa por ID    |
| POST   | `/api/despesas`      | Cria uma nova despesa   |
| PUT    | `/api/despesas`      | Atualiza uma despesa    |
| DELETE | `/api/despesas/{id}` | Remove uma despesa      |

---

## 📥 Exemplo de Requisição

### Criar despesa

POST `/api/despesas`

-- json
{
  "descricao": "Supermercado",
  "valor": 150.00,
  "data": "2026-03-10",
  "categoria": {
    "id": 1
  }
}


---

## 📤 Exemplo de Resposta

-- json
{
  "id": 5,
  "descricao": "Supermercado",
  "valor": 150.00,
  "data": "2026-03-10",
  "categoria": {
    "id": 1,
    "nome": "Alimentação"
  }
}

---

## 🗄 Estrutura do Banco de Dados

### Tabela `categoria`

| Campo | Tipo    |
| ----- | ------- |
| id    | integer |
| nome  | varchar |

---

### Tabela `despesa`

| Campo        | Tipo    |
| ------------ | ------- |
| id           | integer |
| descricao    | varchar |
| valor        | numeric |
| data         | date    |
| categoria_id | integer |

---

## ⚙ Como executar o projeto

1. Clonar o repositório

git clone https://github.com/SKYRONN-gif/despesasJDBC.git

2. Criar o banco de dados PostgreSQL

3. Criar as tabelas `categoria` e `despesa`

4. Configurar os dados de conexão no arquivo:

util/ConexaoDB.java

5. Executar o projeto utilizando um servidor **Apache Tomcat**

---

## 🔄 Status do Projeto

🚧 **Em desenvolvimento**

Atualmente o projeto está focado no **desenvolvimento e entendimento do backend em Java utilizando JDBC**.

O **frontend ainda será desenvolvido**, com o objetivo de consumir esta API e permitir interação visual com o sistema.

---

## 📚 Objetivo do Projeto

Este projeto foi criado com o objetivo de **aprofundar o entendimento sobre como aplicações Java se comunicam com bancos de dados sem o uso de frameworks**, permitindo compreender melhor conceitos que frameworks como **Spring Boot e JPA abstraem**.

---

## 📚 Contexto do Projeto

Este projeto foi desenvolvido como parte de estudos sobre desenvolvimento backend em Java, com foco em compreender como funciona a comunicação com banco de dados utilizando JDBC e Servlets.
