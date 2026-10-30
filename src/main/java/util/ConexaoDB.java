package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
	
	private static int contador = 0;
	
	public static Connection getConexao() throws SQLException{
		
		if(contador++ == 0) {
			try{
				Class.forName("org.postgresql.Driver");
			}
			catch(ClassNotFoundException ex) {
				System.out.println("Não carregou o driver do PostgreSQL");
				return null;
			}
		}

		String url = lerVariavel("DB_URL");
		String usuario = lerVariavel("DB_USER");
		String senha = lerVariavel("DB_PASSWORD");
		
		try {
			Connection conexao = DriverManager.getConnection(url, usuario, senha);
			System.out.println("Conexão deu boa ("+contador+")");
			return conexao;
		}
		catch(SQLException ex) {
			System.out.println("Deu ruim ("+contador+")");
			ex.printStackTrace();
			throw ex; // toca a exceção pra cima
		}
	}

	// Lê uma variável de ambiente e avisa claramente se ela não existir
	private static String lerVariavel(String nome) throws SQLException {
    	String valor = System.getenv(nome);

    	if (valor == null || valor.isBlank()) {
        	throw new SQLException("Variável de ambiente " + nome + " não configurada");
    	}

    return valor;
	}
}
