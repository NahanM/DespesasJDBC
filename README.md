# API de Controle de Despesas (Java + JDBC)

## 📌 Sobre o Projeto

Este projeto é uma **API REST para gerenciamento de despesas pessoais**, desenvolvida com o objetivo principal de **entender na prática como funciona o acesso a banco de dados em Java utilizando JDBC**, sem o uso de frameworks como Spring.

A aplicação permite realizar operações básicas de um sistema financeiro simples, como **cadastro, listagem, atualização e remoção de despesas e categorias**.

O foco principal deste projeto é **compreender o funcionamento do backend Java “por baixo dos panos”**, incluindo:

* Servlets
* JDBC
* Manipulação de JSON
* Estrutura de arquitetura em camadas

O projeto está **concluído** como estudo. Além da API, ele tem um frontend simples em HTML, CSS e JavaScript puro que consome os endpoints: lista, cadastra, edita e exclui despesas, com cards de total, quantidade e último lançamento.

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
| GET    | `/despesas`      | Lista todas as despesas |
| GET    | `/despesas/{id}` | Busca despesa por ID    |
| POST   | `/despesas`      | Cria uma nova despesa   |
| PUT    | `/despesas`      | Atualiza uma despesa    |
| DELETE | `/despesas/{id}` | Remove uma despesa      |

> No `PUT`, o `id` vai no corpo do JSON, não na URL.
> Rodando local, a base é `http://localhost:8080/despesas`, então a listagem fica em `http://localhost:8080/despesas/despesas`.

---

## 📥 Exemplo de Requisição

### Criar despesa

POST `/despesas`

```json
{
  "descricao": "Supermercado",
  "valor": 150.00,
  "data": "2026-03-10",
  "categoria": {
    "id": 1
  }
}
```


---

## 📤 Exemplo de Resposta (`GET /despesas/5`)

```json
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
```

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

## ⚙ Como executar

### Requisitos

* Java 17
* Maven
* Apache Tomcat 9
* PostgreSQL

> Precisa ser o Tomcat 9: o projeto usa `javax.servlet`, e do Tomcat 10 em diante o pacote virou `jakarta.servlet`.

### 1. Clonar o repositório

```bash
git clone https://github.com/NahanM/DespesasJDBC.git
```

### 2. Criar o banco

Crie um banco vazio no PostgreSQL e rode o script `sql/schema.sql`.

### 3. Configurar as variáveis de ambiente

Os dados de conexão não ficam no código. A aplicação lê três variáveis de ambiente:

| Variável      | Exemplo                                     |
| ------------- | ------------------------------------------- |
| `DB_URL`      | `jdbc:postgresql://localhost:5432/despesas` |
| `DB_USER`     | `postgres`                                  |
| `DB_PASSWORD` | `sua_senha`                                 |

**No Eclipse:** aba *Servers* → duplo clique no Tomcat → *Open launch configuration* → aba *Environment* → adicione as três.

**No Windows (cmd):**

```bash
setx DB_URL "jdbc:postgresql://localhost:5432/despesas"
setx DB_USER "postgres"
setx DB_PASSWORD "sua_senha"
```

O `setx` só vale pra janelas abertas depois dele, então reabra o terminal ou o Eclipse.

### 4. Rodar

No Eclipse: *File → Import → Existing Maven Projects*, adicione o projeto ao Tomcat e inicie o servidor.

Acesse o frontend em `http://localhost:8080/despesas`.

### Testes

```bash
mvn test
```

## 🔄 Status do Projeto

✅ **Concluído.** API completa com CRUD de despesas e categorias, e frontend consumindo os endpoints.

---

## 📚 Objetivo do Projeto

Este projeto foi criado com o objetivo de **aprofundar o entendimento sobre como aplicações Java se comunicam com bancos de dados sem o uso de frameworks**, permitindo compreender melhor conceitos que frameworks como **Spring Boot e JPA abstraem**.

---

## 📚 Contexto do Projeto

Este projeto foi desenvolvido como parte de estudos sobre desenvolvimento backend em Java, com foco em compreender como funciona a comunicação com banco de dados utilizando JDBC e Servlets.

Projeto desenvolvido durante o curso Técnico em Desenvolvimento de Sistemas do SENAI-PR, com a ajuda do professor Carlos Correia, principalmente na configuração do Apache Tomcat e do Maven. Os commits em nome dele são dessa ajuda, como a migração para o Maven e a conversão dos valores para reais.
