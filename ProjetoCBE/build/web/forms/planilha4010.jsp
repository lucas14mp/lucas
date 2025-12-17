<!-- =========================== FORM =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" type="imagex/png" href="${pageContext.request.contextPath}/resources/imgs/logo_branca.png">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>
        
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        
        <title>Importar Tabela 4010</title>
        
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        
        <main class="main view-container">
            <article class="article">
                
                <div style="margin-bottom: 20px;">
                    <a href="${pageContext.request.contextPath}/filtro4010Conciliacao.jsp">
                        <input type="button" value="Voltar" class="btn voltar">
                    </a>
                </div>

                <h2 style="color: #003399;">Importação de Dados - Tabela 4010</h2>
                <br>
                <p style="text-align: center;">
                    Utilize a área abaixo para fazer o upload da planilha Excel contendo os dados da Tabela 4010. <br>
                    <b>Atenção:</b> Deve-se enviar somente a planilha referente ao fechamento de cada trimestre.
                </p>
                
                <div class="area-upload">
                    <h3 style="color: #333; margin-bottom: 15px;">Anexar Planilha (.xlsx)</h3>

                    <form action="${pageContext.request.contextPath}/UploadTabela4010Controller" method="post" enctype="multipart/form-data">

                        <label for="arquivoExcel" style="font-weight: bold; display: block; margin-bottom: 5px;">
                            Selecione o arquivo no seu computador:
                        </label>
                        
                        <div class="input-wrapper">
                            <input type="file" name="arquivoExcel" id="arquivoExcel" class="input-arquivo" accept=".xlsx, .xls" required onchange="verificarArquivo()"/>
                            
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
                var input = document.getElementById('arquivoExcel');
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
                var input = document.getElementById('arquivoExcel');
                var btnRemover = document.getElementById('btnRemover');
                
                input.value = ''; // Limpa o input
                btnRemover.style.display = 'none'; // Esconde o botão novamente
            }

            // Validação extra no envio
            document.querySelector('form').addEventListener('submit', function(e) {
                var input = document.getElementById('arquivoExcel');
                if (!input.value) {
                    alert('Nenhum arquivo selecionado.');
                    e.preventDefault();
                    return;
                }
                if (!input.value.endsWith('.xlsx') && !input.value.endsWith('.xls')) {
                    alert('Por favor, selecione um arquivo Excel válido (.xlsx ou .xls).');
                    e.preventDefault();
                }
            });
        </script>
    </body>
</html>