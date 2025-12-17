<!-- =========================== FORM =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="dataUtils" class="br.com.bb.cbe.Utils.DataUtils"/>

<c:set var="uor_equipe" value="${sessionScope.uorEquipe}" />
<c:set var="chave" value="${sessionScope.chave}" />
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Importar PTAX</title>
        <link rel="stylesheet" type="text/css" href="../resources/css/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <%@include file="../topo.jsp" %>
        <main class="main view-container">
            <article class="article">
                
                <div style="margin-bottom: 20px;">
                    <a href="${pageContext.request.contextPath}/filtroPtax.jsp">
                        <input type="button" value="Voltar" class="btn voltar">
                    </a>
                </div>

                <h2 style="color: #003399;">Importação de Dados - PTAX</h2>
                <br>
                <p style="text-align: center;">
                    Utilize a área abaixo para fazer o upload do arquivo CSV contendo os dados PTAX. <br>
                    <b>Atenção:</b> Deve-se Enviar somente o arquivo CSV referente ao trimestre necessário.
                </p>
                
                <div class="area-upload">
                    <h3 style="color: #333; margin-bottom: 15px;">Anexar PTAX (.csv)</h3>

                    <form action="${pageContext.request.contextPath}/UploadPtaxCsvController" method="post" enctype="multipart/form-data">

                        <label for="arquivoCsv" style="font-weight: bold; display: block; margin-bottom: 5px;">
                            Selecione o arquivo no seu computador:
                        </label>
                        
                        <div class="input-wrapper">
                            <input type="file" name="arquivoCsv" id="csvPtax" class="input-arquivo" accept=".csv" required onchange="verificarArquivo()"/>
                            
                            <i class="bi bi-x-circle-fill btn-remover-arquivo" id="btnRemover" onclick="limparArquivo()" title="Remover arquivo selecionado"></i>
                        </div>

                        <br>

                        <button type="submit" class="btn" style="padding: 10px 30px; font-size: 16px;">
                            <i class="bi bi-cloud-upload"></i> Processar Arquivo
                        </button>
                    </form>
                </div>

            </article>
        </main>
        
        <script>
            // Função chamada quando o usuário seleciona um arquivo
            function verificarArquivo() {
                var input = document.getElementById('csvPtax');
                var btnRemover = document.getElementById('btnRemover');
                
                // Se tem arquivo selecionado, mostra o X. Se não, esconde.
                if (input.value) {
                    btnRemover.style.display = 'block';
                } else {
                    btnRemover.style.display = 'none';
                }
            }

            // Função chamada quando clica no X
            function limparArquivo() {
                var input = document.getElementById('csvPtax');
                var btnRemover = document.getElementById('btnRemover');
                
                input.value = ''; // Limpa o input
                btnRemover.style.display = 'none'; // Esconde o botão novamente
            }

            // Validação extra no envio
            document.querySelector('form').addEventListener('submit', function(e) {
                var input = document.getElementById('csvPtax');
                if (!input.value) {
                    alert('Nenhum arquivo selecionado.');
                    e.preventDefault();
                    return;
                }
                if (!input.value.endsWith('.csv') && !input.value.endsWith('.csv')) {
                    alert('Por favor, selecione um arquivo Excel válido (.csv).');
                    e.preventDefault();
                }
            });
        </script>
    </body>
</html>