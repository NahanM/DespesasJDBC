package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.Categoria;
import repository.CategoriaRepository;

@WebServlet("/categorias")
public class CategoriaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obter todas as categorias
        CategoriaRepository repository = new CategoriaRepository();
        List<Categoria> categorias = repository.listarTodas();

        // Criar JSON
        JSONArray jsonArray = new JSONArray();
        for (Categoria c : categorias) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("id", c.getId());
            jsonObj.put("nome", c.getNome());
            jsonArray.add(jsonObj);
        }

        // Configurar response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonArray.toJSONString());
    }
}