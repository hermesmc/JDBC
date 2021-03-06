import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import factory.ConnectionFactory;

public class TestaInsercaoComparando {

	public static void main(String[] args) throws SQLException {
		String nome = "mouse'";
		String descricao = "mouse sem fio";
		ConnectionFactory connectionFactory = new ConnectionFactory();
		try (Connection connection = connectionFactory.recuperarConexao()){ 

			//Retira o controle de commit do JDBC
			connection.setAutoCommit(false);
			
			// try with resources
			try (PreparedStatement stm = connection.prepareStatement("INSERT INTO produto (id, nome, descricao) VALUES(0, ?, ?)", Statement.RETURN_GENERATED_KEYS);)
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
	}

	private static void adicionarVariavel(String nome, String descricao, PreparedStatement stm) throws SQLException {
		stm.setString(1, nome);
		stm.setString(2, descricao);
		
		stm.execute();
		
		// try with resources
		try (ResultSet resultado =  stm.getGeneratedKeys()){
			while(resultado.next()) {
				Integer id = resultado.getInt(1);
				System.out.println("Foi criado o ID:" + id);
			}
		}		
		
	}
}