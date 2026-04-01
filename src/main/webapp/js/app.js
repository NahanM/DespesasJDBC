// ================================
// CONSTANTE: endereço da API
// ================================
const API_URL = "despesas/";
const CATEGORIAS_URL = "categorias/";

// ================================
// FUNÇÃO: carregar despesas
// ================================
// faz requisição GET para a API de despesas
function carregarDespesas() {
fetch(API_URL)

    // recebe a resposta da API
    .then(function(res) {

        // se a resposta não for sucesso, lança erro
        if (!res.ok) {
            throw new Error("Erro na requisição: " + res.status);
        }

        // converte resposta para objeto JavaScript
        return res.json();
    })

    // recebe os dados já convertidos
    .then(function(dados) {

        // debug dos dados
        console.log("Dados recebidos:", dados);

        // envia os dados para a função que renderiza a tabela
        preencherTabela(dados);
    })

    // trata erros da requisição ou processamento
    .catch(function(erro) {
        console.error("Falha ao carregar despesas:", erro);
    });
	}


// ================================
// FUNÇÃO: preencher a tabela
// ================================
function preencherTabela(despesas) {

    //seleciona o <tbody> da tabela onde os dados serão inseridos
    const tbody = document.querySelector("#tabelaDespesas tbody");

    // limpa o que estava antes (os dados fictícios)
    tbody.innerHTML = "";

    // percorre o array e cria uma linha para cada despesa
    despesas.forEach(function(d) {

		//cria linha
        const linha = document.createElement("tr");

		// monta conteudo
        linha.innerHTML =
            "<td>" + d.id + "</td>" +
            "<td>" + d.descricao + "</td>" +
            "<td>" + d.categoria.nome + "</td>" +
            "<td>" + d.data + "</td>" +
            "<td>R$ " + d.valor + "</td>" +
			"<td>" +
			    "<button class='btn-editar' onclick='abrirModalEdicao(" + d.id + ")'>Editar</button>" +
			    "<button class='btn-excluir' onclick='excluirDespesa(" + d.id + ")'>Excluir</button>" +
			"</td>";

        tbody.appendChild(linha); // adiciona a linha criada dentro da tabela
    });
	atualizarCards(despesas);
}

// ================================
// FUNÇÃO: carregar categorias
// ================================
function carregarCategorias() {

    // faz requisição GET para a API de categorias
    fetch(CATEGORIAS_URL)

        // recebe a resposta da API
        .then(function(res) {

            // se a resposta não for sucesso, lança erro
            if (!res.ok) {
                throw new Error("Erro ao buscar categorias: " + res.status);
            }

            // converte a resposta para objeto JavaScript
            return res.json();
        })

        // recebe as categorias já convertidas
        .then(function(categorias) {

            // envia as categorias para a função que preenche o dropdown
            preencherSelectCategorias(categorias);
        })

        // trata erros da requisição ou processamento
        .catch(function(erro) {
            console.error("Falha ao carregar categorias:", erro);
        });
}


