# 📍 Criptografia de Dados Sensíveis

[![SPRING FRAMEWORK](https://img.shields.io/badge/Spring%20framework-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://github.com/BerettaSM/exemplo-readme/blob/main/LICENSE)
[![JAVA](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://github.com/BerettaSM/exemplo-readme/blob/main/LICENSE) 
![GitHub repo size](https://img.shields.io/github/repo-size/BerettaSM/cryptography?style=for-the-badge)

> O projeto foi desenvolvido como uma solução do desafio do [Backend-br](https://github.com/backend-br/desafios/blob/master/cryptography/PROBLEM.md).

Este projeto tem como objetivo implementar a criptografia de dados sensíveis em uma API RESTful utilizando o algoritmo AES para garantir que campos como userDocument e creditCardToken não sejam expostos diretamente. A criptografia e a descriptografia ocorrem de forma transparente para a API, sendo executadas automaticamente durante a conversão dos dados entre a entidade e o banco de dados.

## ☕ Tecnologias utilizadas

- Java 21
- Spring Boot
- H2 Database (para desenvolvimento)
- PostgreSQL (para homologação, via Docker)
- AES (Advanced Encryption Standard) para criptografia simétrica
- Docker (para ambiente de homologação/teste)
- Maven
- Liquibase
- Lombok (para simplificação do código)

## 💻 Pré-requisitos

Caso queira rodar este projeto na sua própria máquina, veja os requisitos abaixo:

- Java `21`
- Docker (para o ambiente de homologação com PostgreSQL)

## 🔗 Variáveis de Ambiente

As seguintes variáveis de ambiente podem ser configuradas ():

- APP_PROFILE: O perfil do projeto: 'dev' (default) ou 'test' (homologação).
- CRYPT_SALT: O salt usado na geração da chave de criptografia.
- CRYPT_PASSWORD: A senha secreta usada no algoritmo AES.

## 🚀 Rodando o projeto

Para rodar o projeto, siga estas etapas:

1. Clone o repositório:
```bash
git clone https://github.com/BerettaSM/cryptography.git
```

2. Entre na pasta raiz do projeto
```bash
cd cryptography/
```

3. Execute o projeto com **Maven**:

    3.1 Perfil dev (h2):
    ```bash
    ./mvn spring-boot:run
    ```

    3.1 Perfil test (homologação/postgres, requer docker):
    ```bash
    APP_PROFILE=test ./mvn spring-boot:run
    ```

## 🔗 Endpoints disponíveis

## OBS: Para cada endpoint (excluindo DELETE), existe um endpoint idêntico que retorna os dados descriptografados, para observação. Ele pode ser acessado colocando o sufixo "**/decrypted**" no final das URLs.

#### ➕ Cadastrar uma nova transfer

- Endpoint:
```http
  POST /transfers
```

- Request Body (JSON):
```json
    {
        "userDocument": "33677899095",
        "creditCardToken": "33e93253-1b67-4b96-bbaa-488331c38c01",
        "value": 1000
    }
```

- Exemplo de requisição:

```bash
curl -XPOST \
    -H "Content-Type: application/json" \
    -H "Accept: application/json" \
    -d '{ "userDocument": "33677899095", "creditCardToken": "33e93253-1b67-4b96-bbaa-488331c38c01", "value": 1000 }' \
    http://localhost:8080/transfers
```

- Respostas:

> - **201**: Transfer cadastrada com sucesso.
> - **422**: Falha de validação (userDocument inválido ou nulo, creditCardToken inválido ou nulo, value nulo ou negativo).

| Parâmetro         | Tipo     | Descrição                                           |
|:------------------|:---------|:----------------------------------------------------|
| `userDocument`    | `string` | **Obrigatório**. Um CPF válido                      |
| `creditCardToken` | `string` | **Obrigatório**. Um UUID como token                 |
| `value`           | `int`    | **Obrigatório**. O valor da transferência           |

#### 📍 Buscar uma transfer por id

- Endpoint:
```http
  GET /transfers/{id}
```

- Exemplo de requisição:

```bash
curl http://localhost:8080/transfers/1
```

- Respostas:

> - **200**: Transfer obtido com sucesso.
> - **404**: Transfer não existe.

- Exemplo de resposta:

```json
    {
        "id": 1,
        "userDocument": "hipkMqw2wamM99CK+uN2JJdjk9EerFo9ThAdsEHumuE=",
        "creditCardToken": "ezQU81ypkph0gd0Q5BCdEXezqc9Yje9cPNuAinkdCqvLXxZfUUWWZ+gb2oLCuVWAegyEd2qFHMJTdiO7fnIMeQ==",
        "value": 5999
    }
```

#### 📋 Listar todas as transfers

- Endpoint:
```http
  GET /transfers
```

- Exemplo de requisição:

```bash
curl http://localhost:8080/transfers
```

- Respostas:

> - **200**: Transfers obtidos com sucesso.

- Exemplo de resposta:

```json
{
    "content": [
        {
            "id": 1,
            "userDocument": "hipkMqw2wamM99CK+uN2JJdjk9EerFo9ThAdsEHumuE=",
            "creditCardToken": "ezQU81ypkph0gd0Q5BCdEXezqc9Yje9cPNuAinkdCqvLXxZfUUWWZ+gb2oLCuVWAegyEd2qFHMJTdiO7fnIMeQ==",
            "value": 5999
        },
        {
            "id": 2,
            "userDocument": "fWN6B6KvctRpFaJc5oLz6HvwidzcjPmE/ZWc31n8kPg=",
            "creditCardToken": "5A3u+D53d9fAU12rwJ/amR70JdAnxn+RrcsKcbDxRNPsm22/kvRyqhDK7rY5CmloAjCwKTCnioWPOZ/e9Y+K+Q==",
            "value": 1000
        },
        {
            "id": 3,
            "userDocument": "En4Cg+2DU4FSD7TyDMASn1FE9tiKx+QCdPUk7iGgau4=",
            "creditCardToken": "R8aVqs0+By9gJGgzJyqa4qeqQSRGeqC1OZt13iicbiwmT/82PNlTc/jZnf6hczNBjk9fxZezjRzWuAxJPm8hiQ==",
            "value": 1500
        }
    ],
    "page": {
        "size": 20,
        "number": 0,
        "totalElements": 3,
        "totalPages": 1
    }
}
```

#### 📍 Atualizar uma transfer

- Endpoint:
```http
  PATCH /transfers/{id}
```

- Exemplo de requisição:

```bash
curl -XPATCH \
    -H "Content-Type: application/json" \
    -H "Accept: application/json" \
    -d '{ "value": 999 }' \
    http://localhost:8080/transfers/1
```

- Respostas:

> - **200**: Transfer atualizado com sucesso.
> - **422**: Falha de validação (userDocument inválido, creditCardToken inválido, value negativo).

- Exemplo de resposta:

```json
    {
        "id": 1,
        "userDocument": "hipkMqw2wamM99CK+uN2JJdjk9EerFo9ThAdsEHumuE=",
        "creditCardToken": "ezQU81ypkph0gd0Q5BCdEXezqc9Yje9cPNuAinkdCqvLXxZfUUWWZ+gb2oLCuVWAegyEd2qFHMJTdiO7fnIMeQ==",
        "value": 999
    }
```

| Parâmetro         | Tipo     | Descrição                                           |
|:------------------|:---------|:----------------------------------------------------|
| `userDocument`    | `string` | **Opcional**. Um CPF válido                         |
| `creditCardToken` | `string` | **Opcional**. Um UUID como token                    |
| `value`           | `int`    | **Opcional**. O valor da transferência              |

#### 📍 Deletar uma transfer

- Endpoint:
```http
  DELETE /transfers/{id}
```

- Exemplo de requisição:

```bash
curl -XDELETE http://localhost:8080/transfers/1
```

- Respostas:

> - **204**: Transfer deletada com sucesso.
> - **404**: Transfer não existe.

## 📚 Algoritmo AES - Criptografia Simétrica

O AES (Advanced Encryption Standard) é um algoritmo de criptografia simétrica amplamente utilizado. Ele utiliza a mesma chave para criptografar e descriptografar os dados. Neste projeto, a criptografia ocorre utilizando uma chave secreta derivada de uma senha e de um salt. A chave gerada é então usada para criptografar e descriptografar os dados sensíveis, garantindo que os dados não sejam expostos diretamente ao banco de dados ou à API, embora endpoints especiais que entregam os dados descriptografados sejam disponibilizados, para observação.

## 📚 Como o AES é Integrado no Projeto

A criptografia e a descriptografia dos dados sensíveis são realizadas de maneira transparente usando a anotação **@SensitiveData**. Quando um campo é anotado com **@SensitiveData**, ele será automaticamente criptografado antes de ser salvo no banco de dados e será descriptografado quando acessado via API.

O **SensitiveDataService** realiza a criptografia e a descriptografia através de reflection, procurando por campos anotados com **@SensitiveData** nos objetos passados para o serviço. A chave secreta para a criptografia é gerada dinamicamente com base nas variáveis de ambiente, garantindo que a solução seja robusta e segura, mesmo com reinicializações do servidor.

## 📄 Licença

Este projeto é licenciado sob os termos da [MIT License](LICENSE).