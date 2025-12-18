# 🏗️ BIP Teste Integrado

## 🎯 Objetivo
Criar solução completa em camadas (DB, EJB, Backend, Frontend), corrigindo bug em EJB e entregando aplicação funcional.

## 🧰 Ferramentas
- Java 17
- Maven 
- PostgreSQL
- npm
- ng

## 📦 Estrutura
- db/: scripts schema e seed
- ejb-module/: serviço EJB com bug a ser corrigido
- backend-module/: backend Spring Boot
- frontend/: app Angular
- docs/: instruções e critérios
- .github/workflows/: CI

## 🧱 AsBuilt
1. No Postgres executar:
    psql -h localhost -U <seu_usuario> -d postgres -f db/schema.sql
    psql -h localhost -U <seu_usuario> -d postgres -f db/seed.sql

2. Build da aplicação Backend. Na pasta raiz do projeto, executar:
    mvn clean install -U
    
3. Rodar a aplicação Backend. Na pasta raiz do projeto, executar:
    java -jar backend-module/target/backend-module-0.0.1.jar

4. Build e rodar a aplicação Frontend, executar:
    npm install
    ng serve --port 4200

5. Testar Backend via Swagger ou Postman.
- http://localhost:8080/api/v1/beneficios/swagger-ui.html
- [Collection Postman](./docs/BIP-teste-integrado.postman_collection.json)

6. Testar Frontend via web.
- http://localhost:4200

## 🧾 Evidências dos testes
- 

