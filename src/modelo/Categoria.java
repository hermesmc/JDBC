package modelo;

import java.util.ArrayList;
import java.util.List;

public class Categoria {

	private Integer id;
	private String nome;
	private List<Produto> produtos = new ArrayList<Produto>();
	
	public Categoria(String nome) {
		super();
		this.nome = nome;
	}

	public Categoria(Integer id, String nome) {
		this.id = id;
		this.nome = nome;		
	}
	
	public String getNome() {
		return nome;
	}

	public int getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;		
	}
	
	@Override
	public String toString() {
		return String.format("A categoria é: %d, %s", 
				this.id, this.nome);		
	}

	public void adicionar(Produto produto) {
		produtos.add(produto);
		
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

}
