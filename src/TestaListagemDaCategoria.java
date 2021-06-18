import java.sql.Connection;
import java.util.List;

import dao.CategoriaDAO;
import factory.ConnectionFactory;
import modelo.Categoria;

public class TestaListagemDaCategoria {

	public static void main(String[] args) throws Exception {
		
		try(Connection connection = new ConnectionFactory().recuperarConexao()){
			CategoriaDAO categoriaDAO = new CategoriaDAO(connection);
			List<Categoria> listaDeCategorias = categoriaDAO.listar();	
			listaDeCategorias.stream().forEach(ct -> System.out.println(ct.getNome()));
		}		
	}
}