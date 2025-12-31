# EncurtaLink

## API de redirecionamento de links desenvolvida como parte do desafio técnico do [Professor Matheus Leandro Ferreira](https://youtube.com/@matheuslf).
Tecnologias usadas:

    Java 21

    Spring Boot 4

    Banco de dados H2

    Protocolo HTTP


## Endpoints da API
### 1. Encurtar URL

Recebe uma URL longa e retorna um código único para acesso.

Requisição:
```
POST /shorten-url
Content-Type: application/json

{
    "url": "https://www.google.com"
}
```

Resposta:
```
HTTP/1.1 200 Ok
Content-Type: application/json

{
    "url": "aBc123X"
}
```

### 2. Redirecionar

Recebe o código único e redireciona o usuário para a URL original.

Requisição:
```
GET /{codigo}
```

Resposta:
```
HTTP/1.1 302 Found
Location: https://www.google.com
```

