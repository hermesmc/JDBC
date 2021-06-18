import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.ProdutoDAO;
import factory.ConnectionFactory;
import modelo.Produto;

public class TestaInsercaoEListagemComProduto {

	public static void main(String[] args) throws Exception {
		
		Produto comoda = new Produto("Cômoda", "Cômoda com 7 gavetas");
		
		try (Connection connection = new ConnectionFactory().recuperarConexao()){
			ProdutoDAO produtoDao = new ProdutoDAO(connection);
			produtoDao.salvar(comoda);
			List<Produto> listaDeProdutos = produtoDao.listar();
			listaDeProdutos.stream().forEach(lp -> System.out.println(lp));
		}
	}
}
