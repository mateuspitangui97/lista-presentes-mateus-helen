# Lista de Presentes

Sistema web para gerenciamento de lista de presentes com personalizacao completa e simulacao de checkout via PIX.

## Visao Geral

Este projeto e uma aplicacao fullstack desenvolvida em Java que permite:

- **Administrador**: Cadastrar itens, personalizar a lista com fotos e informacoes do casal
- **Visitantes**: Visualizar a lista personalizada, escolher presentes e simular checkout via PIX

### Principais Caracteristicas

- Interface web responsiva e moderna
- **Personalizacao completa**: nomes, fotos do casal, cores, mensagens
- Painel administrativo protegido por autenticacao
- Simulacao de pagamento via PIX (sem integracao real)
- Banco de dados H2 embarcado (desenvolvimento) ou PostgreSQL (producao)
- Deploy gratuito em plataformas como Render ou Railway

---

## Personalizacao do Casal

O sistema permite total personalizacao da lista de presentes:

### Informacoes do Casal

| Campo | Descricao |
|-------|-----------|
| Nome Pessoa 1 | Primeiro nome do casal |
| Nome Pessoa 2 | Segundo nome do casal |
| Titulo da Pagina | Subtitulo exibido no cabecalho |
| Mensagem de Boas-Vindas | Texto exibido na pagina inicial |

### Fotos

| Campo | Descricao |
|-------|-----------|
| Foto do Casal | Foto circular exibida no cabecalho |
| Foto de Banner | Imagem de fundo do cabecalho |

### Evento

| Campo | Descricao |
|-------|-----------|
| Data do Evento | Data exibida no cabecalho |
| Local do Evento | Local exibido junto a data |

### Personalizacao Visual

| Campo | Descricao |
|-------|-----------|
| Cor Primaria | Cor principal (cabecalho, botoes) |
| Cor Secundaria | Cor de destaque (gradientes) |

### Mensagens

| Campo | Descricao |
|-------|-----------|
| Mensagem de Agradecimento | Exibida apos confirmacao de presente |

---

## Arquitetura

### Stack Tecnologica

| Componente | Tecnologia |
|------------|------------|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.4.2 |
| Build | Maven |
| Frontend | Thymeleaf (server-side rendering) |
| Banco de Dados | H2 (dev) / PostgreSQL (prod) |
| Autenticacao | Spring Security |
| Estilizacao | CSS puro com variaveis CSS |

### Padrao Arquitetural

A aplicacao segue o padrao **MVC em camadas**:

```
Controller -> Service -> Repository -> Entity
     |
     v
   DTO (transferencia de dados)
```

### Estrutura de Pacotes

```
br.com.listapresentes.api/
├── config/          # Configuracoes (Security, Web)
├── controller/      # Controllers MVC
├── service/         # Regras de negocio
├── repository/      # Acesso a dados (Spring Data JPA)
├── entity/          # Entidades JPA
└── dto/             # Objetos de transferencia
```

---

## Modelo de Dados

### Entidade: ConfiguracaoLista

Armazena as configuracoes de personalizacao do casal.

| Campo | Tipo | Descricao |
|-------|------|-----------|
| id | Long | Identificador unico |
| nomePessoa1 | String | Nome da primeira pessoa |
| nomePessoa2 | String | Nome da segunda pessoa |
| tituloPagina | String | Subtitulo da pagina |
| mensagemBoasVindas | String | Mensagem de boas-vindas |
| fotoCasalUrl | String | URL da foto do casal |
| fotoBannerUrl | String | URL da foto de banner |
| dataEvento | LocalDate | Data do evento |
| localEvento | String | Local do evento |
| corPrimaria | String | Cor primaria (hex) |
| corSecundaria | String | Cor secundaria (hex) |
| mensagemAgradecimento | String | Mensagem de agradecimento |

### Entidade: Item

Representa um presente na lista.

| Campo | Tipo | Descricao |
|-------|------|-----------|
| id | Long | Identificador unico |
| nome | String | Nome do produto |
| descricao | String | Descricao detalhada |
| valor | BigDecimal | Valor em reais |
| imagemUrl | String | URL da imagem do produto |
| status | Enum | DISPONIVEL ou RESERVADO |
| dataCriacao | LocalDateTime | Data de criacao |

### Entidade: Reserva

Representa a reserva de um item por um visitante.

| Campo | Tipo | Descricao |
|-------|------|-----------|
| id | Long | Identificador unico |
| item | Item | Item reservado (relacao 1:1) |
| nomeConvidado | String | Nome de quem reservou |
| emailConvidado | String | E-mail (opcional) |
| mensagem | String | Mensagem para o casal |
| dataReserva | LocalDateTime | Data da reserva |

