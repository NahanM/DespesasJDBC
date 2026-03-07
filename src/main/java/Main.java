import repository.CategoriaRepository;

import java.util.List;

import model.Categoria;

public class Main {

	public static void main(String[] args) {
		CategoriaRepository categRepo = new CategoriaRepository();
		List<Categoria> lista = categRepo.listarTodas();
		for (Categoria categ: lista) {
			System.out.println(categ);
		}
	}

}
