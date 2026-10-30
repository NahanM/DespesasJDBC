package service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import model.Despesa;
import repository.DespesaRepository;

public class DespesaService {

    private DespesaRepository repository;

    public DespesaService() {
        this.repository = new DespesaRepository();
    }

    public boolean salvar(Despesa despesa) {

        if (!validarDespesa(despesa)) {
            return false;
        }

        return repository.salvar(despesa);
    }

    private boolean validarDespesa(Despesa despesa) {

        if (despesa == null) {
            return false;
        }

        if (despesa.getDescricao() == null || despesa.getDescricao().trim().isEmpty()) {
            System.out.println("Descricao não pode ser vazia");
            return false;
        }

        if (despesa.getValor() == null || despesa.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Valor deve ser maior que 0");
            return false;
        }

        if (despesa.getCategoria() == null || despesa.getCategoria().getId() == null) {
            System.out.println("Categoria é obrigatória");
            return false;
        }

        if (despesa.getData() == null || despesa.getData().isAfter(LocalDate.now())) {
            System.out.println("Data não pode ser futura");
            return false;
        }

        return true;
    }

    public boolean atualizar(Despesa despesa) {

        if (!validarDespesa(despesa)) {
            return false;
        }

        return repository.atualizar(despesa);
    }

    public List<Despesa> listarTodas() {
        return repository.listarTodas();
    }

    public Despesa buscarPorId(int id) {

        if (id <= 0) {
            return null;
        }

        return repository.buscarPorId(id);
    }

    public List<Despesa> buscarPorCategoria(int categoriaId) {

        if (categoriaId <= 0) {
            return List.of();
        }

        return repository.buscarPorCategoria(categoriaId);
    }

    public boolean deletar(int id) {

        if (id <= 0) {
            return false;
        }

        return repository.deletar(id);
    }

    public BigDecimal calcularTotalGeral() {

        List<Despesa> despesas = repository.listarTodas();

        BigDecimal total = BigDecimal.ZERO;

        for (Despesa d : despesas) {
            total = total.add(d.getValor());
        }

        return total;
    }

    public BigDecimal calcularTotalPorCategoria(int categoriaId) {

        List<Despesa> despesas = repository.buscarPorCategoria(categoriaId);

        BigDecimal total = BigDecimal.ZERO;

        for (Despesa d : despesas) {
            total = total.add(d.getValor());
        }

        return total;
    }
}