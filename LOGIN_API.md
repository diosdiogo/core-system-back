# API de Login - Core System Back

## Descrição

A API de login foi atualizada para retornar não apenas o token JWT, mas também todas as informações relevantes do usuário logado, incluindo perfil, empresas associadas e informações sobre responsabilidades em cada empresa.

## Endpoint

```
POST /auth/login
```

## Request Body

```json
{
  "email": "usuario@exemplo.com",
  "password": "senha123"
}
```

## Response

A resposta agora inclui todas as informações do usuário, incluindo responsabilidades:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "name": "Nome do Usuário",
  "email": "usuario@exemplo.com",
  "user": "usuario_sistema",
  "contato": "11999999999",
  "cargo": "Desenvolvedor Senior",
  "profile": {
    "id": "uuid-do-perfil",
    "nome": "Nome do Perfil",
    "status": true
  },
  "companies": [
    {
      "id": "uuid-da-empresa",
      "name_fant": "Nome Fantasia",
      "razao_social": "Razão Social LTDA",
      "cnpj": "12345678901234",
      "isResponsible": true,
      "cargo": "Gerente de TI",
      "status": "ATIVO"
    },
    {
      "id": "uuid-da-empresa-2",
      "name_fant": "Outra Empresa",
      "razao_social": "Outra Empresa LTDA",
      "cnpj": "98765432109876",
      "isResponsible": false,
      "cargo": null,
      "status": "TESTE"
    }
  ]
}
```

## Campos da Resposta

### Campos Principais
- **token**: Token JWT para autenticação
- **name**: Nome completo do usuário
- **email**: Email do usuário
- **user**: Nome de usuário no sistema
- **contato**: Informações de contato (telefone, etc.)
- **cargo**: Cargo/função do usuário na empresa

### Profile
- **id**: UUID do perfil
- **nome**: Nome do perfil
- **status**: Status ativo/inativo do perfil

### Companies
- **id**: UUID da empresa
- **name_fant**: Nome fantasia da empresa
- **razao_social**: Razão social da empresa
- **cnpj**: CNPJ da empresa
- **isResponsible**: Indica se o usuário é responsável por esta empresa
- **cargo**: Cargo/função do usuário na empresa (se for responsável)
- **status**: Status da empresa (ATIVO, BLOQUEADO, TESTE, INATIVO, PENDENTE)

### Campos da Resposta de Usuário Responsável

- **id**: UUID da responsabilidade
- **userId**: UUID do usuário
- **userName**: Nome do usuário
- **userEmail**: Email do usuário
- **userCargo**: Cargo/função do usuário na empresa
- **companyId**: UUID da empresa
- **companyName**: Nome da empresa
- **cargo**: Cargo/função do usuário na empresa (responsabilidade específica)
- **companyStatus**: Status da empresa (ATIVO, BLOQUEADO, TESTE, INATIVO, PENDENTE)

## Endpoint para Cadastrar Responsabilidades

### POST /api/user-responsible

Cadastra um vínculo entre usuário, cargo e empresa.

#### Request Body
```json
{
  "userId": "uuid-do-usuario",
  "companyId": "uuid-da-empresa",
  "cargo": "Gerente de TI"
}
```

#### Response
```json
{
  "id": "uuid-da-responsabilidade",
  "userId": "uuid-do-usuario",
  "userName": "Nome do Usuário",
  "userEmail": "usuario@exemplo.com",
  "companyId": "uuid-da-empresa",
  "companyName": "Nome da Empresa",
  "cargo": "Gerente de TI",
  "companyStatus": "ATIVO"
}
```

## Outros Endpoints de UserResponsible

### GET /api/user-responsible/user/{userId}
Retorna todas as responsabilidades de um usuário específico.

### GET /api/user-responsible/company/{companyId}
Retorna todos os usuários responsáveis de uma empresa específica.

### GET /api/user-responsible/check/{userId}/{companyId}
Verifica se um usuário é responsável por uma empresa específica (retorna true/false).

## Exemplo de Uso

### cURL - Login
```bash
curl -X POST "http://localhost:8080/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@exemplo.com",
    "password": "senha123"
  }'
```

### cURL - Cadastrar Responsabilidade
```bash
curl -X POST "http://localhost:8080/api/user-responsible" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "uuid-do-usuario",
    "companyId": "uuid-da-empresa",
    "cargo": "Gerente de TI"
  }'
