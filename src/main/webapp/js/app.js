// ================================
// CONSTANTE: endereço da API
// ================================
const API_URL = "despesas/";
const CATEGORIAS_URL = "categorias/"; // ← mesma ideia
// ================================
// FUNÇÃO: carregar despesas
// ================================
function carregarDespesas() {

    fetch(API_URL)
        .then(function(res) {
            // ETAPA 1: verifica se a resposta foi bem sucedida
            if (!res.ok) {
                throw new Error("Erro na requisição: " + res.status);
            }
            // ETAPA 2: converte o corpo da resposta de texto JSON para objeto JS
            return res.json();
        })
        .then(function(dados) {
            // ETAPA 3: usa os dados para preencher a tabela
            console.log("Dados recebidos:", dados);
            preencherTabela(dados);
        })
        .catch(function(erro) {
            // ETAPA 4: captura qualquer erro das etapas anteriores
            console.error("Falha ao carregar despesas:", erro);
        });
}


// ================================
// FUNÇÃO: preencher a tabela
// ================================
function preencherTabela(despesas) {

    // encontra o tbody da tabela pelo id
    const tbody = document.querySelector("#tabelaDespesas tbody");

    // limpa o que estava antes (os dados fictícios)
    tbody.innerHTML = "";

    // percorre o array e cria uma linha para cada despesa
    despesas.forEach(function(d) {

        const linha = document.createElement("tr");

        linha.innerHTML =
            "<td>" + d.id + "</td>" +
            "<td>" + d.descricao + "</td>" +
            "<td>" + d.categoria.nome + "</td>" +
            "<td>" + d.data + "</td>" +
            "<td>R$ " + d.valor + "</td>";

        tbody.appendChild(linha);
    });
}

// ================================
// FUNÇÃO: carregar categorias
// ================================
function carregarCategorias() {

    fetch(CATEGORIAS_URL)
        .then(function(res) {
            if (!res.ok) {
                throw new Error("Erro ao buscar categorias: " + res.status);
            }
            return res.json();
        })
        .then(function(categorias) {
            preencherSelectCategorias(categorias);
        })
        .catch(function(erro) {
            console.error("Falha ao carregar categorias:", erro);
        });
}


// ================================
// FUNÇÃO: preencher o select
// ================================
function preencherSelectCategorias(categorias) {

    // encontra o select pelo id
    const select = document.getElementById("inputCategoria");

    // limpa o "Carregando..." que estava antes
    select.innerHTML = "";

    // adiciona uma opção neutra no topo
    const opcaoPadrao = document.createElement("option");
    opcaoPadrao.value = "";
    opcaoPadrao.textContent = "Selecione uma categoria";
    select.appendChild(opcaoPadrao);

    // percorre o array e cria uma option para cada categoria
    categorias.forEach(function(c) {
        const opcao = document.createElement("option");
        opcao.value = c.id;
        opcao.textContent = c.nome;
        select.appendChild(opcao);
    });
}

document.addEventListener("DOMContentLoaded", function() {
    carregarDespesas();
    carregarCategorias(); // ← adicione essa linha
});