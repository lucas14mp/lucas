<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <link rel="stylesheet" href="resources/css/style.css"/>
        <title>Conciliação</title>

        <!-- CDN jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

        <!-- CDN jQuery Confirm -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.4/jquery-confirm.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.4/jquery-confirm.min.js"></script>

        <!-- jQuery Mask -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.js"  integrity="sha512-0XDfGxFliYJPFrideYOoxdgNIvrwGTLnmK20xZbCAvPfLGQMzHUsaqZK8ZoH+luXGRxTrS46+Aq400nCnAT0/w==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

        <!-- Biblioteca Icones Bootstrap -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

        <!-- Latest compiled and minified CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Latest compiled JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

        <!-- CSS da página -->
        <link rel="stylesheet" href="resources/css/ptax.css">
    </head>
    <body>
        <%@include file="topo.jsp"%>
        <main class="p-4 w-100 d-flex flex-column justify-content-center align-items-center my-1" style="height: 50%">
            <div>


                <h1 class="fs-3 text-preto text-center fw-bold"> Tabela de inserção e visualização Conciliação </h1>
                <h2 class="fs-5 text-center text-cinza fw-normal"> Selecione a opção desejada para seguir na Conciliação </h2>
            </div>

            <div class="mt-4 w-100">


                <div class="container d-flex justify-content-center column-gap-4">
                    <a href="forms/planilha4010.jsp" class="d-block text-decoration-none">
                        <div style="width: 20vw; height: 30vh" class=" bg-white rounded shadow-sm p-4 d-flex flex-column justify-content-center align-items-center mb-1 option-card">
                            <img src="resources/imgs/inserir.png" alt="Projeções" class="icone-card"/>


                            <div class="d-flex flex-column row-gap-2">
                                <h4 class="fw-bold text-azul-banco text-center" style="font-size: 20px; margin: 1px">Inserir dados 4010</h4>
                                <p class="text-center text-cinza" style="font-size: 14px; margin: 0px">Adicione o arquivo da planilha EXCEL 4010</p>
                                <h5 class="text-azul-banco text-center" style="font-size: 14px; margin: 0px">Ver mais <i class="bi bi-arrow-right"></i></h5>
                            </div>
                        </div>
                    </a>
                    <a href="filtroConciliacao.jsp" class="d-block text-decoration-none">
                        <div style="width: 20vw; height: 30vh" class="bg-white rounded shadow-sm p-4 d-flex flex-column justify-content-center align-items-center mb-1 option-card">
                            <img src="resources/imgs/visualizarPTAX.png" alt="Propostas Orçamentárias" class="icone-card"/>


                            <div class="d-flex flex-column row-gap-2">
                                <h4 class="fw-bold text-azul-banco text-center" style="font-size: 20px; margin: 1px">Visualizar tabela Conciliação</h4>
                                <p class="text-center text-cinza" style="font-size: 14px; margin: 0px">Relatório da tabela Conciliação</p>
                                <h5 class="text-azul-banco text-center" style="font-size: 14px; margin: 0px">Ver mais <i class="bi bi-arrow-right"></i></h5>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </main>
    </body>
</html>