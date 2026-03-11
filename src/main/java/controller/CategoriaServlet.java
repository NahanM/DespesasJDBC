package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;	

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Categoria;
import repository.CategoriaRepository;

@WebServlet("/categorias/*")
public class CategoriaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final CategoriaRepository repository = new CategoriaRepository();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	// traduz o JSON do request para um objeto
        Categoria categoria = mapper.readValue(request.getReader(), Categoria.class);

        // tenta salvar no banco
        boolean salvou = repository.salvar(categoria);

        if (salvou) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            mapper.writeValue(response.getWriter(), categoria);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao salvar categoria");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	// traduz o JSON para objeto
        Categoria categoria = mapper.readValue(request.getReader(), Categoria.class);

        // grava no banco de dados
        boolean atualizou = repository.atualizar(categoria);

        // escreve a resposta
        if (atualizou) {
            mapper.writeValue(response.getWriter(), categoria);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Categoria não encontrada");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID não informado");
            return;
        }

        int id = Integer.parseInt(path.substring(1));

        boolean deletou = repository.deletar(id);

        if (deletou) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Categoria não encontrada");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if (path == null || path.equals("/") || path.equals("")) {
            listarTodos(response);
        } else {
            try {
                int id = Integer.parseInt(path.substring(1));
                listarPorId(id, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    private void listarTodos(HttpServletResponse response) throws IOException {

        List<Categoria> categorias = repository.listarTodas();

        preparaResponseJSON(response);
        mapper.writeValue(response.getWriter(), categorias);
    }

	private void preparaResponseJSON(HttpServletResponse response) {
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
	}

    private void listarPorId(int id, HttpServletResponse response) throws IOException {

        Categoria categoria = repository.buscarPorId(id);

        if (categoria == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        preparaResponseJSON(response);

        mapper.writeValue(response.getWriter(), categoria);
    }
}