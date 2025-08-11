# Simulador de empréstimo

## 📖 Sobre o projeto

Este projeto é um backend em Java 21 com Spring Boot que expõe um endpoint para simular empréstimos com base nos dados fornecidos pelo usuário.

A API está documentada via Swagger/OpenAPI.

---

## 🚀 Tecnologias utilizadas

- Java 21
- Spring Boot 3.5.4
- Lombok
- SpringDoc OpenAPI 2.8.9
- Gradle 8+
- MapStruct

---

## Pré-requisitos

- [Java 21+](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Gradle](https://gradle.org/)
- IDE da sua preferência (IntelliJ, VS Code, Eclipse...)

---

## Requisito opcional

- MailHog (Para visualizar os emails enviados pelo sistema)
- [Download MailHog](https://github.com/mailhog/MailHog/releases/v1.0.0)
- Para rodar o MailHog basta baixar e executar o arquivo, em seguida no seu navegador acesse http://localhost:8025
- Por fim a flag "envia-email" no application.properties deve ser setata como true, por default ela está como false.

## Para startar o projeto

1. Após baixar/clonar o projeto, execute o comando:

```bash
 ./gradlew bootRun
```

3. Acesse a API:

```bash
 POST http://localhost:8080/api/emprestimos/simular
Request body:
{
    "valorEmprestimo": 10000,
    "dataNascimento": "1988-12-17",
    "prazoMeses": 36
}

Response:
{
    "valorEmprestimo": 10000,
    "prazoMeses": 36,
    "valorTotalAPagar": 10374.48,
    "parcelaMensal": 288.18,
    "totalJurosPagos": 374.48,
    "jurosAnual": 0.03,
    "jurosMensal": 0.002
}
```
4. ## 📖 Documentação da API
### A documentação interativa está disponível em:

[Swagger: Simulação de Empréstimos](http://localhost:8080/swagger-ui/index.html#/emprestimo-controller/simular)<br>


---
## Estrutura do projeto

```bash
src/
├── main/
│   ├── java/
│   │   └── com/creditas/simulador_emprestimo/
│   │       ├── configs/
│   │       │       ├── mappers/
│   │       │       ├── properties/
│   │       ├── controller/
│   │       │       ├── data/
│   │       │       │   ├── request/
│   │       │       │   ├── response/
│   │       │       ├── mappers/
│   │       ├── exception/
│   │       │       ├── handler/
│   │       ├── service/
│   │       │       ├── data/
│   │       │       │   ├── input/
│   │       │       │   ├── output/
│   │       │       ├── interfaces/
│   │       │       ├── mappers/
│   └── resources/
│       └── application.properties
└── test/
└── java/
└── com/creditas/simulador_emprestimo/
```
O projeto está estruturado com as camadas principais:

- **Controller**: Interface de entrada, responsável por receber e responder às requisições HTTP.
- **Service**: Contém a lógica de negócio e implementa as regras centrais da aplicação.
- **Configs**: Configurações da aplicação.

OBS: O projeto utiliza o MapStruct para mapeamento entre objetos de entrada e saída, facilitando a conversão de dados entre camadas.
Camadas de relacionamento com banco de dados como por exemplo Gateway e Repository não foram implementadas, pois o projeto não possui persistência de dados.
