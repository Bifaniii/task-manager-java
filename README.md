# Task Manager Java 📝

Um gerenciador de tarefas robusto desenvolvido com **Spring Boot**, focado em boas práticas de desenvolvimento, persistência de dados e organização em camadas.

## 🚀 Tecnologias
- **Java 17+**
- **Spring Boot 3**
- **Spring Data JPA**
- **MySQL**
- **Lombok** (para redução de boilerplate)
- **Jakarta Validation** (para integridade dos dados)

## 🛠️ Configuração de Banco de Dados
O projeto utiliza MySQL. Certifique-se de que o banco `taskmanager` existe ou deixe a flag `createDatabaseIfNotExist=true` ativa no seu `application.properties`.

## ✅ O que já foi implementado
- [x] **Modelo de Dados**: Entidades de `Task` e `User` (UUID).
- [x] **Regras de Negócio**: `TaskService` com validações e suporte a transações (@Transactional).
- [x] **Persistência**: Repositórios JPA com métodos de busca customizados.
- [x] **DTOs**: Uso de Records para validação de entrada de dados.

## 🏗️ Próximos Passos (Roadmap)
- [ ] **Camada de Controller**: Expor os endpoints REST.
- [ ] **Global Exception Handler**: Tratamento padronizado de erros (ex: TaskNotFound).
- [ ] **Documentação com Swagger/OpenAPI**: Para testar as rotas facilmente.
- [ ] **Spring Security + JWT**: Implementação de autenticação (Planejado para fases futuras).

---
Desenvolvido por [Bifaniii](https://github.com/Bifaniii)
