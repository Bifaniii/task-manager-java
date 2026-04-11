# Como Usar a API Task Manager

## ⚙️ Configuração Inicial

### 1. Variáveis de Ambiente
Antes de executar a aplicação, defina as variáveis de ambiente:

```bash
export DATABASE_USERNAME=root
export DATABASE_PASSWORD=sua_senha_mysql
export JWT_SECRET=sua_chave_super_secreta_123456
```

Ou crie um arquivo `.env` na raiz do projeto com:
```env
DATABASE_USERNAME=root
DATABASE_PASSWORD=sua_senha_mysql
JWT_SECRET=sua_chave_super_secreta_123456
```

### 2. Inicie o MySQL
Certifique-se de que o MySQL está rodando na porta 3306.

### 3. Execute a Aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

---

## 🔐 Fluxo de Autenticação com JWT

### 1️⃣ **Registrar um Novo Usuário**

**Endpoint:** `POST /auth/register`

**Requisição (Postman):**
- URL: `http://localhost:8080/auth/register`
- Method: `POST`
- Headers:
  ```
  Content-Type: application/json
  ```
- Body (raw JSON):
  ```json
  {
    "name": "João Silva",
    "email": "joao@example.com",
    "password": "senha123456"
  }
  ```

**Resposta (200 OK):**
```
Vazio (status 200 significa sucesso)
```

---

### 2️⃣ **Fazer Login e Obter JWT Token**

**Endpoint:** `POST /auth/login`

**Requisição (Postman):**
- URL: `http://localhost:8080/auth/login`
- Method: `POST`
- Headers:
  ```
  Content-Type: application/json
  ```
- Body (raw JSON):
  ```json
  {
    "email": "joao@example.com",
    "password": "senha123456"
  }
  ```

**Resposta (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ0YXNrLW1hbmFnZXItYXBpIiwic3ViIjoiam9hb0BleGFtcGxlLmNvbSIsImV4cCI6MTc3NTk0MjU2N30...."
}
```

⚠️ **Copie o valor do token! Você usará isso para acessar endpoints protegidos.**

---

### 3️⃣ **Usar Endpoints Protegidos com JWT**

Para acessar qualquer endpoint protegido, você **DEVE** incluir o token no header `Authorization` com o formato:
```
Authorization: Bearer <SEU_TOKEN_AQUI>
```

**IMPORTANTE:** Não esqueça do `Bearer ` antes do token!

---

## 📝 Exemplos de Requisições

### Criar Tarefa

**Endpoint:** `POST /tasks`

**Requisição (Postman):**
- URL: `http://localhost:8080/tasks`
- Method: `POST`
- Headers:
  ```
  Content-Type: application/json
  Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ0YXNrLW1hbmFnZXItYXBpIiwic3ViIjoiam9hb0BleGFtcGxlLmNvbSIsImV4cCI6MTc3NTk0MjU2N30....
  ```
- Body (raw JSON):
  ```json
  {
    "title": "Fazer dever de casa",
    "description": "Terminar as tarefas de matemática e português"
  }
  ```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "title": "Fazer dever de casa",
  "description": "Terminar as tarefas de matemática e português",
  "status": "PENDING",
  "createdAt": "2026-04-11T16:19:15.596874552"
}
```

---

### Listar Todas as Tarefas do Usuário

**Endpoint:** `GET /tasks`

**Requisição (Postman):**
- URL: `http://localhost:8080/tasks`
- Method: `GET`
- Headers:
  ```
  Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9....
  ```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "title": "Fazer dever de casa",
    "description": "Terminar as tarefas de matemática e português",
    "status": "PENDING",
    "createdAt": "2026-04-11T16:19:15.596874552"
  }
]
```

---

### Buscar Tarefa por ID

**Endpoint:** `GET /tasks/{id}`

**Requisição (Postman):**
- URL: `http://localhost:8080/tasks/1`
- Method: `GET`
- Headers:
  ```
  Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9....
  ```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "title": "Fazer dever de casa",
  "description": "Terminar as tarefas de matemática e português",
  "status": "PENDING",
  "createdAt": "2026-04-11T16:19:15.596874552"
}
```

---

### Atualizar Tarefa

**Endpoint:** `PATCH /tasks/{id}`

**Requisição (Postman):**
- URL: `http://localhost:8080/tasks/1`
- Method: `PATCH`
- Headers:
  ```
  Content-Type: application/json
  Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9....
  ```
- Body:
  ```json
  {
    "title": "Fazer dever de casa URGENTE",
    "description": "Terminar HOJE as tarefas de matemática e português"
  }
  ```

---

### Marcar Tarefa como Concluída

**Endpoint:** `PATCH /tasks/{id}/done`

**Requisição (Postman):**
- URL: `http://localhost:8080/tasks/1/done`
- Method: `PATCH`
- Headers:
  ```
  Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9....
  ```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "title": "Fazer dever de casa",
  "description": "Terminar as tarefas de matemática e português",
  "status": "DONE",
  "createdAt": "2026-04-11T16:19:15.596874552"
}
```

---

### Deletar Tarefa

**Endpoint:** `DELETE /tasks/{id}`

**Requisição (Postman):**
- URL: `http://localhost:8080/tasks/1`
- Method: `DELETE`
- Headers:
  ```
  Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9....
  ```

**Resposta (204 No Content):**
Sem corpo de resposta (apenas status 204)

---

## 🔧 Testando no Swagger UI

1. Acesse `http://localhost:8080/swagger-ui.html`
2. Clique no botão **"Authorize"** (ícone de cadeado no topo direito)
3. Em "value", copie e cole: `Bearer seu_token_aqui`
4. Clique **"Authorize"**
5. Agora você pode testar todos os endpoints protegidos diretamente no Swagger

---

## ⏰ Token JWT

- **Validade:** 2 horas após o login
- **Renovação:** Faça login novamente para obter um novo token quando expirar
- **Formato:** `Header.Payload.Signature` (3 partes separadas por pontos)

---

## ❌ Erros Comuns

| Erro | Causa | Solução |
|------|-------|--------|
| **403 Forbidden** | Token ausente ou inválido no header | Inclua `Authorization: Bearer seu_token` |
| **401 Unauthorized** | Token expirou | Faça login novamente |
| **400 Bad Request** | E-mail já cadastrado | Use um e-mail único |
| **500 Internal Server Error** | JWT_SECRET não definido | Defina a variável de ambiente `JWT_SECRET` |

---

## 🔑 Estrutura do JWT

Um JWT contém 3 partes:

**Header:**
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

**Payload (Claims):**
```json
{
  "iss": "task-manager-api",
  "sub": "joao@example.com",
  "exp": 1775942567
}
```

**Signature:**
```
HMACSHA256(header.payload, JWT_SECRET)
```

Você pode decodificar tokens em `https://jwt.io` para ver as informações (mas não compartilhe tokens sensíveis!).

---

## 📌 Resumo Rápido

```bash
# 1. Registrar
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"João","email":"joao@example.com","password":"senha123"}'

# 2. Login (obter token)
TOKEN=$(curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"joao@example.com","password":"senha123"}' | jq -r '.token')

# 3. Usar token para criar tarefa
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"title":"Minha tarefa","description":"Descrição"}'
```

