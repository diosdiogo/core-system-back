# API de Login - Core System Back

## Descrição

A API de login foi atualizada para retornar não apenas o token JWT, mas também todas as informações relevantes do usuário logado, incluindo perfil e empresas associadas.

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

A resposta agora inclui todas as informações do usuário:

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
      "cnpj": "12345678901234"
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

## Exemplo de Uso

### cURL
```bash
curl -X POST "http://localhost:8080/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@exemplo.com",
    "password": "senha123"
  }'
```

### JavaScript/Fetch
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
console.log('Empresas:', userData.companies);
```

## Segurança

- O endpoint usa autenticação JWT
- As senhas são criptografadas usando BCrypt
- O token JWT deve ser incluído no header `Authorization: Bearer {token}` para endpoints protegidos

## Tratamento de Erros

### Usuário não encontrado
```json
{
  "error": "Usuário não encontrado"
}
```

### Senha incorreta
```json
{
  "error": "Senha incorreta"
}
```

## Notas Técnicas

- A API usa `LEFT JOIN FETCH` para carregar eficientemente os relacionamentos
- As empresas são carregadas através do relacionamento `UserCompany`
- O perfil é carregado através do relacionamento direto com a entidade `Profile`
- Todas as informações são retornadas em uma única chamada para otimizar a performance 