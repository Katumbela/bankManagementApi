 

### **Descrição do Projeto**  
O **Sistema de Gerenciamento Bancário com Open Banking** é uma API desenvolvida em **Java com Spring Boot**, que segue os padrões do **Open Banking Brasil**. A API permite o cadastro e autenticação de usuários, gerenciamento de contas bancárias e transferência entre contas. Além disso, conta com **OAuth2**, permitindo que aplicações externas acessem os dados bancários dos usuários, proporcionando integração com sistemas de controle financeiro e fintechs.

---


```md
# 📌 Sistema Bancário com Open Banking  
🚀 API RESTful desenvolvida em **Java + Spring Boot** que implementa funcionalidades bancárias e suporte a **Open Banking** com OAuth2.

## 🏆 Diferenciais  
✅ **Open Banking**: Permite integração segura com outras aplicações financeiras.  
✅ **Autenticação OAuth2**: Suporte para acessos autorizados por terceiros.  
✅ **Gerenciamento de Contas**: Criar, listar e consultar saldo de contas bancárias.  
✅ **Transferências Seguras**: Realizar transações entre contas.  
✅ **Swagger Documentation**: API documentada com OpenAPI.  

## 📌 Tecnologias Utilizadas  
- Java 17+  
- Spring Boot  
- Spring Security + OAuth2  
- MySQL  
- Swagger (OpenAPI)  
- JUnit para testes  

## ⚡ Funcionalidades  
### **1️⃣ Cadastro e Autenticação de Usuários**  
- Registro de usuários  
- Login com JWT  
- Autenticação OAuth2 para acesso externo  

### **2️⃣ Gerenciamento de Contas**  
- Criar nova conta bancária  
- Consultar saldo  

### **3️⃣ Transferências Bancárias**  
- Realizar transferência entre contas  
- Histórico de transações  

### **4️⃣ Open Banking API**  
- Permite que **aplicações externas** acessem dados bancários com **OAuth2**  
- Implementa **padrões do Open Banking Brasil**  

## 🚀 Como Executar  
1️⃣ **Clone o repositório**  
```bash
git clone https://github.com/seuusuario/sistema-bancario-open-banking.git
cd sistema-bancario-open-banking
```

2️⃣ **Configure o MySQL**  
- Crie um banco de dados chamado `banco_db`.  

3️⃣ **Configure as variáveis de ambiente**  
Crie um arquivo `.env` e adicione:  
```env
DB_URL=jdbc:mysql://localhost:3306/banco_db
DB_USER=root
DB_PASS=suasenha
JWT_SECRET=sua_chave_secreta
OAUTH2_CLIENT_ID=seu_client_id
OAUTH2_CLIENT_SECRET=sua_chave_oauth2
```

4️⃣ **Execute o projeto**  
```bash
./mvnw spring-boot:run
```

5️⃣ **Acesse a documentação Swagger**  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🏦 Open Banking - Como Usar OAuth2  
1. **Registre uma aplicação cliente** (exemplo: um app de finanças pessoais).  
2. **Obtenha um token OAuth2** enviando um request para `/oauth/token`.  
3. **Use o token para acessar dados bancários** através da API.  

Exemplo de requisição para obter um token OAuth2:  
```bash
curl -X POST "http://localhost:8080/oauth/token" \
     -H "Content-Type: application/x-www-form-urlencoded" \
     -d "grant_type=client_credentials&client_id=seu_client_id&client_secret=sua_chave_oauth2"
```

---

## 👨‍💻 Autor  
Desenvolvido por **João Afonso Katombela**. Conecte-se comigo no [LinkedIn](https://www.linkedin.com/in/joao-afonso-katumbela) 🚀.
```
 