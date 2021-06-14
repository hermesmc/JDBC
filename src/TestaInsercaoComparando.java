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
				
		PreparedStatement stm = connection.prepareStatement("INSERT INTO produto (id, nome, descricao) VALUES(0, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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