```

### JavaScript/Fetch - Login
```javascript
const response = await fetch('/auth/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    email: 'usuario@exemplo.com',
    password: 'senha123'
  })
});

const userData = await response.json();
console.log('Token:', userData.token);
console.log('Usuário:', userData.name);

// Verificar responsabilidades em cada empresa
userData.companies.forEach(company => {
  if (company.isResponsible) {
    console.log(`Responsável por ${company.name_fant} como ${company.cargo}`);
  } else {
    console.log(`Usuário de ${company.name_fant}`);
  }
});
```

## Segurança

- O endpoint usa autenticação JWT
- As senhas são criptografadas usando BCrypt
- O token JWT deve ser incluído no header `Authorization: Bearer {token}` para endpoints protegidos

## Tratamento de Erros

### Usuário não encontrado (404)
```json
{
  "timestamp": "2025-08-11T21:28:15.310+00:00",
  "status": 404,
  "error": "Usuário não encontrado",
  "message": "O email informado não está cadastrado em nossa base de dados. Verifique o email e tente novamente.",
  "path": "/auth/login"
}
```

### Senha incorreta (401)
```json
{
  "timestamp": "2025-08-11T21:28:15.310+00:00",
  "status": 401,
  "error": "Senha incorreta",
  "message": "A senha informada está incorreta. Verifique sua senha e tente novamente.",
  "path": "/auth/login"
}
```

### Token JWT Expirado (401)
```json
{
  "timestamp": "2025-08-11T21:28:15.310+00:00",
  "status": 401,
  "error": "Token expirado",
  "message": "Seu token de acesso expirou. Faça login novamente para obter um novo token.",
  "path": "/api/user"
}
```

### Token JWT Inválido (401)
```json
{
  "timestamp": "2025-08-11T21:28:15.310+00:00",
  "status": 401,
  "error": "Token inválido",
  "message": "O token de acesso fornecido é inválido. Verifique o token e tente novamente.",
  "path": "/api/user"
}
```

### Empresa não encontrada (404)
```json
{
  "timestamp": "2025-08-11T21:28:15.310+00:00",
  "status": 404,
  "error": "Empresa não encontrada",
  "message": "A empresa informada não foi encontrada em nossa base de dados. Verifique o ID da empresa e tente novamente.",
  "path": "/api/user-responsible"
}
```

### Responsabilidade duplicada (409)
```json
{
  "timestamp": "2025-08-11T21:28:15.310+00:00",
  "status": 409,
  "error": "Responsabilidade duplicada",
  "message": "Este usuário já é responsável por esta empresa. Não é possível criar vínculos duplicados.",
  "path": "/api/user-responsible"
}
```

### Dados inválidos (400)
```json
{
  "timestamp": "2025-08-11T21:28:15.310+00:00",
  "status": 400,
  "error": "Dados inválidos",
  "message": "Descrição específica do erro de validação",
  "path": "/api/user-responsible"
}
```

### Erro interno do servidor (500)
```json
{
  "timestamp": "2025-08-11T21:28:15.310+00:00",
  "status": 500,
  "error": "Erro interno do servidor",
  "message": "Ocorreu um erro inesperado. Tente novamente mais tarde ou entre em contato com o suporte.",
  "path": "/auth/login"
}
```

## Notas Técnicas

- A API usa `LEFT JOIN FETCH` para carregar eficientemente os relacionamentos
- As empresas são carregadas através do relacionamento `UserCompany`
- O perfil é carregado através do relacionamento direto com a entidade `Profile`
- As responsabilidades são verificadas através da tabela `UserResponsible`
- Todas as informações são retornadas em uma única chamada para otimizar a performance
- A tabela `UserResponsible` permite que um usuário tenha diferentes cargos em diferentes empresas
- **Status das Empresas**: As empresas possuem um campo status que pode ser ATIVO, BLOQUEADO, TESTE, INATIVO ou PENDENTE, permitindo controle de acesso e visibilidade
- **Tratamento de Tokens JWT**: A API captura e trata adequadamente tokens expirados e inválidos, retornando status 401 com mensagens amigáveis em vez de erros 500 genéricos
- **Expiração de Token**: Os tokens JWT expiram automaticamente após 1 hora (3.600.000 ms) conforme configurado em `application.properties` 

## 📚 **Próximos Passos**

Com todas as migrações executadas, o banco de dados estará completamente configurado e pronto para uso. Você pode:

1. Testar a API de login que retorna todas as informações do usuário
2. Criar novos usuários, empresas e aplicações
3. Implementar funcionalidades adicionais
4. Personalizar os dados de exemplo conforme necessário

---

# 🚀 **API AppEmpresa - Relacionamentos App-Empresa**

## **Base URL**
```
/api/app-empresa
```

## **Endpoints Disponíveis**

### **POST** `/api/app-empresa`
**Cadastrar relacionamento entre aplicação e empresa**

#### Request Body
```json
{
  "appId": "123e4567-e89b-12d3-a456-426614174000",
  "companyId": "123e4567-e89b-12d3-a456-426614174001",
  "ativo": true,
  "status": "ATIVO",
  "validade": "2025-12-31"
}
```

#### Response (201 Created)
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174002",
  "appId": "123e4567-e89b-12d3-a456-426614174000",
  "appName": "Core System",
  "companyId": "123e4567-e89b-12d3-a456-426614174001",
  "companyName": "Arte Visual Soft",
  "ativo": true,
  "status": "ATIVO",
  "validade": "2025-12-31",
  "createdAt": "2025-08-13T18:00:00",
  "updatedAt": "2025-08-13T18:00:00"
}
```

