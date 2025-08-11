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
      "cargo": "Gerente de TI"
    },
    {
      "id": "uuid-da-empresa-2",
      "name_fant": "Outra Empresa",
      "razao_social": "Outra Empresa LTDA",
      "cnpj": "98765432109876",
      "isResponsible": false,
      "cargo": null
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
  "cargo": "Gerente de TI"
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