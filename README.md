# Task Manager Java 📝

![CI](https://github.com/Bifaniii/task-manager-java/actions/workflows/ci.yml/badge.svg)

Um gerenciador de tarefas robusto desenvolvido com **Spring Boot**, focado em boas práticas de desenvolvimento, persistência de dados e organização em camadas.

**Status:** funcionalidades principais concluídas, com testes automatizados e CI.

## 🚀 Tecnologias
- **Java 17+**
- **Spring Boot 4**
- **Spring Data JPA**
- **Spring Security** (Autenticação e Autorização)
- **JSON Web Token (JWT)** (Autenticação Stateless)
- **MySQL**
- **Lombok** (para redução de boilerplate)
- **Jakarta Validation** (para integridade dos dados)
- **SpringDoc OpenAPI (Swagger)** (para documentação interativa)
- **JUnit 5, Mockito e MockMvc** (testes automatizados) + **H2** no perfil de testes

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
- [x] **Documentação com Swagger/OpenAPI**: Interface visual para testes de rotas disponível em `/swagger-ui.html` com suporte a autenticação JWT Bearer.

## 📖 Como Usar a API

### 1. **Configuração Inicial**
- Certifique-se de que o MySQL está rodando
- Execute a aplicação Spring Boot
- Acesse `http://localhost:8080/swagger-ui.html` para a documentação interativa

### 2. **Autenticação**
- **Registrar usuário**: `POST /auth/register`
- **Login**: `POST /auth/login` (retorna token JWT)
- **Usar token**: No Swagger, clique em "Authorize" e insira `Bearer <seu_token>`

### 3. **Endpoints Disponíveis**
- `GET /tasks` - Listar tarefas do usuário
- `GET /tasks/{id}` - Buscar tarefa por ID
- `GET /tasks/search/{title}` - Buscar tarefa por título
- `POST /tasks` - Criar nova tarefa
- `PATCH /tasks/{id}` - Atualizar tarefa
- `PATCH /tasks/{id}/done` - Marcar tarefa como concluída
- `DELETE /tasks/{id}` - Excluir tarefa

## 🧪 Testes
Testes unitários dos services com **JUnit 5 + Mockito** e testes de controller com **MockMvc** (status HTTP, validação e tratamento de erros). O teste de contexto roda com **H2 em memória**, então não precisa de MySQL:

```bash
./mvnw test
```

O workflow do GitHub Actions roda a suíte a cada push na `main` e em todo pull request.

## 🎯 Próximos passos
- [ ] Docker Compose com a aplicação e o MySQL
- [ ] Migrations com Flyway no lugar de `ddl-auto=update`
- [ ] Deploy público com o Swagger acessível

---
Desenvolvido por [Bifaniii](https://github.com/Bifaniii)
