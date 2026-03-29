// ================================
// CONSTANTE: endereço da API
// ================================
const API_URL = "despesas/";
const CATEGORIAS_URL = "categorias/";

// ================================
// FUNÇÃO: carregar despesas
// ================================
// faz requisição GET para a API de despesas
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
            "<td>R$ " + d.valor + "</td>";

        tbody.appendChild(linha); // adiciona a linha criada dentro da tabela
    });
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
});