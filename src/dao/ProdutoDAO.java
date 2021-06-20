package dao;
// DAO - Data Access Object

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Categoria;
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
	
	public List<Produto> listar() throws Exception {
		List<Produto> produtos = new ArrayList<Produto>();
		
		String sql = "SELECT  id, nome, descricao FROM produto ";
		
		try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			pstm.execute();
			
			try(ResultSet rst = pstm.getResultSet()){
				while(rst.next()) {
					Produto produto = new Produto(rst.getInt(1), rst.getString(2), rst.getString(3));
					produtos.add(produto);
				}
			}
		}
		return produtos;
	}

	public List<Produto> buscar(Categoria ct) throws Exception {
		List<Produto> produtos = new ArrayList<Produto>();
		
		String sql = "SELECT  id, nome, descricao FROM produto WHERE categoria_id = ?";
		
		try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			pstm.setInt(1, ct.getId());
			pstm.execute();
			
			try(ResultSet rst = pstm.getResultSet()){
				while(rst.next()) {
					Produto produto = new Produto(rst.getInt(1), rst.getString(2), rst.getString(3));
					produtos.add(produto);
				}
			}
		}
		return produtos;
	}
	
}
