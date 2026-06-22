# Morhar Automação — Sistema de Gerenciamento de Chamados

Sistema web para gerenciamento de chamados técnicos, clientes e agenda de visitas da empresa Morhar Automação.

---

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Backend | Java 21 · Spring Boot 3.3.5 |
| Persistência | Spring Data JPA · Hibernate · H2 Database |
| Frontend | Thymeleaf · Bootstrap Icons |
| Segurança | Spring Security 6 |
| Build | Maven |

---

## Funcionalidades

### Perfil ADMIN
- Dashboard com contadores de chamados por status
- Cadastro, edição e exclusão de **clientes** (Pessoa Física e Jurídica)
- Cadastro, edição e exclusão de **técnicos**
- Abertura, edição e exclusão de **chamados**
- Agendamento de visitas técnicas
- Visualização de todos os chamados e agendamentos

### Perfil TÉCNICO
- Visualização dos chamados atribuídos
- Atualização de status: **Aberto → Em andamento**
- Finalização de chamados: **Em andamento → Finalizado**
- Envio e exclusão de anexos nos chamados
- Visualização e gerenciamento da própria agenda
- Marcar agendamento como **Realizado**

---

## Módulo de Agendamento

Gerencia as visitas técnicas vinculadas (ou não) a chamados.

### Funcionalidades
- Listagem de agendamentos com filtros: **Todos · Hoje · por Status**
- ADMIN visualiza todos; Técnico visualiza apenas os seus
- Criação de agendamento avulso ou direto pelo detalhe de um chamado
- Ao agendar uma visita de um chamado **Aberto**, o chamado é automaticamente movido para **Em andamento**
- Ao cancelar o único agendamento ativo de um chamado **Em andamento**, o chamado volta para **Aberto**

### Status de um agendamento

| Status | Descrição |
|---|---|
| `Agendado` | Criado, aguardando confirmação |
| `Confirmado` | Confirmado com o cliente |
| `Realizado` | Visita concluída (técnico responsável) |
| `Cancelado` | Cancelado (ADMIN); chamado vinculado retorna para Aberto se não houver outro agendamento ativo |

### Permissões

| Ação | ADMIN | TÉCNICO |
|---|---|---|
| Criar / editar agendamento | ✅ | ❌ |
| Visualizar agendamentos | Todos | Apenas os próprios |
| Marcar como Realizado | ✅ | Apenas o responsável |
| Cancelar | ✅ | ❌ |

---

## Como rodar o projeto

### Pré-requisitos
- Java 21+
- Maven 3.8+

### Passos

```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/ProjetoMorhar.git
cd ProjetoMorhar

# 2. Compile e execute
mvn spring-boot:run
```

A aplicação sobe na porta **8080**. O banco H2 é criado automaticamente em `./data/morhar` na primeira execução, e os dados iniciais são inseridos pelo `DataInitializer`.

Acesse: [http://localhost:8080](http://localhost:8080)

---

## Usuários padrão

| Usuário | Senha | Perfil |
|---|---|---|
| `admin` | `admin123` | Administrador |
| `carlos.silva` | `tecnico123` | Técnico |
| `joao.santos` | `tecnico123` | Técnico |
| `pedro.lima` | `tecnico123` | Técnico |

---

## Estrutura do projeto

```
src/main/
├── java/br/cesarschool/poo/projeto/
│   ├── config/
│   │   └── SecurityConfig.java          # Configuração do Spring Security
│   ├── controller/
│   │   ├── AgendaController.java
│   │   ├── AuthController.java          # Login, recuperação de senha
│   │   ├── ChamadoController.java
│   │   ├── ClienteController.java
│   │   ├── DashboardController.java
│   │   ├── PerfilController.java
│   │   └── TecnicoController.java
│   ├── model/
│   │   ├── Agendamento.java
│   │   ├── Anexo.java
│   │   ├── Chamado.java
│   │   ├── Cliente.java                 # Entidade abstrata (herança SINGLE_TABLE)
│   │   ├── ClientePessoaFisica.java
│   │   ├── ClientePessoaJuridica.java
│   │   ├── Tecnico.java
│   │   └── Usuario.java
│   ├── repository/                      # Interfaces JPA
│   ├── service/                         # Regras de negócio
│   └── DataInitializer.java             # Seed de dados iniciais
└── resources/
    ├── static/
    │   ├── css/morhar.css               # Estilo global da aplicação
    │   └── images/logo-morhar.png       # Logo da empresa
    ├── templates/                       # Templates Thymeleaf
    │   ├── agenda/
    │   ├── chamados/
    │   ├── clientes/
    │   ├── perfil/
    │   ├── tecnicos/
    │   ├── dashboard.html
    │   ├── login.html
    │   ├── recuperar-senha.html
    │   └── redefinir-senha.html
    └── application.properties
```

---

## Modelo de dados

```
Usuario ──────── Tecnico
   │
   └── perfil: ADMIN | TECNICO

Cliente (abstrato)
   ├── ClientePessoaFisica  (CPF)
   └── ClientePessoaJuridica (CNPJ)

Chamado
   ├── status: Aberto | Em andamento | Finalizado | Urgente
   ├── prioridade: Alta | Média | Baixa
   └── Anexo[]

Agendamento
   └── status: Agendado | Confirmado | Realizado | Cancelado
```

---

## Fluxo de status do chamado

```
[Aberto] ──(técnico)──► [Em andamento] ──(técnico)──► [Finalizado]
```

Somente o **técnico responsável** pelo chamado pode avançar o status. O ADMIN gerencia os dados, mas não altera o status operacional.

---