### Diagrama de Relacionamento

```
┌───────────────────┐
│ CONFIGURACAO_LISTA│
├───────────────────┤
│ id                │
│ nomePessoa1       │
│ nomePessoa2       │
│ tituloPagina      │
│ mensagemBoasVindas│
│ fotoCasalUrl      │
│ fotoBannerUrl     │
│ dataEvento        │
│ localEvento       │
│ corPrimaria       │
│ corSecundaria     │
│ mensagemAgradec.  │
└───────────────────┘

┌─────────────┐         ┌─────────────┐
│    ITEM     │ 1 --- 1 │   RESERVA   │
├─────────────┤         ├─────────────┤
│ id          │         │ id          │
│ nome        │         │ item_id(FK) │
│ descricao   │         │ nomeConvid. │
│ valor       │         │ email       │
│ imagemUrl   │         │ mensagem    │
│ status      │         │ dataReserva │
│ dataCriacao │         └─────────────┘
└─────────────┘
```

---

## Fluxos do Sistema

### Fluxo do Visitante

```
1. Acessa a lista publica (/lista)
   - Ve foto do casal e nomes personalizados
   - Le mensagem de boas-vindas
         │
         ▼
2. Visualiza itens disponiveis
         │
         ▼
3. Clica em "Ver Detalhes" de um item (/item/{id})
         │
         ▼
4. Clica em "Quero Presentear" (/checkout/{id})
         │
         ▼
5. Preenche nome e mensagem para o casal
         │
         ▼
6. Confirma reserva (POST /reservar)
         │
         ▼
7. Ve pagina de confirmacao personalizada
   - Mensagem de agradecimento do casal
   - Dados do PIX
         │
         ▼
8. Realiza pagamento manualmente via PIX
```

### Fluxo do Administrador

```
1. Acessa /admin
         │
         ▼
2. Faz login (usuario e senha configurados)
         │
         ▼
3. Dashboard: Lista de todos os itens
         │
         ├──> Novo Item: /admin/item/novo
         │
         ├──> Editar Item: /admin/item/editar/{id}
         │
         ├──> Excluir Item: /admin/item/excluir/{id}
         │
         ├──> Ver Reservas: /admin/reservas
         │         │
         │         └──> Cancelar Reserva
         │
         └──> Configuracoes: /admin/configuracoes
                    │
                    ├──> Nomes do casal
                    ├──> Fotos (casal e banner)
                    ├──> Cores personalizadas
                    ├──> Data e local do evento
                    └──> Mensagens personalizadas
```

---

## Camadas da Aplicacao

### Controller

| Controller | Tipo | Responsabilidade |
|------------|------|------------------|
| `PublicController` | MVC | Paginas publicas com dados personalizados |
| `AdminController` | MVC | CRUD de itens, reservas e configuracoes |
| `LoginController` | MVC | Pagina de login |

### Service

| Service | Responsabilidade |
|---------|------------------|
| `ItemService` | CRUD de itens, alteracao de status |
| `ReservaService` | Criar e cancelar reservas |
| `ConfiguracaoService` | Gerenciar personalizacao do casal |

### Repository

| Repository | Metodos Customizados |
|------------|---------------------|
| `ItemRepository` | `findByStatusOrderByNomeAsc()` |
| `ReservaRepository` | `findByItemId()` |
| `ConfiguracaoRepository` | `findFirstByAtivoTrue()` |

### DTO

| DTO | Uso |
|-----|-----|
| `ItemDTO` | Exibicao de item |
| `ItemFormDTO` | Formulario de cadastro |
| `ReservaDTO` | Exibicao de reserva |
| `ReservaFormDTO` | Formulario de reserva |
| `ConfiguracaoDTO` | Formulario de personalizacao |

---

## Paginas (Templates Thymeleaf)

### Publicas

| Pagina | Rota | Descricao |
|--------|------|-----------|
| lista.html | /lista | Lista com foto do casal e mensagem |
| item-detalhe.html | /item/{id} | Detalhes do presente |
| checkout.html | /checkout/{id} | Formulario de reserva + PIX |
| confirmacao.html | /confirmacao/{id} | Agradecimento personalizado |

### Administrativas

| Pagina | Rota | Descricao |
|--------|------|-----------|
| dashboard.html | /admin | Tabela com todos os itens |
| item-form.html | /admin/item/novo | Formulario de item |
| reservas.html | /admin/reservas | Lista de reservas |
| configuracoes.html | /admin/configuracoes | Personalizacao do casal |

---

## Configuracao

### Arquivo: application.yml

