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
