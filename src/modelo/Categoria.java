package modelo;

public class Categoria {

	private Integer id;
	private String nome;
	
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

}