```yaml
spring:
  application:
    name: lista-presentes

  datasource:
    url: jdbc:h2:file:./data/listapresentes

  h2:
    console:
      enabled: true
      path: /h2-console

app:
  admin:
    username: admin
    password: admin123
  pix:
    chave: "sua-chave-pix"
    nome: "Nome do Titular"
```

### Variaveis de Ambiente (Producao)

| Variavel | Descricao |
|----------|-----------|
| `SPRING_PROFILES_ACTIVE` | Definir como `prod` |
| `DATABASE_URL` | URL do PostgreSQL |
| `ADMIN_USERNAME` | Usuario admin |
| `ADMIN_PASSWORD` | Senha admin |
| `PIX_CHAVE` | Chave PIX |
| `PIX_NOME` | Nome do titular |

---

## Como Executar

### Pre-requisitos

- Java 21+
- Maven 3.8+

### Desenvolvimento

```bash
# Clonar o repositorio
git clone <url-do-repositorio>
cd lista-presentes

# Executar
./mvnw spring-boot:run

# Acessar
# Lista publica: http://localhost:8080/lista
# Admin: http://localhost:8080/admin
# Console H2: http://localhost:8080/h2-console
```

### Primeiros Passos

1. Acesse `/admin` (usuario: admin, senha: admin123)
2. Va em **Configuracoes** e personalize:
   - Nomes do casal
   - Foto do casal (URL)
   - Cores do site
   - Mensagens
3. Adicione itens na lista
4. Compartilhe o link `/lista` com os convidados

### Build para Producao

```bash
./mvnw clean package -DskipTests
java -jar target/lista-presentes-1.0.0.jar --spring.profiles.active=prod
```

### Docker

```bash
docker build -t lista-presentes .
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DATABASE_URL=jdbc:postgresql://host:5432/db \
  -e ADMIN_USERNAME=admin \
  -e ADMIN_PASSWORD=senhasegura \
  -e PIX_CHAVE=suachavepix \
  -e PIX_NOME=SeuNome \
  lista-presentes
```

---

## Deploy Gratuito

### Opcao 1: Render (Recomendado)

1. Crie uma conta em [render.com](https://render.com)
2. Crie um banco PostgreSQL gratuito
3. Crie um Web Service conectando ao repositorio Git
4. Configure as variaveis de ambiente
5. Deploy automatico a cada push

### Opcao 2: Railway

1. Crie uma conta em [railway.app](https://railway.app)
2. Adicione um projeto com PostgreSQL
3. Conecte o repositorio Git
4. Configure variaveis de ambiente

---

## Estrutura de Arquivos

```
lista-presentes/
├── pom.xml
├── Dockerfile
├── README.md
├── src/
│   ├── main/
│   │   ├── java/br/com/listapresentes/api/
│   │   │   ├── ListaPresentesApplication.java
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── WebConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── LoginController.java
│   │   │   │   └── PublicController.java
│   │   │   ├── dto/
│   │   │   │   ├── ConfiguracaoDTO.java
│   │   │   │   ├── ItemDTO.java
│   │   │   │   ├── ItemFormDTO.java
│   │   │   │   ├── ReservaDTO.java
│   │   │   │   └── ReservaFormDTO.java
│   │   │   ├── entity/
│   │   │   │   ├── ConfiguracaoLista.java
│   │   │   │   ├── Item.java
│   │   │   │   ├── Reserva.java
│   │   │   │   └── StatusItem.java
│   │   │   ├── repository/
│   │   │   │   ├── ConfiguracaoRepository.java
│   │   │   │   ├── ItemRepository.java
│   │   │   │   └── ReservaRepository.java
│   │   │   └── service/
│   │   │       ├── ConfiguracaoService.java
│   │   │       ├── ItemService.java
│   │   │       └── ReservaService.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-prod.yml
│   │       ├── static/css/style.css
│   │       └── templates/
│   │           ├── login.html
│   │           ├── admin/
│   │           │   ├── dashboard.html
│   │           │   ├── item-form.html
│   │           │   ├── reservas.html
│   │           │   └── configuracoes.html
│   │           ├── public/
│   │           │   ├── lista.html
│   │           │   ├── item-detalhe.html
│   │           │   ├── checkout.html
│   │           │   └── confirmacao.html
│   │           └── fragments/
│   │               ├── header.html
│   │               └── footer.html
│   └── test/
└── data/                    # Banco H2 (ignorado no git)
```

---

## Seguranca

- Rotas `/admin/**` protegidas por autenticacao
- Senhas codificadas com BCrypt
- CSRF habilitado (exceto H2 Console)
- Sem armazenamento de dados sensiveis de pagamento

---

## Licenca

Este projeto e de uso pessoal e livre para modificacoes.
