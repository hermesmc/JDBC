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

- Utilizando PrepareStatement:

      import java.sql.Connection;
      import java.sql.PreparedStatement;
      import java.sql.ResultSet;
      import java.sql.SQLException;
      import java.sql.Statement;

      public class TestaInsercaoComparando {

	      public static void main(String[] args) throws SQLException {
		      String nome = "mouse'";
		      String descricao = "mouse sem fio"; delete from produto;";
		      ConnectionFactory connectionFactory = new ConnectionFactory();
		      Connection connection = connectionFactory.recuperarConexao(); 
				
		      PreparedStatement stm = connection.prepareStatement
		          ("INSERT INTO produto (id, nome, descricao) VALUES(0, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		      stm.setString(1, nome);
		      stm.setString(2, descricao);
		
		      stm.execute();
		      ResultSet resultado =  stm.getGeneratedKeys();
		      while(resultado.next()) {
		   	   Integer id = resultado.getInt(1);
		   	   System.out.println("Foi criado o ID:" + id);
		      }        
	      }
      }

- Controle de COMMIT/ROLLBACK

      import java.sql.Connection;
      import java.sql.PreparedStatement;
      import java.sql.ResultSet;
      import java.sql.SQLException;
      import java.sql.Statement;

      public class TestaInsercaoComparando {

      	public static void main(String[] args) throws SQLException {
      		String nome = "mouse'";
      		String descricao = "mouse sem fio";
      		ConnectionFactory connectionFactory = new ConnectionFactory();
      		Connection connection = connectionFactory.recuperarConexao(); 
      
      		//Retira o controle de commit do JDBC
      		connection.setAutoCommit(false);
      
      		try {
      			PreparedStatement stm = connection.prepareStatement("INSERT INTO produto (id, nome, descricao) VALUES(0, ?, ?)",
			      Statement.RETURN_GENERATED_KEYS);
			
      			//adicionarVariavel(nome, descricao, stm);	
      			adicionarVariavel("SmartTV",  "45 polegadas", stm);
      			adicionarVariavel("Radio", "AM/FM stereo", stm);
			
      			//Efetua o commit depois de todos os inserts 
      			connection.commit();		
      		} catch(Exception e){
      			e.printStackTrace();
      			System.out.println("Rollback executado.");
      			connection.rollback();
      		}	

      	}

      	private static void adicionarVariavel(String nome, String descricao, PreparedStatement stm) throws SQLException {
      		stm.setString(1, nome);
      		stm.setString(2, descricao);
      		
      		if(nome.equals("Radio")) {
      			throw new RuntimeException("Não foi possível incluir o produto: " + nome);
      		}
      		
      		stm.execute();
      		ResultSet resultado =  stm.getGeneratedKeys();
      		while(resultado.next()) {
      			Integer id = resultado.getInt(1);
      			System.out.println("Foi criado o ID:" + id);
      		}    
		
      		resultado.close();
      	}
      }
