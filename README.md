# 🔗 Link Shortener

## 🚀 Sobre o Projeto

Este projeto é um serviço de encurtamento de URL desenvolvido para estruturar e aprofundar conhecimentos em arquitetura **Serverless**, **Infraestrutura como Código (IaC)** e desenvolvimento **Backend escalável** em ambiente **Cloud**.

## ✨ Arquitetura e Tecnologia

O projeto segue a **Arquitetura Hexagonal (Ports and Adapters)**.

| Categoria | Tecnologia / Ferramenta | Descrição |
| :--- | :--- | :--- |
| **Linguagem** | Java | Linguagem principal do projeto. |
| **Framework** | Spring Cloud Function | Utilizado para desenvolver o *handler* Serverless e abstrair os detalhes do *vendor* Cloud. |
| **Computação** | AWS Lambda | Ambiente de execução Serverless da aplicação. |
| **Banco de Dados** | AWS DynamoDB (NoSQL) | Escolhido pela alta performance de escrita e escalabilidade, ideal para a funcionalidade principal. |
| **Segurança** | Spring Security + JWT | Implementação de autenticação e autorização por Token JWT. |
| **Gerenciamento de Segredos** | AWS Secrets Manager | Utilizado para armazenar variáveis sensíveis, como chaves públicas e privadas do JWT. |
| **Infraestrutura como Código (IaC)** | Terraform | Responsável pelo provisionamento completo de todos os recursos AWS (Lambda, DynamoDB, API Gateway, etc.). |
| **CI/CD** | GitHub Actions | Configuração de uma pipeline de Integração e Entrega Contínua para automação do *deploy*. |

## 💡 Funcionalidades (Endpoints da API)

O serviço disponibiliza a seguinte API:

| Método | Endpoint | Descrição | Autenticação |
| :--- | :--- | :--- | :--- |
| `POST` | `/users/register` | Criação de um novo usuário. | **NÃO** |
| `POST` | `/users/login` | Autenticação e geração do Token JWT. | **NÃO** |
| `DELETE` | `/users` | Deleção do usuário autenticado. | **SIM** (JWT) |
| `POST` | `/links/shorten` | Encurtamento de uma URL. | **SIM** (JWT) |
| `GET` | `/{shortUrlId}` | Redireciona para a URL original (pública). | **NÃO** |
| `GET` | `/links` | Lista todos os links encurtados do usuário. | **SIM** (JWT) |
| `GET` | `/links/analytics/{linkId}` | Lista os dados analíticos (cliques, etc.) de um link. | **SIM** (JWT) |
