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
