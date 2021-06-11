import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaListagem {

	public static void main(String[] args) throws SQLException {
		CriaConexao criaConexao = new CriaConexao();
		Connection connection = criaConexao.recuperarConexao();
		
		Statement stm = connection.createStatement();

//		boolean resultado = stm.execute("SELECT id, nome, descricao FROM produto");
//		System.out.println(resultado);
		
		stm.execute("SELECT id, nome, descricao FROM produto");
		
		ResultSet rst = stm.getResultSet();
		
		while(rst.next()) {
			int id = rst.getInt("id");
			String nome = rst.getString("nome");
			String descricao = rst.getString("descricao");
			System.out.println(id);
			System.out.println(nome);
			System.out.println(descricao); 
		}
		
		connection.close();	
	}
	
}
