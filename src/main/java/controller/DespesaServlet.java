package controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/despesas")
public class DespesaServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	 
    	    response.setContentType("application/json");
    	    response.setCharacterEncoding("UTF-8");
    	    
    	    String json = "{\"mensagem\": \"API funcionando!\"}";
    	    
    	    response.getWriter().write(json);
    	}
        
}