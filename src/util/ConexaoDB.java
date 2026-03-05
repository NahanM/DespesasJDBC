package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
	
	private static final String URL = "jdbc:postgresql://localhost:5432/gestaoDespesas";
	private static final String USUARIO = "postgres";
	private static final String SENHA = "root";
	
	public static Connection getConexao() {
		try {
			Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
			System.out.println("Conexão deu boa");
			return conexao;
		} catch (SQLException r) {
			System.err.println("Não deu boa");
			System.err.println("Ó: " + r.getMessage());
			return null;
		}
	}
	
	public static void fecharConexao(Connection conexao) {
		if (conexao != null) {
			try {
				conexao.close();
				System.out.println("Fechei");
			} catch (SQLException r) {
				System.err.println("Não fechou");
				System.err.println("Problema: " + r.getMessage());
			}
		}
	}
	
}
