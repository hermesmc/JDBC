import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaExclusao {

	public static void main(String[] args) throws SQLException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = connectionFactory.recuperarConexao(); 
		
		Statement stm = connection.createStatement();
		stm.execute("DELETE FROM produto WHERE (id = 6)");
        
		int contador = stm.getUpdateCount();
		System.out.println("Linhas modificadas: " + contador);
	}
}