# AutoPartsCRM

Sistema CRM para gestão de autopeças, desenvolvido em Spring Boot com Maven. Esta aplicação oferece funcionalidades para gerenciamento de clientes, cotações, pedidos e controle de estoque.

---

## Requisitos

- Java 17 ou superior instalado  
- Maven instalado
- Banco de dados relacional configurado (MySQL, PostgreSQL, H2, etc.)  
- IDE  

---

## Configuração do Banco de Dados

1. Configure seu banco de dados preferido (exemplo: MySQL).  
2. Crie um banco, por exemplo, chamado `auto_parts_db`.  
3. Configure as credenciais de acesso (usuário e senha).  

---

## Configuração da Aplicação

No arquivo `src/main/resources/application.properties` (ou `application.yml`), configure as propriedades de conexão do banco, por exemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/auto_parts_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```


## Como Rodar a Aplicação
Usando Maven
Abra um terminal na raiz do projeto.

Execute:

bash
Copiar
mvn spring-boot:run
Usando Wrapper Maven
