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

    private Categoria mapearCategoria(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String nome = rs.getString("nome");

        return new Categoria(id, nome);
    }

    public List<Categoria> listarTodas() {

        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT id, nome FROM categoria ORDER BY nome";

        try (
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {
                Categoria categoria = mapearCategoria(rs);
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar categorias");
            System.err.println("Detalhes: " + e.getMessage());
        }

        return categorias;
    }

    public Categoria buscarPorId(int id) {

        if (id <= 0) {
            return null;
        }

        String sql = "SELECT id, nome FROM categoria WHERE id = ?";

        try (
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapearCategoria(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar categoria por id");
            System.err.println("Detalhes: " + e.getMessage());
        }

        return null;
    }

    public boolean salvar(Categoria categoria) {

        if (categoria == null || categoria.getNome() == null) {
            return false;
        }

        String sql = "INSERT INTO categoria (nome) VALUES (?) RETURNING id";

        try (
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, categoria.getNome());

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    int idGerado = rs.getInt("id");
                    categoria.setId(idGerado);
                    return true;
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar categoria");
            System.err.println("Detalhes: " + e.getMessage());
        }

        return false;
    }

    public boolean atualizar(Categoria categoria) {

        if (categoria == null || categoria.getId() <= 0 || categoria.getNome() == null) {
            return false;
        }

        String sql = "UPDATE categoria SET nome = ? WHERE id = ?";

        try (
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, categoria.getNome());
            stmt.setInt(2, categoria.getId());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar categoria");
            System.err.println("Detalhes: " + e.getMessage());
        }

        return false;
    }

    public boolean deletar(int id) {

        if (id <= 0) {
            return false;
        }

        String sql = "DELETE FROM categoria WHERE id = ?";

        try (
            Connection conexao = ConexaoDB.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar categoria");
            System.err.println("Detalhes: " + e.getMessage());
        }

        return false;
    }
}