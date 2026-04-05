# Task Manager Java 📝

Um gerenciador de tarefas robusto desenvolvido com **Spring Boot**, focado em boas práticas de desenvolvimento, persistência de dados e organização em camadas.

## 🚀 Tecnologias
- **Java 17+**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Security** (Autenticação e Autorização)
- **JSON Web Token (JWT)** (Autenticação Stateless)
- **MySQL**
- **Lombok** (para redução de boilerplate)
- **Jakarta Validation** (para integridade dos dados)
- **SpringDoc OpenAPI (Swagger)** (para documentação interativa)

## 🛠️ Configuração de Banco de Dados
O projeto utiliza MySQL. Certifique-se de que o banco `taskmanager` existe ou deixe a flag `createDatabaseIfNotExist=true` ativa no seu `application.properties`.
- **Fuso Horário**: A aplicação está configurada para `America/Sao_Paulo`.

## ✅ O que já foi implementado
- [x] **Modelo de Dados**: Entidades de `Task` e `User` (UUID).
- [x] **Segurança & Autenticação**: Implementação de **Spring Security** com **JWT**. Cadastro de usuários com senhas criptografadas via **BCrypt**.
- [x] **Regras de Negócio**: `TaskService` e `AuthorizationService` com suporte a transações (`@Transactional`) e gestão de fuso horário.
- [x] **Persistência**: Repositórios JPA com métodos de busca customizados.
- [x] **DTOs**: Uso de Records para entrada (`Request`), saída (`Response`) e autenticação.
- [x] **Camada de Controller**: Endpoints REST (`GET`, `POST`, `PATCH`, `DELETE`) protegidos por token.
- [x] **Global Exception Handler**: Tratamento padronizado de erros com Exceptions customizadas (`TaskNotFound`, `TaskAlreadyFinished` e `UserAlreadyExists`).
- [x] **Documentação com Swagger/OpenAPI**: Interface visual para testes de rotas disponível em `/swagger-ui.html`.

## 🏗️ Próximos Passos (Roadmap)
- [ ] **Docker**: Containerização da aplicação e do banco de dados para facilitar o deploy.
- [ ] **Testes Unitários**: Garantir a cobertura da lógica de negócio com JUnit e Mockito.

---
Desenvolvido por [Bifaniii](https://github.com/Bifaniii)
