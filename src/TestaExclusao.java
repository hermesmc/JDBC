import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaExclusao {

	public static void main(String[] args) throws SQLException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = connectionFactory.recuperarConexao(); 
		
		int numId = 3;
		PreparedStatement stm = connection.prepareStatement("DELETE FROM produto WHERE (id > ?)");
		stm.setInt(1, numId);
		stm.execute();
        
		int contador = stm.getUpdateCount();
		System.out.println("Linhas modificadas: " + contador);
	}
}