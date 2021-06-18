package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Categoria;

public class CategoriaDAO {

	private Connection connection; 
	
	public CategoriaDAO(Connection connection) {
		this.connection = connection;		
	}
	
	public void salvar(Categoria categoria) throws SQLException {
		String sql = "INSERT INTO categoria (nome) VALUES(?)";
		
		try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			pstm.setString(1, categoria.getNome());
			pstm.execute();
			
			try(ResultSet rst = pstm.getGeneratedKeys()){
				while(rst.next()) {
					categoria.setId(rst.getInt(1));
				}
			}
		}
	}
	
	public List<Categoria> listar() throws Exception {
		List<Categoria> Categorias = new ArrayList<Categoria>();
		
		String sql = "SELECT  id, nome FROM categoria";
		
		try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			pstm.execute();
			
			try(ResultSet rst = pstm.getResultSet()){
				while(rst.next()) {
					Categoria Categoria = new Categoria(rst.getInt(1), rst.getString(2));
					Categorias.add(Categoria);
				}
			}
		}
		return Categorias;
	}
	
}
