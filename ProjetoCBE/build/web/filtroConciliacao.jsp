<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.com.bb.cbe.controllers.ConsolidadoController"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Concialiação</title>
        <link rel="stylesheet" href="resources/css/style.css"/>
    </head>
    <body>
        <%@include file="topo.jsp"%>
        
        <%
            String periodosJson = ConsolidadoController.getPeriodosDisponiveisJson();
        %>
        
        <form action="views/consolidado.jsp" method="get">
            <div class="container-filtro-trimeste">
                <h1>Selecione ano e trimestre referentes:</h1>

                <div class="div-opcoes">
                    <select name="ano" id="ano">
                        <option value="" disabled selected>Selecione o Ano</option>
                    </select>

                    <select name="tri" id="tri">
                        <option value="" disabled selected>Selecione o Trimestre</option>
                    </select>
                </div>
                
                <button class="btn" type="submit">Buscar</button>
            </div>
        </form>

        <script>
            // Dados vindos do Java (Controller -> DAO -> Banco)
            // Exemplo: [{"ano":2025, "tri":4}, {"ano":2025, "tri":3}]
            var dadosPeriodos = <%= periodosJson %>;

            document.addEventListener('DOMContentLoaded', function() {
                const selectAno = document.getElementById('ano');
                const selectTri = document.getElementById('tri');

                // 1. Extrair anos únicos e preencher o select de Anos
                const anosUnicos = [...new Set(dadosPeriodos.map(item => item.ano))];
                
                // Limpa opções antigas (mantendo a primeira)
                selectAno.innerHTML = '<option value="" disabled selected>Selecione o Ano</option>';
                
                anosUnicos.forEach(ano => {
                    const option = document.createElement('option');
                    option.value = ano;
                    option.textContent = ano;
                    selectAno.appendChild(option);
                });

                // 2. Quando o usuário mudar o Ano, filtrar os Trimestres disponíveis
                selectAno.addEventListener('change', function() {
                    const anoSelecionado = parseInt(this.value);
                    
                    // Limpa trimestres anteriores
                    selectTri.innerHTML = '<option value="" disabled selected>Selecione o Trimestre</option>';
                    
                    // Filtra os dados para pegar apenas os trimestres daquele ano
                    const trimestresDoAno = dadosPeriodos
                        .filter(item => item.ano === anoSelecionado)
                        .map(item => item.tri)
                        .sort(); // Ordena 1, 2, 3, 4

                    trimestresDoAno.forEach(tri => {
                        const option = document.createElement('option');
                        option.value = tri;
                        option.textContent = tri + 'º Trimestre';
                        selectTri.appendChild(option);
                    });
                });
            });
        </script>
    </body>
</html>