<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paisController" class="br.com.bb.cbe.controllers.PaisController"/>
<jsp:useBean id="moedaController" class="br.com.bb.cbe.controllers.MoedaController"/>
<jsp:useBean id="empresaController" class="br.com.bb.cbe.controllers.EmpresaController"/>
<c:set var="chave" value="${sessionScope.chave}" />
<c:set var="comissao" value="${sessionScope.nomeComissao}" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="/ProjetoCBE/resources/css/style.css">
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <title>Topo</title>
        
        <!-- CDN jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

        <!-- CDN jQuery Confirm -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.4/jquery-confirm.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.4/jquery-confirm.min.js"></script>

        <!-- CDN Select2 -->
        <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

        <!-- Biblioteca Icones Bootstrap -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

        <!-- Latest compiled JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
        <header>
            <div class="logo-container">
                <picture id="logoBranca">
                    <img class="logo" src="/ProjetoCBE/resources/imgs/logo_branca.png" alt="Logo branca">
                </picture>
                <picture id="logoAzul" style="display:none;">
                    <img class="logo" src="/ProjetoCBE/resources/imgs/logo_azul.png" alt="Logo azul">
                </picture>
                <h2>Formulário Declaração CBE - Capitais Brasileiros no Exterior</h2>
                <picture id="imgConscientiza">
                    <img class="conscientizacao" src="/ProjetoCBE/resources/imgs/conscientizacao.png" alt="Fita branca de conscientização">
                </picture>
                <picture id="imgSetembro"  class="conscientizacao-setembro">
                    <img src="/ProjetoCBE/resources/imgs/setembro.png" alt="Fita branca de conscientização">
                </picture>
            </div>
            <div class="usuario-container">
                <img class="foto" src="https://humanograma.intranet.bb.com.br/avatar/${chave}" alt="Foto do funcionário"><br><br>
                <h5 style="display:none;">Se precisar, peça ajuda!</h5>
            </div>
        </header>
        <div class="tela-preta-loading">
            <div>
                <img src="/ProjetoCBE/resources/imgs/loading.gif" alt="Loading image"/>
                <p>Carregando...</p>
            </div>
        </div>
        <script src="/ProjetoCBE/resources/js/topo.js"></script>
    </body>
</html>