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
			import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {

	public DataSource dataSource;
	
	public ConnectionFactory() {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/loja_virtual?useTimezone=true&serverTimezone=UTC");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("Peruas@1");
		
		
		this.dataSource = comboPooledDataSource; 
	}
	
	public Connection recuperarConexao() throws SQLException{
		return this.dataSource.getConnection();
		
	}	
}
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
      
- Utilizando try with resources. Uma das vantagens é não se precoupar em abrir e fechar a conexão com banco de dados:

      		try (Connection connection = connectionFactory.recuperarConexao()){ 

      			//Retira o controle de commit do JDBC
      			connection.setAutoCommit(false);
			
      			// try with resources
      			try (PreparedStatement stm = connection.prepareStatement("INSERT INTO produto (id, nome, descricao) VALUES(0, ?, ?)",
      			Statement.RETURN_GENERATED_KEYS);)
      			   {//adicionarVariavel(nome, descricao, stm);	
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

- Utilizando Pool de conexões:

      import java.sql.Connection;
      import java.sql.SQLException;
      import javax.sql.DataSource;

      import com.mchange.v2.c3p0.ComboPooledDataSource;

      public class ConnectionFactory {

	      public DataSource dataSource;
	
	      public ConnectionFactory() {
		      ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		      comboPooledDataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/loja_virtual?useTimezone=true&serverTimezone=UTC");
		      comboPooledDataSource.setUser("root");
		      comboPooledDataSource.setPassword("sua senha");
		
		
		      this.dataSource = comboPooledDataSource; 
	      }
	
	      public Connection recuperarConexao() throws SQLException{
		      return this.dataSource.getConnection();
		
	      }	
      }

- Conceito DAO
   - Crei um package chamado DAO e a classe abaixo dentro dela:

         package dao;
         // DAO - Data Access Object

         import java.sql.Connection;
         import java.sql.PreparedStatement;
         import java.sql.ResultSet;
         import java.sql.SQLException;
         import java.sql.Statement;

         import modelo.Produto;

         public class ProdutoDAO {
	
	         private Connection connection; 
	
	         public ProdutoDAO(Connection connection) {
		         this.connection = connection;		
	         }
	
	         public void salvar(Produto produto) throws SQLException {
		         String sql = "INSERT INTO produto (nome, descricao) VALUES(?, ?)";
		
		         try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			         pstm.setString(1, produto.getNome());
			         pstm.setString(2, produto.getDescricao());
			
			         pstm.execute();
			
			         try(ResultSet rst = pstm.getGeneratedKeys()){
				         while(rst.next()) {
					         produto.setId(rst.getInt(1));
				         }
			         }
		         }
	         }	
         }
	 
   - Na classe onde se quer inserir, use assim:

         import java.sql.Connection;
         import java.sql.SQLException;
         import dao.ProdutoDAO;
         import modelo.Produto;

         public class TestaInsercaoComProduto {

	         public static void main(String[] args) throws SQLException {
		
		         Produto comoda = new Produto("Cômoda", "Cômoda com 3 gavetas");
		
		         try (Connection connection = new ConnectionFactory().recuperarConexao()){
			         ProdutoDAO produtoDao = new ProdutoDAO(connection);
			         produtoDao.salvar(comoda);
		         }
		         System.out.println(comoda);
	         }
         }	
		 
