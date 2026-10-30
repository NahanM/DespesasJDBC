package modelTest;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import model.Categoria;
import model.Despesa;

class DespesaTest {

    @Test
    void deveriaCriarDespesaComDadosValidos() {

        Categoria categoria = new Categoria("Alimentação");

        Despesa despesa = new Despesa(
                "Mercado",
                new BigDecimal("50.00"),
                LocalDate.now(),
                categoria
        );

        assertEquals("Mercado", despesa.getDescricao());
        assertEquals(new BigDecimal("50.00"), despesa.getValor());
        assertEquals(categoria, despesa.getCategoria());
    }

    @Test
    void deveriaCriarDespesaComId() {

        Categoria categoria = new Categoria("Transporte");

        Despesa despesa = new Despesa(
                1,
                "Uber",
                new BigDecimal("20.00"),
                LocalDate.now(),
                categoria
        );

        assertEquals(1, despesa.getId());
        assertEquals("Uber", despesa.getDescricao());
    }

    @Test
    void deveriaAlterarDescricao() {

        Categoria categoria = new Categoria("Lazer");

        Despesa despesa = new Despesa(
                "Cinema",
                new BigDecimal("30.00"),
                LocalDate.now(),
                categoria
        );

        despesa.setDescricao("Teatro");

        assertEquals("Teatro", despesa.getDescricao());
    }

    @Test
    void deveriaAlterarValor() {

        Categoria categoria = new Categoria("Lazer");

        Despesa despesa = new Despesa(
                "Cinema",
                new BigDecimal("30.00"),
                LocalDate.now(),
                categoria
        );

        despesa.setValor(new BigDecimal("60.00"));

        assertEquals(new BigDecimal("60.00"), despesa.getValor());
    }

    @Test
    void deveriaAlterarData() {

        Categoria categoria = new Categoria("Lazer");

        Despesa despesa = new Despesa(
                "Cinema",
                new BigDecimal("30.00"),
                LocalDate.now(),
                categoria
        );

        LocalDate novaData = LocalDate.of(2025, 5, 10);

        despesa.setData(novaData);

        assertEquals(novaData, despesa.getData());
    }

    @Test
    void deveriaAlterarCategoria() {

        Categoria categoria1 = new Categoria("Lazer");
        Categoria categoria2 = new Categoria("Educação");

        Despesa despesa = new Despesa(
                "Cinema",
                new BigDecimal("30.00"),
                LocalDate.now(),
                categoria1
        );

        despesa.setCategoria(categoria2);

        assertEquals(categoria2, despesa.getCategoria());
    }

    @Test
    void deveriaLancarExcecaoQuandoDescricaoForNull() {

        Categoria categoria = new Categoria("Alimentação");

        assertThrows(IllegalArgumentException.class, () -> {
            new Despesa(
                    null,
                    new BigDecimal("50.00"),
                    LocalDate.now(),
                    categoria
            );
        });
    }

    @Test
    void deveriaLancarExcecaoQuandoValorForNull() {

        Categoria categoria = new Categoria("Alimentação");

        assertThrows(IllegalArgumentException.class, () -> {
            new Despesa(
                    "Mercado",
                    null,
                    LocalDate.now(),
                    categoria
            );
        });
    }

    @Test
    void deveriaLancarExcecaoQuandoValorForZeroOuNegativo() {

        Categoria categoria = new Categoria("Alimentação");

        assertThrows(IllegalArgumentException.class, () -> {
            new Despesa(
                    "Mercado",
                    new BigDecimal("-10.00"),
                    LocalDate.now(),
                    categoria
            );
        });
    }

    @Test
    void deveriaLancarExcecaoQuandoDataForNull() {

        Categoria categoria = new Categoria("Alimentação");

        assertThrows(IllegalArgumentException.class, () -> {
            new Despesa(
                    "Mercado",
                    new BigDecimal("50.00"),
                    null,
                    categoria
            );
        });
    }

    @Test
    void deveriaLancarExcecaoQuandoCategoriaForNull() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Despesa(
                    "Mercado",
                    new BigDecimal("50.00"),
                    LocalDate.now(),
                    null
            );
        });
    }

}