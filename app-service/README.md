---

# 🔗 **Serverless Link Shortener**

Este projeto é uma implementação robusta e escalável de um encurtador de links, construído com um foco em **arquitetura serverless** e utilizando um conjunto de tecnologias modernas e de alto desempenho. Desenvolvido para demonstrar proficiência em sistemas distribuídos e computação em nuvem, este serviço oferece uma solução eficiente para transformar URLs longas em links curtos e gerenciáveis.

---

## 🚀 **Tecnologias Utilizadas (e Minhas Habilidades em Destaque)**

Este projeto foi meticulosamente elaborado para showcase uma ampla gama de habilidades técnicas, incluindo:

### ☕ **Java Spring 21**
A base da aplicação é construída com **Java 21**, a versão mais recente do Java LTS, aproveitando suas melhorias de desempenho e novas funcionalidades. A escolha do **Spring Framework** na sua versão mais recente demonstra minha capacidade de desenvolver aplicações robustas e escaláveis, utilizando o ecossistema Spring para injeção de dependências, configuração e gerenciamento de transações. Meu domínio em Spring permite a criação de APIs RESTful eficientes e a integração de diversos serviços.

### ☁️ **Spring Cloud Function**
Para habilitar a arquitetura serverless, utilizei **Spring Cloud Function**. Esta framework é crucial para a portabilidade do código para diferentes provedores de nuvem, permitindo que a mesma lógica de negócio seja executada como uma função serverless. Isso destaca minha experiência em construir aplicações agnósticas à infraestrutura, prontas para serem implantadas em ambientes de computação em nuvem sem sacrificar a flexibilidade.

### ⚡ **AWS Lambda**
A implantação do serviço é realizada via **AWS Lambda**, um serviço de computação serverless que executa o código em resposta a eventos. Minha experiência com AWS Lambda inclui o empacotamento e a otimização de funções Java para execução eficiente, bem como a configuração de triggers e gerenciamento de permissões. Isso valida minha habilidade em projetar e implementar soluções nativas da nuvem que se beneficiam da escalabilidade automática e do modelo de pagamento por uso.

### 🐳 **Docker Compose**
Para um ambiente de desenvolvimento local consistente e simplificado, o projeto utiliza **Docker Compose**. Isso permite que todos os serviços dependentes (como o DynamoDB local) sejam facilmente inicializados com um único comando. Meu uso do Docker Compose reflete minha proficiência em contêineres e na criação de ambientes de desenvolvimento reproduzíveis, garantindo que o projeto possa ser executado em qualquer máquina com as dependências corretas pré-configuradas.

### 🗄️ **DynamoDB**
Como banco de dados NoSQL, o **Amazon DynamoDB** foi escolhido por sua escalabilidade, baixa latência e alta disponibilidade, características ideais para uma arquitetura serverless. Minha experiência com DynamoDB abrange a modelagem de dados NoSQL, a escrita de consultas eficientes e o gerenciamento de tabelas. Isso demonstra minha capacidade de selecionar e utilizar bancos de dados que se alinham com os requisitos de desempenho e escalabilidade de aplicações distribuídas.

### 🔒 **Spring Security**
A segurança é uma prioridade, e **Spring Security** foi implementado para proteger as APIs e garantir que apenas usuários autorizados possam interagir com o serviço. Minha proficiência em Spring Security inclui a configuração de autenticação (JWT ou OAuth2) e autorização baseada em roles, protegendo endpoints específicos e garantindo a integridade dos dados. Isso evidencia minha capacidade de construir aplicações seguras e aderir às melhores práticas de segurança.

### ☁️ **Arquitetura Serverless**
O projeto é um exemplo prático de **arquitetura serverless**, minimizando a sobrecarga operacional e otimizando o custo. Minha expertise em serverless se estende ao design de soluções que se beneficiam do desacoplamento de serviços, da escalabilidade sob demanda e da resilição inerente a este paradigma. Isso demonstra minha visão arquitetural para construir sistemas modernos e eficientes.

---

## 🏗️ **Estrutura do Projeto (Arquitetura Hexagonal)**

Este projeto adota a **Arquitetura Hexagonal (Ports and Adapters)** para garantir um alto nível de separação de preocupações, testabilidade e manutenibilidade. Essa abordagem permite que o domínio da aplicação seja independente de tecnologias específicas de infraestrutura ou interfaces externas. Minha escolha por essa arquitetura destaca minha habilidade em projetar sistemas com:

* **Baixo acoplamento**: As dependências externas são gerenciadas através de interfaces (ports) e implementações (adapters), isolando a lógica de negócio central.
* **Alta coesão**: As responsabilidades são bem definidas dentro de cada camada.
* **Facilidade de teste**: O domínio pode ser testado isoladamente, sem a necessidade de infraestrutura real.
