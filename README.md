# Task Manager Java 📝

Um gerenciador de tarefas robusto desenvolvido com **Spring Boot**, focado em boas práticas de desenvolvimento, persistência de dados e organização em camadas.

## 🚀 Tecnologias
- **Java 17+**
- **Spring Boot 3**
- **Spring Data JPA**
- **MySQL**
- **Lombok** (para redução de boilerplate)
- **Jakarta Validation** (para integridade dos dados)
- **SpringDoc OpenAPI (Swagger)** (para documentação interativa)

## 🛠️ Configuração de Banco de Dados
O projeto utiliza MySQL. Certifique-se de que o banco `taskmanager` existe ou deixe a flag `createDatabaseIfNotExist=true` ativa no seu `application.properties`.
- **Fuso Horário**: A aplicação está configurada para `America/Sao_Paulo`.

## ✅ O que já foi implementado
- [x] **Modelo de Dados**: Entidades de `Task` e `User` (UUID).
- [x] **Regras de Negócio**: `TaskService` com suporte a transações (`@Transactional`) e gestão de fuso horário.
- [x] **Persistência**: Repositórios JPA com métodos de busca customizados.
- [x] **DTOs**: Uso de Records para entrada (`TaskRequest`) e saída (`TaskResponse`) incluindo data de criação.
- [x] **Camada de Controller**: Endpoints REST (`GET`, `POST`, `PATCH`, `DELETE`) com suporte a `@RequestParam`.
- [x] **Global Exception Handler**: Tratamento padronizado de erros com Exceptions customizadas (`TaskNotFound` e `TaskAlreadyFinished`).
- [x] **Documentação com Swagger/OpenAPI**: Interface visual para testes de rotas disponível em `/swagger-ui.html`.

## 🏗️ Próximos Passos (Roadmap)
- [ ] **Spring Security + JWT**: Implementação de autenticação e controle de acesso de usuários.
- [ ] **Docker**: Containerização da aplicação e do banco de dados para facilitar o deploy.
- [ ] **Testes Unitários**: Garantir a cobertura da lógica de negócio com JUnit e Mockito.

---
Desenvolvido por [Bifaniii](https://github.com/Bifaniii)
