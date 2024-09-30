# Spring Data REST
* Spring Data REST simplificou bastante a criação de APIs RESTful ao construir sobre os repositórios do Spring Data. Antes do surgimento do Spring Data REST, a criação de endpoints REST envolvia a configuração manual de controladores, serviços e repositórios. Aqui está um resumo de como isso mudou:

## Antes do Spring Data REST
* Controladores REST: Você precisava criar controladores manualmente para cada entidade.

    ```java
    @RestController
    @RequestMapping("/api/entidades")
    public class EntidadeController {
        
        @Autowired
        private EntidadeService entidadeService;
        
        @GetMapping("/{id}")
        public ResponseEntity<Entidade> getEntidade(@PathVariable Long id) {
            Entidade entidade = entidadeService.findById(id);
            return ResponseEntity.ok(entidade);
        }
        
        // Outros métodos (POST, PUT, DELETE)
    }
    ```

* Serviços: Serviços para encapsular a lógica de negócios.

    ```java
    @Service
    public class EntidadeService {
        
        @Autowired
        private EntidadeRepository entidadeRepository;
        
        public Entidade findById(Long id) {
            return entidadeRepository.findById(id).orElse(null);
        }
        
        // Outros métodos
    }
    ```
* Repositórios: Repositórios para acesso ao banco de dados.

    ```java
    public interface EntidadeRepository extends JpaRepository<Entidade, Long> {
    }
    ```

## Depois do Spring Data REST
* Com o Spring Data REST, você pode expor automaticamente os repositórios como endpoints REST sem a necessidade de criar controladores e serviços manualmente.

* Repositórios REST: Apenas definindo o repositório, ele já é exposto como um endpoint REST.

    ```java
    @RepositoryRestResource
    public interface EntidadeRepository extends JpaRepository<Entidade, Long> {
    }
    ```
* O Spring Data REST possibilita configuração mínima: ele cuida da criação dos endpoints para operações CRUD (Create, Read, Update, Delete):
    * GET /entidades: Lista todas as entidades. (Read)
    * GET /entidades/{id}: Obtém uma entidade específica (Read)
    * POST /entidades: Cria uma nova entidade. (Create)
    * PUT /entidades/{id}: Atualiza uma entidade existente (Update).
    * DELETE /entidades/{id}: Remove uma entidade. (Delete)

* Vantagens do Spring Data REST
    * Redução de Código: Menos código boilerplate para escrever e manter.
    * Hypermedia: Suporte integrado para HATEOAS, facilitando a navegação entre recursos.
    * Customização: Possibilidade de personalizar os endpoints e adicionar lógica de negócios específica quando necessário.

## **HATEOAS (Hypermedia as the Engine of Application State)**

* **HATEOAS** (Hypermedia as the Engine of Application State) é um princípio fundamental da arquitetura REST. Ele sugere que a interação entre o cliente e o servidor deve ser guiada por hipermídia. Em outras palavras, as respostas do servidor não apenas contêm os dados solicitados, mas também links que indicam as ações possíveis a seguir.

* Quando um cliente faz uma solicitação a um servidor RESTful, a resposta não deve apenas fornecer os dados solicitados, mas também incluir links para outras operações relacionadas. Isso permite que o cliente navegue pela API de maneira dinâmica, sem precisar conhecer previamente a estrutura completa da API.

    * Por exemplo, ao solicitar informações sobre um usuário, a resposta pode incluir links para atualizar ou excluir esse usuário, ou para acessar informações relacionadas, como os pedidos feitos por esse usuário.

* **Analogia**: Pense em **HATEOAS** como um GPS para APIs.

    * Imagine que você está dirigindo em uma cidade desconhecida e precisa chegar a um destino específico. Sem um GPS, você precisaria conhecer todas as ruas e direções de antemão. Isso é como uma API **sem** HATEOAS, onde o cliente precisa conhecer todas as URLs e endpoints possíveis.

    * Com um GPS, você simplesmente insere seu destino e ele guia você passo a passo, fornecendo direções conforme você avança. Se você errar uma curva, o GPS recalcula a rota e oferece novas direções. Isso é como uma API **com** HATEOAS, onde cada resposta fornece links para as próximas ações possíveis, guiando o cliente através da navegação na API sem a necessidade de conhecimento prévio de todas as rotas possíveis.

