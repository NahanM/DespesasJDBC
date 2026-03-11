package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Despesa;
import repository.DespesaRepository;

@WebServlet("/despesas/*")
public class DespesaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final DespesaRepository repository = new DespesaRepository();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

        List<Despesa> despesas = repository.listarTodas();

        preparaResponseJSON(response);
        mapper.writeValue(response.getWriter(), despesas);
    }

    private void listarPorId(int id, HttpServletResponse response) throws IOException {

        Despesa despesa = repository.buscarPorId(id);

        if (despesa == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        preparaResponseJSON(response);
        mapper.writeValue(response.getWriter(), despesa);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Despesa despesa = mapper.readValue(request.getReader(), Despesa.class);

        boolean salvou = repository.salvar(despesa);

        if (salvou) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            mapper.writeValue(response.getWriter(), despesa);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao salvar despesa");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Despesa despesa = mapper.readValue(request.getReader(), Despesa.class);

        boolean atualizou = repository.atualizar(despesa);

        if (atualizou) {
            mapper.writeValue(response.getWriter(), despesa);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Despesa não encontrada");
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
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Despesa não encontrada");
        }
    }

    private void preparaResponseJSON(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}