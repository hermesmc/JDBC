import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import factory.ConnectionFactory;

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