### **GET** `/api/app-empresa`
**Listar todos os relacionamentos**

#### Response (200 OK)
```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174002",
    "appId": "123e4567-e89b-12d3-a456-426614174000",
    "appName": "Core System",
    "companyId": "123e4567-e89b-12d3-a456-426614174001",
    "companyName": "Arte Visual Soft",
    "ativo": true,
    "status": "ATIVO",
    "validade": "2025-12-31",
    "createdAt": "2025-08-13T18:00:00",
    "updatedAt": "2025-08-13T18:00:00"
  }
]
```

### **GET** `/api/app-empresa/{id}`
**Buscar relacionamento por ID**

#### Response (200 OK)
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174002",
  "appId": "123e4567-e89b-12d3-a456-426614174000",
  "appName": "Core System",
  "companyId": "123e4567-e89b-12d3-a456-426614174001",
  "companyName": "Arte Visual Soft",
  "ativo": true,
  "status": "ATIVO",
  "validade": "2025-12-31",
  "createdAt": "2025-08-13T18:00:00",
  "updatedAt": "2025-08-13T18:00:00"
}
```

### **GET** `/api/app-empresa/company/{companyId}`
**Buscar relacionamentos por empresa**

#### Response (200 OK)
```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174002",
    "appId": "123e4567-e89b-12d3-a456-426614174000",
    "appName": "Core System",
    "companyId": "123e4567-e89b-12d3-a456-426614174001",
    "companyName": "Arte Visual Soft",
    "ativo": true,
    "status": "ATIVO",
    "validade": "2025-12-31",
    "createdAt": "2025-08-13T18:00:00",
    "updatedAt": "2025-08-13T18:00:00"
  }
]
```

### **GET** `/api/app-empresa/app/{appId}`
**Buscar relacionamentos por aplicação**

#### Response (200 OK)
```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174002",
    "appId": "123e4567-e89b-12d3-a456-426614174000",
    "appName": "Core System",
    "companyId": "123e4567-e89b-12d3-a456-426614174001",
    "companyName": "Arte Visual Soft",
    "ativo": true,
    "status": "ATIVO",
    "validade": "2025-12-31",
    "createdAt": "2025-08-13T18:00:00",
    "updatedAt": "2025-08-13T18:00:00"
  }
]
```

### **GET** `/api/app-empresa/company/{companyId}/app/{appId}`
**Buscar relacionamento específico por empresa e aplicação**

#### Response (200 OK)
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174002",
  "appId": "123e4567-e89b-12d3-a456-426614174000",
  "appName": "Core System",
  "companyId": "123e4567-e89b-12d3-a456-426614174001",
  "companyName": "Arte Visual Soft",
  "ativo": true,
  "status": "ATIVO",
  "validade": "2025-12-31",
  "createdAt": "2025-08-13T18:00:00",
  "updatedAt": "2025-08-13T18:00:00"
}
```

### **PUT** `/api/app-empresa/{id}`
**Atualizar relacionamento existente**

#### Request Body
```json
{
  "ativo": false,
  "status": "INATIVO",
  "validade": "2025-06-30"
}
```

