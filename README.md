# Fórum Hub

![Badge do Challenge Forum Hub](./badge-forum-hub.png)

O Fórum Hub consiste em uma API REST voltada para o gerenciamento de tópicos de um fórum, permitindo operações completas de CRUD. O projeto foi desenvolvido como Challenge final da especialização de Back-End do programa ONE Oracle + Alura, consolidando conceitos avançados de Java e Spring Boot e boas práticas no desenvolvimento de APIs.

## Tecnologias e conceitos utilizados

- **Spring Boot** para criação e configuração da aplicação.
- **Arquitetura em camadas** para separação de responsabilidades.
- **Spring Data JPA** para persistência de dados.
- **MySQL** para armazenamento em banco de dados relacional.
- **Flyway** para versionamento do banco de dados com migrations.
- **Spring Security** para autenticação e autorização.
- **JWT** para autenticação stateless baseada em tokens.
- **Tratamento centralizado de exceções** para gerenciamento de erros.
- **SpringDoc** para geração automática de documentação interativa da API.

## Funcionalidades

- **Autenticação de usuários**: login via email e senha com retorno de token JWT.

- **Criar tópico**: cadastro de novos tópicos no fórum por usuários autenticados.

- **Listar tópicos**: listagem paginada de tópicos ativos, ordenada por data de criação, permitindo filtrar por curso, ano e status.

- **Detalhar tópico**: exibe as informações de um tópico específico a partir do seu ID.

- **Atualizar tópico**: permite ao autor ou a usuários com permissão especial modificar o título, a mensagem, o curso e o status de um tópico existente.

- **Excluir tópico**: permite ao autor ou a usuários com permissão especial remover um tópico do sistema por meio de soft delete.

## Endpoints

A tabela abaixo apresenta os endpoints disponíveis na API. As operações relacionadas a tópicos requerem autenticação via token JWT obtido por meio do endpoint `/login`.

| Método | Endpoint | Descrição |
|------|------|------|
| POST | `/login` | Autenticação do usuário |
| POST | `/topicos` | Criar novo tópico |
| GET | `/topicos` | Listar tópicos |
| GET | `/topicos/{id}` | Detalhar tópico |
| PUT | `/topicos/{id}` | Atualizar tópico |
| DELETE | `/topicos/{id}` | Excluir tópico |

## Instalação e Execução

1.  **Clone o repositório**.
    ```bash
    git clone https://github.com/brunaeloisa/forum-hub.git
    ```

2. Abra a pasta do projeto na IDE de sua preferência.

3. Crie um banco de dados MySQL para a aplicação.

4. Configure o banco de dados criando variáveis de ambiente `DB_HOST`, `DB_NAME`, `DB_USER` e `DB_PASSWORD` ou substituindo as informações diretamente no arquivo `src/main/resources/application.properties`.

    ```properties
    spring.datasource.url=jdbc:mysql://${DB_HOST}/${DB_NAME}
    spring.datasource.username=${DB_USER}
    spring.datasource.password=${DB_PASSWORD}
    ```

5. Configure uma secret key para geração do token JWT por meio da variável `SECRET_JWT`.

    ```properties
    api.security.token.secret=${SECRET_JWT}
    ```

6. Execute a classe `ForumHubApplication.java`.

## Documentação

Após iniciar a aplicação, a documentação interativa gerada pelo SpringDoc estará disponível em: `/swagger-ui/index.html`.