// ================================
// FUNÇÃO: preencher o select
// ================================
function preencherSelectCategorias(categorias) {

    // encontra o elemento <select> pelo id (onde insere as opções)
    const select = document.getElementById("inputCategoria");

    // limpa todas as opções existentes
    select.innerHTML = "";

    // ================================
    // OPÇÃO PADRÃO (placeholder)
    // ================================

    // cria uma nova opção (<option>)
    const opcaoPadrao = document.createElement("option");

    // define valor vazio
    opcaoPadrao.value = "";

    // define o texto que aparece para o usuário
    opcaoPadrao.textContent = "Selecione uma categoria";

    // adiciona essa opção no select
    select.appendChild(opcaoPadrao);

    // ================================
    // LOOP DAS CATEGORIAS
    // ================================

    // percorre o array de categorias recebido da API
    categorias.forEach(function(c) {

        // cria uma nova <option> para cada categoria
        const opcao = document.createElement("option");

        // define o valor da option (esse valor será enviado depois para o backend)
        opcao.value = c.id;

        // define o texto visível no dropdown (nome da categoria)
        opcao.textContent = c.nome;

        // adiciona a option dentro do select
        select.appendChild(opcao);
    });
}
// ================================
// FUNÇÃO: salvar despesa (POST)
// ================================
function salvarDespesa() {

    // ================================
    // PASSO 1: capturar os valores dos campos
    // ================================

    // pega o valor do input de descrição e remove espaços extras no começo/fim
    const descricao  = document.getElementById("inputDescricao").value.trim();

    // pega o valor do input de valor (vem como string)
    const valor      = document.getElementById("inputValor").value;

    // pega a data selecionada no input
    const data       = document.getElementById("inputData").value;

    // pega o id da categoria selecionada no dropdown
    const categoriaId = document.getElementById("inputCategoria").value;

    // ================================
    // PASSO 2: validar se todos os campos estão preenchidos
    // ================================

    // verifica se algum campo está vazio
    if (!descricao || !valor || !data || !categoriaId) {

        // mostra alerta para o usuário
        alert("Preencha todos os campos antes de salvar.");

        // interrompe a execução da função
        return;
    }

    // ================================
    // PASSO 3: montar o objeto que a API espera receber
    // ================================

    // cria um objeto JavaScript no formato esperado pelo backend
    const novaDespesa = {

        // texto da descrição
        descricao: descricao,

        // converte o valor de string para número decimal
        valor: parseFloat(valor),

        // data (já vem no formato aceito pelo backend)
        data: data,

        // objeto aninhado de categoria (relacionamento)
        categoria: {

            // converte o id da categoria para número inteiro
            id: parseInt(categoriaId)
        }
    };

    // ================================
    // PASSO 4: enviar requisição POST para a API
    // ================================

    fetch(API_URL, {

        // método HTTP POST (criar novo registro)
        method: "POST",

        // cabeçalho informando que estamos enviando JSON
        headers: { "Content-Type": "application/json" },

        // converte o objeto JS para JSON (string) antes de enviar
        body: JSON.stringify(novaDespesa)
    })

    // ================================
    // TRATAMENTO DA RESPOSTA
    // ================================

    .then(function(res) {

        // verifica se a resposta da API foi bem sucedida
        if (!res.ok) {
            throw new Error("Erro ao salvar: " + res.status);
        }

        // converte a resposta para objeto JavaScript
        return res.json();
    })

    .then(function(despesaSalva) {

        // exibe no console a despesa retornada pela API
        console.log("Despesa salva:", despesaSalva);

		// fecha o modal após salvar
		    const modal = bootstrap.Modal.getInstance(
		        document.getElementById("modalNovaDespesa")
		    );
		    modal.hide();
		
        // limpa os campos do formulário
        limparFormulario();

        // recarrega a lista de despesas (atualiza a tabela)
        carregarDespesas();
    })

    // ================================
    // TRATAMENTO DE ERROS
    // ================================

    .catch(function(erro) {

        // mostra erro no console (debug)
        console.error("Falha no POST:", erro);

        // mostra alerta para o usuário
        alert("Erro ao salvar a despesa. Verifique o console.");
    });
}
// ================================
// FUNÇÃO: limpar o formulário
// ================================
function limparFormulario() {

    // limpa o campo de descrição (remove qualquer texto digitado)
    document.getElementById("inputDescricao").value = "";

    // limpa o campo de valor
    document.getElementById("inputValor").value = "";

    // limpa o campo de data
    document.getElementById("inputData").value = "";

    // reseta o select de categoria (volta para valor vazio/padrão)
    document.getElementById("inputCategoria").value = "";
}


// ================================
// FUNÇÃO: excluir despesa (DELETE)
// ================================
function excluirDespesa(id) {

    // PASSO 1: pede confirmação ao usuário
    const confirmou = confirm("Tem certeza que deseja excluir esta despesa?");

    // PASSO 2: se não confirmou, para aqui
    if (!confirmou) {
        return;
    }

    // PASSO 3: dispara o fetch DELETE
    fetch(API_URL + id, {
        method: "DELETE"
    })
    .then(function(res) {
        if (!res.ok) {
            throw new Error("Erro ao excluir: " + res.status);
        }
        // API retorna 204 No Content — sem body para converter
        carregarDespesas();
    })
    .catch(function(erro) {
        console.error("Falha no DELETE:", erro);
        alert("Erro ao excluir a despesa. Verifique o console.");
    });
}

// ================================
// FUNÇÃO: abrir modal de edição
// ================================
function abrirModalEdicao(id) {

    // PASSO 1: busca os dados atuais da despesa pelo id
    fetch(API_URL + id)
        .then(function(res) {
            if (!res.ok) {
                throw new Error("Erro ao buscar despesa: " + res.status);
            }
            return res.json();
        })
        .then(function(despesa) {
            // PASSO 2: preenche os campos do modal
            preencherModalEdicao(despesa);
            // PASSO 3: abre o modal via JavaScript
            const modal = new bootstrap.Modal(document.getElementById("modalEditarDespesa"));
            modal.show();
        })
        .catch(function(erro) {
            console.error("Falha ao buscar despesa:", erro);
        });
}


