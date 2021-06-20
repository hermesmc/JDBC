package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Categoria;
import modelo.Produto;

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

	public List<Categoria> listarComProdutos() throws SQLException{
		
		Categoria ultima = null;
		
		List<Categoria> Categorias = new ArrayList<Categoria>();
		
		String sql = "SELECT  C.id, C.nome, P.id, P.nome, P.descricao FROM categoria C"
				   + " INNER JOIN produto P ON C.id = P.categoria_id";
		
		try(PreparedStatement pstm = connection.prepareStatement(sql)){
			
			pstm.execute();
			
			try(ResultSet rst = pstm.getResultSet()){
				while(rst.next()) {
					if (ultima == null || !ultima.getNome().equals(rst.getString(2))) {
						Categoria categoria = new Categoria(rst.getInt(1), rst.getString(2));
						ultima = categoria;
						Categorias.add(categoria);						
					}
					Produto produto = new Produto(rst.getInt(3), rst.getString(4), rst.getString(5));
					ultima.adicionar(produto);
				}
			}
		}
		return Categorias;
	}
	
}