## Features do Spring Data REST

* Spring Data REST oferece várias funcionalidades que facilitam a criação de APIs RESTful a partir de repositórios Spring Data JPA. As principais são:

    * **Exposição Automática de Repositórios**: Converte automaticamente repositórios Spring Data JPA em endpoints RESTful.
    * **Hypermedia (HATEOAS)**: Suporte integrado para HATEOAS, permitindo que as respostas incluam links para navegação.
    * **Paginação e Ordenação**: Suporte para paginação e ordenação de resultados através de parâmetros de URL.
    * **Projeções e Trechos (Projections and Excerpts)**: Permite definir visualizações personalizadas dos dados.
    * **Manipulação de Eventos**: Gatilhos para eventos de criação, atualização e exclusão de entidades.
    * **Customização de Endpoints**: Possibilidade de personalizar os endpoints e adicionar lógica de negócios específica.
    * **Suporte a CORS**: Configuração fácil para Cross-Origin Resource Sharing (CORS).

* **Configurações no ```application.properties```**:

    * É possível configurar várias opções do Spring Data REST no arquivo application.properties:
    
        * Configuração de Base Path: 
            * ```spring.data.rest.base-path=/api```

        * Configuração de Paginação:
            * ```spring.data.rest.default-page-size=20```
            * ```spring.data.rest.max-page-size=100```

        * Configuração de CORS:
            * ```spring.data.rest.cors.allowed-origins=http://example.com```
            * ```spring.data.rest.cors.allowed-methods=GET,POST,PUT,DELETE```

        * Configuração de HATEOAS:
            * ```spring.hateoas.use-hal-as-default-json-media-type=true```

        * Configuração de Projeções:
            * ```spring.data.rest.projections.enabled=true```

    * Essas configurações permitem ajustar o comportamento do Spring Data REST conforme suas necessidades específicas

 * *Configurações do <u>Spring DATA REST</u> no código usando **Anotações***:

    * ```@RepositoryRestResource```: Utilizada para personalizar a exposição de um repositório como um recurso REST.

        ```java
        @RepositoryRestResource(path = "usuarios", collectionResourceRel = "usuarios")
        public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        }
        ```
    * ```@RestResource```: Permite personalizar os métodos de repositório individuais.

        ```java
        public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
            
            @RestResource(path = "por-nome", rel = "por-nome")
            List<Usuario> findByNome(@Param("nome") String nome);
        }
        ```
    * ```@RepositoryEventHandler```: Utilizada para definir manipuladores de eventos para entidades específicas, esta anotação é aplicada sobre uma classe e dentro dos métodos dessa classe podem ser aplicadas outras anotações, tais como: ```@HandleBeforeCreate```, ```@HandleAfterCreate```, ```@HandleBeforeSave```, ```@HandleAfterSave```, ```@HandleBeforeDelete```, ```@HandleAfterDelete```. Essas anotações são usadas para manipulação de eventos específicos.

        ```java
        @RepositoryEventHandler(Usuario.class)
        public class UsuarioEventHandler {
            
            @HandleBeforeCreate
            public void handleUsuarioCreate(Usuario usuario) {
                // Lógica antes de criar um usuário
            }
            
            @HandleAfterCreate
            public void handleAfterCreate(Usuario usuario) {
                // Lógica após criar um usuário
            }
        }
        ```

    * ```@Projection```: Utilizada para definir projeções de dados, permitindo exibir apenas partes específicas de uma entidade.

        ```java
        @Projection(name = "nomeApenas", types = { Usuario.class })
        public interface NomeApenas {
            String getNome();
        }
        ```