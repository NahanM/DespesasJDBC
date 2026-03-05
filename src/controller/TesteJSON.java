package controller;

import org.json.simple.JSONObject;

public class TesteJSON {
    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        obj.put("nome", "Teste");
        obj.put("valor", 100);
        
        System.out.println(obj.toJSONString());
        // Deve imprimir: {"nome":"Teste","valor":100}
    }
}