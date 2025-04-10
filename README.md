   ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)  ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)  ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

# Projeto-Login

# Descrição
 Mini projeto de autenticação com spirng boot e jwt 
# Tecnologias utilizadas
  <h3>Spring Starter</h3>
 <ul>
  <li>web</li>
  <li>jpa</li>
  <li>security</li>
  <li>validation</li>
 </ul>
 <h3>Token</h3>
 <ul>
  <li>JJWT</li>
 </ul>
 <h3>Banco de dados</h3>
 <ul>
  <li>Mysql Driver</li>
 </ul>

 # Script BD
 <pre>
 Create table usuario(
  id bigInt primary key auto_increment,
  nome varchar(50) not null,
  email varchar(100) not null unique,
  senha varchar(300) not null,
  perfil_usuario enum('USUARIO','ADMINISTRADOR'),
  foto longBlob
 );
</pre>

# JJWT
<h3>Adicionando a dependência do JJWT no pom.xml</h3>
<pre>
  <code>
    &lt;dependency&gt;
      &lt;groupId&gt;io.jsonwebtoken&lt;/groupId&gt;
      &lt;artifactId&gt;jjwt-api&lt;/artifactId&gt;
      &lt;version&gt;0.12.6&lt;/version&gt;
    &lt;/dependency&gt;
    <br>
    &lt;dependency&gt;
      &lt;groupId&gt;io.jsonwebtoken&lt;/groupId&gt;
      &lt;artifactId&gt;jjwt-impl&lt;/artifactId&gt;
      &lt;version&gt;0.12.6&lt;/version&gt;
      &lt;scope&gt;runtime&lt;/scope&gt;
    &lt;/dependency&gt; 
    <br>
    &lt;dependency&gt;
      &lt;groupId&gt;io.jsonwebtoken&lt;/groupId&gt;
      &lt;artifactId&gt;jjwt-jackson&lt;/artifactId&gt; <!-- or jjwt-gson if Gson is preferred -->
      &lt;version&gt;0.12.6&lt;/version&gt;
      &lt;scope&gt;runtime&lt;/scope&gt;
    &lt;/dependency&gt;
  </code>
</pre>

<h3>Classe responsável por gerar chave de assinatura de tokens</h3>
<pre>
 
      @Component
      public class SecretKeyGenerator {

      private SecretKey key;
   
      public SecretKey getSecretKey() {
          if (this.key == null) {
              this.key = Jwts.SIG.HS256.key().build();
          }
          return this.key;
      }
     } 
</pre>



<h3>Classe resonsável por gerar token JWT </h3>
 <pre>
  
    @RequiredArgsConstructor
    @Service
    public class JwtService {
 
      private final SecretKeyGenerator keyGenerator;
  
      public Token gerarToken(Usuario usuario) {
          var token = Jwts.builder()
                  .signWith(keyGenerator.getSecretKey())
                  .subject(usuario.getEmail())
                  .expiration(getExpiration())
                  .claims(gerarClaims(usuario.getPerfil().toString()))
                  .compact();
          return new Token(token);
      }

  
      public Date getExpiration() {
          var expiration = LocalDateTime.now().atZone(ZoneId.systemDefault()).plusMinutes(60).toInstant();
          return Date.from(expiration);
      }

  
      private Map<String, Object> gerarClaims(String perfil) {
          Map<String, Object> claims = new HashMap<>();
          claims.put("Perfil", perfil);
          return claims;
      }

      public String getEmailByToken(String token) {
          try {
              return Jwts.parser()
                      .verifyWith(keyGenerator.getSecretKey())
                      .build()
                      .parseSignedClaims(token)
                      .getPayload()
                      .getSubject();
  
          } catch (Exception e) {
              throw new RuntimeException(e);
          }
      }
     } 
 </pre>

 # Uso

 <h3>Cadastro de usuário</h3>
   <h4>http://localhost:8080/usuario</h4>
   <pre>
     {
       "nome": "Paulo Oliveira",
       "email": "paulo@oliveira.com",
       "senha": "123",
       "perfil": "USUARIO"
     }
   <br>
   Respostas:
   201 Created
   409 Conflict: Este email já está sendo usado
   </pre>
   

   <h3>Fazer login</h3>
   <h4>http://localhost:8080/usuario/login</h4>
   <pre>
      {
        "email": "paulo@oliveira.com",
        "senha": "123"
      }
   <br>
   Respostas:
   200 Ok: token
   404 Not Found: Email e/ou senha incorretos
   </pre>




   
