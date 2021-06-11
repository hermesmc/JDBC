# JDBC

Acompanhamento do estudo sobre JDBC no Alura

JDBC significa Java DataBase Conectivity

    JDBC define uma camada de abstração entre a sua aplicação e o driver do banco de dados
    Essa camada possui, na sua grande maioria, interfaces que o driver implementa


- Exemplo de conexão simples:

      import java.sql.Connection;
      import java.sql.DriverManager;
      import java.sql.SQLException;

      public class TestaConexao {

	      public static void main(String[] args) throws SQLException {
		      Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/loja_virtual?useTimezone=true&serverTimezone=UTC", "root", "sua senha");

		      System.out.println("Fechando conexão!!!"); 
		      con.close();	
          }
      }

- Criando classe de conexão simples:

      import java.sql.Connection;
      import java.sql.DriverManager;
      import java.sql.SQLException;

      public class CriaConexao {

	      public Connection recuperarConexao() throws SQLException {
		      String urlConnection = 	"jdbc:mysql://127.0.0.1/loja_virtual?useTimezone=true&serverTimezone=UTC";
		      String userConnection = "root";
		      String passwordConnection = "sua senha";
		      return DriverManager.getConnection(urlConnection, userConnection, passwordConnection); 
	      }
      }

- Efetuando inclusões simples:

      import java.sql.Connection;
      import java.sql.ResultSet;
      import java.sql.SQLException;
      import java.sql.Statement;

      public class TestaInsercao {
          public static void main(String[] args) throws SQLException {
	          ConnectionFactory connectionFactory = new ConnectionFactory();
	          Connection connection = connectionFactory.recuperarConexao(); 
		
	          Statement stm = connection.createStatement();
	          stm.execute("INSERT INTO produto (id, nome, descricao) VALUES(0, 'MOUSE', 'MOUSE RAZER')", Statement.RETURN_GENERATED_KEYS);
	          ResultSet resultado =  stm.getGeneratedKeys();
	          while(resultado.next()) {
	                Integer id = resultado.getInt(1);
	                System.out.println("Foi criado o ID:" + id);
	          }        
	      } 
      }	
