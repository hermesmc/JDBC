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
		
		//Quantidade de conexões disponível	
		comboPooledDataSource.setMaxPoolSize(15);
		
		this.dataSource = comboPooledDataSource; 
	}
	
	public Connection recuperarConexao() throws SQLException{
		return this.dataSource.getConnection();
		
	}	
}