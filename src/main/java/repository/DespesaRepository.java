package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Despesa;
import util.ConexaoDB;

public class DespesaRepository {
	
	public boolean salvar (Despesa despesa) {
		String sql = "Insert into despesa (descricao, valor, data, categoria_id) values (?, ?, ?, ?)";
		
		Connection conexao = null;
		PreparedStatement stmt = null;
		
		try {
			conexao = ConexaoDB.getConexao();
			stmt = conexao.prepareStatement(sql);
			
			stmt.setString(1, despesa.getDescricao());
			stmt.setBigDecimal(2, despesa.getValor());
			stmt.setDate(3, Date.valueOf(despesa.getData()));
			stmt.setInt(4, despesa.getCategoria().getId());
			
			int linhas = stmt.executeUpdate();
			
			if (linhas > 0) {
				System.out.println("Despsa salva");
				return true;
			}
		} catch (SQLException r) {
			System.err.println("Erro ao salvar a despera");
			System.err.println("Detalhes: "+r.getMessage());
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conexao != null) conexao.close();
			} catch (SQLException r) {
				System.out.println("Erro ao fechar "+r.getMessage());
			}
		}
		
		return false;
	}
	
	public List<Despesa> listarTodas() {
		List<Despesa> despesas = new ArrayList<>();
		
		String sql = "Select d.id, d.descricao, d.valor, d.data ,	" +
		" c.id as categoria_id, c.nome as categoria_nome " +
				" from despesa d" +
		" inner join categoria c on d.categoria_id = c.id " +
				" order by d.data desc ";
		
		Connection conexao = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conexao = ConexaoDB.getConexao();
			stmt = conexao.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				Categoria categoria = new Categoria (rs.getInt("categoria_id"), rs.getString("categoria_nome"));
				
				Despesa despesa = new Despesa (
						rs.getInt("id"),
						rs.getString("descricao"),
						rs.getBigDecimal("valor"),
						rs.getDate("data").toLocalDate(),
						categoria
						);
				despesas.add(despesa);
			}
			
			System.out.println(despesas.size() + " Despesas encontradas");
			
		} catch (SQLException r) {
			System.err.println("Erro ao listar despesas");
			System.err.println("Detalhes "+r.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conexao != null) conexao.close();
			} catch (SQLException r) {
				System.out.println("Erro ao fechar " + r.getMessage());
			}
		}
		return despesas;
	}
	
	public Despesa buscarPorId(int id) {
		String sql = "SELECT d.id, d.descricao, d.valor, d.data, " +
	"c.id as categoria_id, c.nome as categoria_nome " +
				"from despesa d "+
	"inner join categoria c on d.categoria_id = c.id "+
				"where d.id = ?";
		
		Connection conexao = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Despesa despesa = null;
	    
	    try {
	    	 conexao = ConexaoDB.getConexao();
	         stmt = conexao.prepareStatement(sql);
	         stmt.setInt(1, id);
	         
	         rs = stmt.executeQuery();
	         
	         if (rs.next()) {
	        	 Categoria categoria = new Categoria(
	                     rs.getInt("categoria_id"),
	                     rs.getString("categoria_nome")
	                 );
	        	 
	        	 despesa = new Despesa(
	                     rs.getInt("id"),
	                     rs.getString("descricao"),
	                     rs.getBigDecimal("valor"),
	                     rs.getDate("data").toLocalDate(),
	                     categoria
	                     );
	        	 System.out.println("Despesa encontrada");
	         } else {
	             System.out.println("⚠️ Despesa com ID " + id + " não encontrada");
	         }
	    } catch (SQLException e) {
	        System.err.println("❌ Erro ao buscar despesa por ID!");
	        System.err.println("Detalhes: " + e.getMessage()); 
	    }finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conexao != null) conexao.close();
	        } catch (SQLException e) {
	            System.err.println("Erro ao fechar recursos: " + e.getMessage());
	        }
	    }
	    return despesa;
	}
	
	public List<Despesa> buscarPorCategoria(int categoriaId) {
		List<Despesa> despesas = new ArrayList<>();
		
		String sql ="SELECT d.id, d.descricao, d.valor, d.data, " +
                "c.id AS categoria_id, c.nome AS categoria_nome " +
                "FROM despesa d " +
                "INNER JOIN categoria c ON d.categoria_id = c.id " +
                "WHERE c.id = ? " +
                "order by d.data desc";
		
		Connection conexao = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	    	conexao = ConexaoDB.getConexao();
	        stmt = conexao.prepareStatement(sql);
	        stmt.setInt(1, categoriaId);
	        
	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            Categoria categoria = new Categoria(
	                rs.getInt("categoria_id"),
	                rs.getString("categoria_nome")
	            );
	            
	            Despesa despesa = new Despesa(
	                    rs.getInt("id"),
	                    rs.getString("descricao"),
	                    rs.getBigDecimal("valor"),
	                    rs.getDate("data").toLocalDate(),
	                    categoria
	                );
	            despesas.add(despesa);
	    }
	        System.out.println("✅ " + despesas.size() + " despesa(s) encontrada(s) na categoria");
	        
	}catch (SQLException e) {
        System.err.println("❌ Erro ao buscar despesas por categoria!");
        System.err.println("Detalhes: " + e.getMessage());
        
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conexao != null) conexao.close();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }
	    
	return despesas;
}
	public boolean atualizar(Despesa despesa) {
		String sql = "update despesa set descricao = ?, valor = ?, data = ?, categoria_id = ? " +
				"where id = ?";
		
		Connection conexao = null;
		PreparedStatement stmt = null;
		
		try {
			conexao = ConexaoDB.getConexao();
	        stmt = conexao.prepareStatement(sql);
	        
	        stmt.setString(1, despesa.getDescricao());
	        stmt.setBigDecimal(2, despesa.getValor());
	        stmt.setDate(3, Date.valueOf(despesa.getData()));
	        stmt.setInt(4, despesa.getCategoria().getId());
	        stmt.setInt(5, despesa.getId());
	        
	        int linhas = stmt.executeUpdate();
	        
	        if (linhas > 0) {
	        	System.out.println("DespesaID: "+despesa.getId()+" atualizada");
	        	return true;
	        } else {
	        	System.out.println("Nenhuma despesa encontradad com o ID "+ despesa.getId());
	        }
		} catch (SQLException e) {
	        System.err.println("❌ Erro ao atualizar despesa!");
	        System.err.println("Detalhes: " + e.getMessage());
	        
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (conexao != null) conexao.close();
	        } catch (SQLException e) {
	            System.err.println("Erro ao fechar recursos: " + e.getMessage());
	        }
	    }
		return false;
	}
	
	public boolean deletar(int id) {
	    String sql = "DELETE FROM despesa WHERE id = ?";
	    
	    Connection conexao = null;
	    PreparedStatement stmt = null;
	    
	    try {
	        conexao = ConexaoDB.getConexao();
	        stmt = conexao.prepareStatement(sql);
	        stmt.setInt(1, id);
	        
	        int linhas = stmt.executeUpdate();
	        
	        if (linhas > 0) {
	            System.out.println("✅ Despesa ID " + id + " deletada!");
	            return true;
	        } else {
	            System.out.println("⚠️ Nenhuma despesa encontrada com ID " + id);
	        }
	        
	    } catch (SQLException e) {
	        System.err.println("❌ Erro ao deletar despesa!");
	        System.err.println("Detalhes: " + e.getMessage());
	        
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (conexao != null) conexao.close();
	        } catch (SQLException e) {
	            System.err.println("Erro ao fechar recursos: " + e.getMessage());
	        }
	    }
	    
	    return false;
	}
}