#### Response (200 OK)
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174002",
  "appId": "123e4567-e89b-12d3-a456-426614174000",
  "appName": "Core System",
  "companyId": "123e4567-e89b-12d3-a456-426614174001",
  "companyName": "Arte Visual Soft",
  "ativo": false,
  "status": "INATIVO",
  "validade": "2025-06-30",
  "createdAt": "2025-08-13T18:00:00",
  "updatedAt": "2025-08-13T18:17:00"
}
```

### **DELETE** `/api/app-empresa/{id}`
**Excluir relacionamento**

#### Response (204 No Content)
Sem corpo de resposta

## **Campos dos DTOs**

### **AppEmpresaDTO (Request)**
- **appId** (UUID, obrigatório): ID da aplicação
- **companyId** (UUID, obrigatório): ID da empresa
- **ativo** (Boolean, opcional): Status ativo/inativo (padrão: true)
- **status** (String, opcional): Status do relacionamento (padrão: "ATIVO")
- **validade** (Date, opcional): Data de validade

### **AppEmpresaResponseDTO (Response)**
- **id** (UUID): ID do relacionamento
- **appId** (UUID): ID da aplicação
- **appName** (String): Nome da aplicação
- **companyId** (UUID): ID da empresa
- **companyName** (String): Nome da empresa
- **ativo** (Boolean): Status ativo/inativo
- **status** (String): Status do relacionamento
- **validade** (Date): Data de validade
- **createdAt** (LocalDateTime): Data de criação
- **updatedAt** (LocalDateTime): Data da última atualização

## **Códigos de Status HTTP**

- **200 OK**: Operação realizada com sucesso
- **201 Created**: Relacionamento criado com sucesso
- **204 No Content**: Relacionamento excluído com sucesso
- **400 Bad Request**: Dados inválidos na requisição
- **404 Not Found**: Recurso não encontrado
- **409 Conflict**: Conflito (relacionamento duplicado)
- **500 Internal Server Error**: Erro interno do servidor

## **Exemplos de Uso**

### **cURL - Criar Relacionamento**
```bash
curl -X POST "http://localhost:8080/api/app-empresa" \
  -H "Content-Type: application/json" \
  -d '{
    "appId": "123e4567-e89b-12d3-a456-426614174000",
    "companyId": "123e4567-e89b-12d3-a456-426614174001",
    "ativo": true,
    "status": "ATIVO",
    "validade": "2025-12-31"
  }'
```

### **cURL - Buscar por Empresa**
```bash
curl -X GET "http://localhost:8080/api/app-empresa/company/123e4567-e89b-12d3-a456-426614174001"
```

### **cURL - Buscar por Aplicação**
```bash
curl -X GET "http://localhost:8080/api/app-empresa/app/123e4567-e89b-12d3-a456-426614174000"
```

### **cURL - Buscar por Empresa e Aplicação**
```bash
curl -X GET "http://localhost:8080/api/app-empresa/company/123e4567-e89b-12d3-a456-426614174001/app/123e4567-e89b-12d3-a456-426614174000"
```

### **JavaScript/Fetch - Criar Relacionamento**
```javascript
const response = await fetch('/api/app-empresa', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    appId: '123e4567-e89b-12d3-a456-426614174000',
    companyId: '123e4567-e89b-12d3-a456-426614174001',
    ativo: true,
    status: 'ATIVO',
    validade: '2025-12-31'
  })
});

const appEmpresa = await response.json();
console.log('Relacionamento criado:', appEmpresa);
```

## **Validações e Regras de Negócio**

1. **Relacionamento Único**: Não é possível criar relacionamentos duplicados entre a mesma aplicação e empresa
2. **Aplicação Existente**: A aplicação deve existir no sistema
3. **Empresa Existente**: A empresa deve existir no sistema
4. **Campos Obrigatórios**: appId e companyId são obrigatórios
5. **Valores Padrão**: ativo (true) e status ("ATIVO") são definidos automaticamente se não informados
6. **Timestamps**: createdAt e updatedAt são gerenciados automaticamente

## **Casos de Uso**

- **Controle de Acesso**: Definir quais aplicações cada empresa pode acessar
- **Licenciamento**: Controlar validade e status de licenças por empresa
- **Configuração**: Personalizar funcionalidades por empresa
- **Auditoria**: Rastrear uso de aplicações por empresa
- **Segurança**: Isolar dados entre empresas 