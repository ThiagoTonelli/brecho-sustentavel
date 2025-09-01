# Brechó Sustentável ♻️

![Java](https://img.shields.io/badge/Java-11+-orange.svg) ![Maven](https://img.shields.io/badge/Maven-3.8-blue.svg) ![SQLite](https://img.shields.io/badge/SQLite-3-blue.svg) ![H2](https://img.shields.io/badge/H2-Database-red.svg)

Uma aplicação de desktop desenvolvida em Java com Swing, focada na criação de um marketplace para roupas de segunda mão, que promove a sustentabilidade e a economia circular.

## 📖 Sobre o Projeto

O **Brechó Sustentável** é mais do que uma plataforma de compra e venda. É uma ferramenta para conscientizar sobre o impacto ambiental da indústria da moda. O grande diferencial do projeto é a sua capacidade de calcular e exibir métricas de sustentabilidade para cada peça de roupa, incentivando um consumo mais responsável.

A aplicação calcula dois índices importantes:
* **GWP (Global Warming Potential - Potencial de Aquecimento Global):** Estima a quantidade de emissões de gases de efeito estufa que é evitada ao comprar uma peça usada em vez de uma nova.
* **MCI (Material Circularity Indicator - Índice de Circularidade de Materiais):** Mede quão circular é o ciclo de vida do material da peça, incentivando o reúso.

O sistema foi construído seguindo os princípios **SOLID** e utilizando diversos **Design Patterns** para garantir um código limpo, manutenível e escalável.

## ✨ Funcionalidades Principais

* **Autenticação e Perfis:** Sistema completo de cadastro e login de usuários.
* **Múltiplos Perfis:** Os usuários podem atuar como **Comprador** ou **Vendedor**, com a possibilidade de um perfil de **Administrador** para gerenciamento.
* **Anúncios Detalhados:** Vendedores podem criar anúncios informando o tipo de peça, composição dos materiais e possíveis defeitos.
* **Cálculo de Sustentabilidade:** O sistema calcula automaticamente os índices GWP e MCI com base nos dados do anúncio.
* **Sistema de Ofertas:** Compradores podem fazer ofertas nos anúncios, e vendedores podem aceitar ou recusar.
* **Gamificação:**
    * **Sistema de Reputação:** Usuários ganham pontos por ações positivas (cadastro completo, vendas bem-sucedidas, etc.).
    * **Conquistas (Insígnias):** Os usuários desbloqueiam insígnias por atingir marcos, como "Primeira Venda" ou "Guardião da Qualidade".
* **Dashboard Administrativo:** O administrador tem acesso a gráficos e estatísticas sobre a plataforma, como reputação dos usuários, GWP total evitado e materiais mais transacionados.
* **Segurança:** As senhas dos usuários são armazenadas de forma segura utilizando hashing com BCrypt.

## 📐 Arquitetura e Design Patterns

A arquitetura do projeto foi pensada para ser modular e desacoplada, facilitando a manutenção e a adição de novas funcionalidades. A seguir, os principais Design Patterns utilizados e como eles contribuem para os princípios SOLID:

### **Padrões Comportamentais**

* **`Strategy`**:
    * **Onde:** Sistema de cálculo de pontuação (`PontuacaoService`).
    * **Por quê:** Cada regra de pontuação (por avaliação, por cadastro completo, etc.) é uma implementação da interface `IPontuacaoStrategy`. Isso permite adicionar novas formas de pontuar sem alterar o serviço principal, seguindo o **Princípio Aberto/Fechado (OCP)**.

* **`Command`**:
    * **Onde:** Praticamente todas as ações do usuário (salvar, excluir, carregar dados nas telas) são encapsuladas em classes de comando (ex: `SalvarTipoPecaCommand`, `CarregarAnunciosCommand`).
    * **Por quê:** Desacopla o "invocador" (um botão na UI) do "executor" (a lógica de negócio). Cada comando tem uma única responsabilidade, o que facilita a manutenção e adere ao **Princípio da Responsabilidade Única (SRP)**.

* **`State`**:
    * **Onde:** Gerenciamento da tela principal (`JanelaPrincipalPresenter`) e da tela de anúncios (`ManterAnuncioPresenter`).
    * **Por quê:** O comportamento da tela muda drasticamente dependendo do perfil do usuário (Comprador, Vendedor, Admin) ou do estado do anúncio (criação, edição, visualização). O State Pattern organiza essa complexidade, permitindo que cada estado gerencie seu próprio comportamento, o que simplifica o código e segue o **OCP**.

* **`Chain of Responsibility`**:
    * **Onde:** Sistema de concessão de insígnias (`AplicaInsigniaService`).
    * **Por quê:** Cada tipo de insígnia é um "handler" (`ITipoInsigniaHandler`) em uma corrente. Quando um evento ocorre (ex: uma venda é concluída), o sistema passa o usuário pela corrente e cada handler verifica se a condição para ganhar sua respectiva insígnia foi atendida. Isso torna o sistema de insígnias altamente extensível.

* **`Observer`**:
    * **Onde:** Utilizado para notificar diferentes partes do sistema quando eventos importantes acontecem, como a conclusão de uma transação.
    * **Por quê:** Promove o baixo acoplamento. Módulos podem "ouvir" eventos sem precisarem se conhecer diretamente.

### **Padrões Criacionais**

* **`Factory`**:
    * **Onde:** Na camada de persistência de dados (`repositoryFactory`).
    * **Por quê:** O sistema pode alternar entre bancos de dados **SQLite** e **H2** apenas com uma mudança de configuração. A `RepositoryFactory` abstrata define a interface para criar repositórios, e temos fábricas concretas (`SQLiteRepositoryFactory`, `H2RepositoryFactory`) que implementam essa interface. Isso nos permite "injetar" a fábrica desejada, respeitando o **Princípio da Inversão de Dependência (DIP)**.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** [Java 17](https://www.oracle.com/java/)
* **Interface Gráfica:** [Java Swing](https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html)
* **Look and Feel:** [FlatLaf](https://www.formdev.com/flatlaf/) (para um visual moderno e clean)
* **Gerenciador de Dependências:** [Maven](https://maven.apache.org/)
* **Banco de Dados:**
    * [SQLite](https://www.sqlite.org/index.html) 
    * [H2 Database](https://www.h2database.com/html/main.html) 
* **Hashing de Senhas:** [jBCrypt](https://github.com/jeremyh/jBCrypt)
* **Logging:** [SLF4J](http://www.slf4j.org/) & [Logback](http://logback.qos.ch/)
* **Gráficos:** [JFreeChart](https://www.jfree.org/jfreechart/)

## 🚀 Como Executar o Projeto

### **Pré-requisitos**

* JDK 17 ou superior instalado.
* Apache Maven instalado.
* Git.

### **Passo a Passo**

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/brecho-sustentavel.git](https://github.com/seu-usuario/brecho-sustentavel.git)
    cd brecho-sustentavel
    ```

2.  **Compile e instale as dependências com o Maven:**
    ```bash
    mvn clean install
    ```

3.  **Execute a aplicação:**
    ```bash
    mvn exec:java -Dexec.mainClass="br.brechosustentavel.BrechoSustentavel"
    ```

### **Configuração do Banco de Dados**

Você pode escolher qual banco de dados utilizar (SQLite ou H2) editando o arquivo:
`src/main/java/br/brechosustentavel/configuracao/configuracaoSGBD.env`

Altere a variável `SGBD_TYPE` para `SQLITE` ou `H2`.
