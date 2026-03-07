package model;

public class Categoria {
	    
	    private Integer id;
	    private String nome;
	    
	    // Construtor vazio (usado pelo JDBC)
	    public Categoria() {
	    }
	    
	    // Construtor com nome (para criar nova categoria)
	    public Categoria(String nome) {
	        setNome(nome);
	    }
	    
	    // Construtor completo (quando buscar do banco)
	    public Categoria(Integer id, String nome) {
	        this.id = id;
	        setNome(nome);
	    }
	    
	    public Integer getId() {
	        return id;
	    }
	    
	    public void setId(Integer id) {
	        this.id = id;
	    }
	    
	    public String getNome() {
	        return nome;
	    }
	    
	    public void setNome(String nome) {
	        if(nome == null || nome.isEmpty()){
	            throw new IllegalArgumentException("Nome não pode ser vazio");
	        }
	        this.nome = nome;
	    }
	    
	    @Override
	    public String toString() {
	        return "Categoria [id=" + id + ", nome=" + nome + "]";
	    }
	
}