// ================================
// FUNÇÃO: preencher campos do modal
// ================================
function preencherModalEdicao(despesa) {

    // preenche cada campo com os dados da despesa
    document.getElementById("editId").value        = despesa.id;
    document.getElementById("editDescricao").value = despesa.descricao;
    document.getElementById("editValor").value     = despesa.valor;
    document.getElementById("editData").value      = despesa.data;

    // preenche o select de categorias do modal
    const selectEdit = document.getElementById("editCategoria");
    selectEdit.innerHTML = "";

    fetch("categorias/")
        .then(function(res) { return res.json(); })
        .then(function(categorias) {
            categorias.forEach(function(c) {
                const opcao = document.createElement("option");
                opcao.value       = c.id;
                opcao.textContent = c.nome;

                // marca como selecionada a categoria atual da despesa
                if (c.id === despesa.categoria.id) {
                    opcao.selected = true;
                }

                selectEdit.appendChild(opcao);
            });
        });
}

// ================================
// FUNÇÃO: salvar edição (PUT)
// ================================
function salvarEdicao() {

    // PASSO 1: captura os valores do modal
    const id          = document.getElementById("editId").value;
    const descricao   = document.getElementById("editDescricao").value.trim();
    const valor       = document.getElementById("editValor").value;
    const data        = document.getElementById("editData").value;
    const categoriaId = document.getElementById("editCategoria").value;

    // PASSO 2: valida os campos
    if (!descricao || !valor || !data || !categoriaId) {
        alert("Preencha todos os campos.");
        return;
    }

    // PASSO 3: monta o objeto atualizado
    const despesaAtualizada = {
        id:        parseInt(id),
        descricao: descricao,
        valor:     parseFloat(valor),
        data:      data,
        categoria: {
            id: parseInt(categoriaId)
        }
    };

    // PASSO 4: dispara o fetch PUT
    fetch(API_URL + id, {
        method:  "PUT",
        headers: { "Content-Type": "application/json; charset=UTF-8" },
        body:    JSON.stringify(despesaAtualizada)
    })
    .then(function(res) {
        if (!res.ok) {
            throw new Error("Erro ao atualizar: " + res.status);
        }
        return res.json();
    })
    .then(function(despesaAtualizada) {
        console.log("Despesa atualizada:", despesaAtualizada);

        // fecha o modal
        const modal = bootstrap.Modal.getInstance(
            document.getElementById("modalEditarDespesa")
        );
        modal.hide();

        // recarrega a tabela
        carregarDespesas();
    })
    .catch(function(erro) {
        console.error("Falha no PUT:", erro);
        alert("Erro ao atualizar. Verifique o console.");
    });
}

// ================================
// FUNÇÃO: atualizar cards de resumo
// ================================
function atualizarCards(despesas) {

    // CARD 1: soma todos os valores
    const total = despesas.reduce(function(acumulador, d) {
        return acumulador + parseFloat(d.valor);
    }, 0);

    // CARD 2: quantidade de despesas
    const quantidade = despesas.length;

    // CARD 3: despesa mais recente
    const maisRecente = despesas.reduce(function(maisNova, d) {
        return new Date(d.data) > new Date(maisNova.data) ? d : maisNova;
    }, despesas[0]);

    // atualiza os elementos na tela
    document.getElementById("totalDespesas").textContent =
        "R$ " + total.toFixed(2).replace(".", ",");

    document.getElementById("qtdLancamentos").textContent =
        quantidade + " lançamento(s)";

    document.getElementById("ultimoLancamento").textContent =
        maisRecente ? formatarData(maisRecente.data) + " — " + maisRecente.descricao : "—";
}


// ================================
// FUNÇÃO AUXILIAR: formatar data
// ================================
function formatarData(dataISO) {
    if (!dataISO) return "—";
    const partes = dataISO.split("-");
    return partes[2] + "/" + partes[1] + "/" + partes[0];
}

// ================================
// EVENTO: quando o HTML terminar de carregar
// ================================
document.addEventListener("DOMContentLoaded", function() {

    // chama a função que busca e preenche a tabela de despesas
    carregarDespesas();

    // chama a função que busca e preenche o dropdown de categorias
    carregarCategorias();

    // ================================
    // EVENTO DO BOTÃO SALVAR
    // ================================

    // seleciona o botão pelo id e adiciona um evento de clique
    document.getElementById("btnSalvar").addEventListener("click", salvarDespesa);

    // quando o usuário clicar no botão, executa a função salvarDespesa
	document.getElementById("btnSalvarEdicao").addEventListener("click", salvarEdicao);
});