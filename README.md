# Golden Raspberry Awards

## Pré-requisitos
Antes de iniciar, certifique-se de que você tenha os seguintes requisitos instalados em seu ambiente de desenvolvimento:

- [Java 17](https://openjdk.java.net/projects/jdk/17/)
- [Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)
- Uma IDE de sua escolha (recomendamos o [IntelliJ IDEA](https://www.jetbrains.com/idea/))

Siga estas etapas para executar o projeto em seu ambiente local:

1. Clone o repositório:
```bash
git clone https://gitlab.com/erick-kleim/golden-raspberry-awards.git
```
2. Navegue até o diretório do projeto
```bash
cd awards
```
3. Execute a aplicação
```bash
mvn spring-boot:run
```
Isso iniciará a aplicação em http://localhost:8080.

## Upload de arquivo CSV
Para carregar dados de um arquivo CSV, siga estas etapas:
1. Prepare um arquivo CSV com o seguinte cabeçalho: year;title;studios;producers;winner.
2. Renomeie o arquivo como "movielist.csv".
3. Coloque o arquivo no diretório src/main/resources do projeto.
- A coluna "producers" aceita uma lista com nomes de produtores. E os separadores aceitos neste caso são: pontuação virgula "," e palavra reservada " and ".

### Testes com upload de arquivo CSV
Se você deseja carregar um arquivo CSV específico durante os testes da aplicação, siga estas etapas:
1. Edite a propriedade csvloader.enabled para true no arquivo src/test/resources/application.properties.
2. Coloque o arquivo de teste no diretório src/test/resources do projeto, seguindo as mesmas diretrizes do cabeçalho e nomenclatura mencionadas anteriormente.

## Documentação swagger
- [Swegger UI](http://localhost:8080/swagger-ui/index.html): Interface interativa da API.
- [API Docs](http://localhost:8080/v3/api-docs): Documentação YAML

## Interface grafica BD
- O console do SGBD pode ser acessado no link
  - [H2 Console](http://localhost:8080/h2-console)
- Use as seguintes credenciais para fazer login
  - User Name: usuariobd
  - Password: senhabd