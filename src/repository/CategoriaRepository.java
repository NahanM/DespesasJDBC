package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import util.ConexaoDB;

public class CategoriaRepository {
    
    public List<Categoria> listarTodas(){
    	List<Categoria> categorias = new ArrayList<>();
    	
    	String sql = "SELECT id, nome FROM categoria ORDER BY nome";
    	
    	Connection conexao = null;
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	
    	try {
    		conexao = ConexaoDB.getConexao();
    		stmt = conexao.prepareStatement(sql);
    		rs = stmt.executeQuery();
    		
    		while (rs.next()) {
    			Integer id = rs.getInt("id");
    			String nome = rs.getString("nome");
    			
    			Categoria categoria = new Categoria(id, nome);
    			categorias.add(categoria);
    		}
    		
    		System.out.println(categorias.size() + " categorias encontradas");
    		
    	} catch (SQLException r) {
    		System.err.println("Erro ao listar");
    		System.err.println("Datalhes: "+r.getMessage());
    	} finally {
    		try {
    			if (rs!= null) rs.close();
    			if (stmt !=null) stmt.close();
    			if (conexao != null) conexao.close();
    		} catch (SQLException r) {
    			System.err.println("Erro :" + r.getMessage());
    		}
    	}
    	return categorias;
    }
    
    public Categoria buscarPorId(int id) {
    	String sql ="select id, nome from categoria where id = ?";
    	
    	Connection conexao = null;
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	Categoria categoria = null;
    	
    	try {
    		conexao = ConexaoDB.getConexao();
    		stmt = conexao.prepareStatement(sql);
    		
    		stmt.setInt(1, id);
    		
    		rs = stmt.executeQuery();
    		
    		if (rs.next()) {
    			Integer idCategoria = rs.getInt("id");
    			String nome = rs.getString("nome");
    			categoria = new Categoria(idCategoria, nome);
    			
    			System.out.println("Acho a categoria: "+ nome);
    		} else {
    			System.out.println("Não achou pelo id: "+id);
    		}
    		
    	} catch (SQLException r) {
    		System.err.println("Erro ao buscar pelo id");
    		System.err.println("Detalhes "+ r.getMessage());
    	} finally {
    		try {
    			if (rs != null) rs.close();
    			if (stmt != null) stmt.close();
    			if (conexao != null) conexao.close();
    		} catch (SQLException r) {
    			System.out.println("Erro ao fechar: "+r.getMessage());
    		}
    	}
    	
    	return categoria;
    }
    
    public boolean salvar(Categoria categoria) {
    	String sql = "INsert into categoria (nome) values (?)";
    	
    	Connection conexao = null;
    	PreparedStatement stmt = null;
    	
    	try {
    		conexao = ConexaoDB.getConexao();
    		stmt = conexao.prepareStatement(sql);
    		
    		stmt.setString(1, categoria.getNome());
    		
    		int linhasAfetadas = stmt.executeUpdate();
    		
    		if (linhasAfetadas > 0) {
    			System.out.println("Categoria nova: "+categoria.getNome());
    			return true;
    		}
    		
    	} catch (SQLException r) {
    		System.err.println("Erro ao salvar cateogira");
    		System.err.println("Detalhes: "+r.getMessage());
    	} finally {
    		try {
    			if (stmt !=null) stmt.close();
    			if(conexao != null) conexao.close();
    			
    		} catch (SQLException r) {
    			System.err.println("Erro ao fechar: " + r.getMessage());
    		}
    	}
    	return false;
    }
    
    public boolean atualizar(Categoria categoria) {
    	String sql = "update categoria set nome = ? where id = ?";
    	
    	Connection conexao = null;
    	PreparedStatement stmt = null;
    	
    	try {
    		conexao = ConexaoDB.getConexao();
    		stmt = conexao.prepareStatement(sql);
    		
    		stmt.setString(1, categoria.getNome());
    		stmt.setInt(2, categoria.getId());
    		
    		int linhasAfetadas = stmt.executeUpdate();
    		
    		if (linhasAfetadas > 0) {
    			System.out.println("Categoria ID: "+categoria.getId());
    			return true;
    		} else {
    			System.out.println("Nenhuma categoria com esse ID");
    		}
    	} catch (SQLException r) {
    		System.err.println("Erro ao atualizar");
    		System.err.println("Detalhes " + r.getMessage());
    	} finally {
    		try {
    			if (stmt != null) stmt.close();
    			if(conexao !=null) conexao.close();
    		} catch (SQLException r) {
    			System.out.println("Erro ao fechar "+r.getMessage());
    		}
    	}
    	return false;
    }
    
    public boolean deletar(int id) {
    	String sql = "DELETE FROM CATEGORIA WHERE ID = ?";
    	
    	Connection conexao = null;
    	PreparedStatement stmt = null;
    	
    	try {
    		conexao = ConexaoDB.getConexao();
    		stmt = conexao.prepareStatement(sql);
    		
    		stmt.setInt(1, id);
    		
    		int linhasAfetadas = stmt.executeUpdate();
    		
    		if (linhasAfetadas > 0) {
    			System.out.println("Categoria ID: " + id + " deletada");
    			return true;
    		} else {
    			System.out.println("Nenhuma categoria com esse id");
    		}
    		
    	} catch (SQLException r) {
    		System.err.println("Erro ao deletar");
    		System.err.println("Detalhes " + r.getMessage());
    	} finally {
    		try {
    			if (stmt != null) stmt.close();
    			if (conexao != null) conexao.close();
    		} catch (SQLException r) {
    			System.out.println("Erro ao fechar: "+r.getMessage());
    		}
    	}
    	
    	return false;
    }
    
}