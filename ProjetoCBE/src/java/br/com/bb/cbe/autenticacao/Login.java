package br.com.bb.cbe.autenticacao;

import br.com.bb.cbe.Bean.Funcionario;
import br.com.bb.cbe.controllers.DependenciaController;
import br.com.bb.cbe.controllers.FuncionarioController;
import java.io.IOException;
import java.net.ProxySelector; // Importante para ambiente corporativo
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import okhttp3.*;
import org.json.JSONObject;

@WebServlet("/login")
public class Login extends HttpServlet {

    // Configura o cliente para usar o Proxy do sistema automaticamente
    private final OkHttpClient cliente = new OkHttpClient.Builder()
            .proxySelector(ProxySelector.getDefault())
            .build();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            FuncionarioController funcionarioController = new FuncionarioController();
            String code = req.getParameter("code");

            if (code == null || code.isEmpty()) {
                System.out.println("ERRO: Code não recebido.");
                return;
            }

            String accessToken = getAccessToken(code);
            
            // Se falhar ao pegar token, não tente ler usuario
            if (accessToken == null || accessToken.isEmpty()) {
                System.out.println("ERRO: Token vazio ou falha de conexão.");
                return;
            }

            String informacoesJSON = getInformacoesUsuarioJSON(accessToken);
            
            // Se o JSON vier vazio, é aqui que dava o erro JSONException
            if (informacoesJSON == null || !informacoesJSON.trim().startsWith("{")) {
                System.out.println("ERRO: JSON inválido recebido: " + informacoesJSON);
                return;
            }

            Funcionario funcionario = instanciarObjetoFuncionario(informacoesJSON);
            
            if (!funcionarioController.funcionarioExiste(funcionario.getChave())) {
                funcionarioController.criarFuncionario(funcionario);
            }
            
            HttpSession session = req.getSession();
            session.setAttribute("chave", funcionario.getChave());
            
            String uorStr = converterValorJsonParaString(informacoesJSON, "codigo_uor_equipe");
            try {
                session.setAttribute("uorEquipe", Integer.parseInt(uorStr));
            } catch (NumberFormatException e) {
                session.setAttribute("uorEquipe", 0);
            }
            System.out.println(informacoesJSON);
            
            
            String nomeComissao = converterValorJsonParaString(informacoesJSON, "nome_comissao");
            System.out.println(funcionario.getChave());
            System.out.println(nomeComissao);
            session.setAttribute("nomeComissao", nomeComissao);
            
            resp.sendRedirect("/ProjetoCBE"); // Ou /ProjetoCBE dependendo do contexto
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getAccessToken(String code) {
        String reqBodyString = "grant_type=" + ConstantsSSO.GRANT_TYPE
                + "&code=" + code
                + "&redirect_uri=" + ConstantsSSO.REDIRECT_URI;
        
        // CORREÇÃO CRÍTICA PARA OKHTTP 3: MediaType vem PRIMEIRO
        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded"),
                reqBodyString
        );

        Request request = new Request.Builder()
                .url(ConstantsSSO.URL_ACCESS_TOKEN)
                .addHeader("Authorization", "Basic " + ConstantsSSO.CLIENT_ID_BASE64)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(reqBody) // ISTO ESTAVA COMENTADO!
                .build();
        try {
            Response response = cliente.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return converterValorJsonParaString(responseBody, "access_token");
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getInformacoesUsuarioJSON(String accessToken) {
        String informacoesJSON = "";
        
        // CORREÇÃO: Criar corpo vazio com tipo nulo (compatível OkHttp 3)
        RequestBody corpoVazio = RequestBody.create(null, new byte[0]);

        Request request = new Request.Builder()
                .url(ConstantsSSO.URL_USER_INFO)
                .addHeader("Authorization", "Bearer " + accessToken)
                .post(corpoVazio) // ISTO ESTAVA COMENTADO!
                .build();
        try {
            Response response = cliente.newCall(request).execute();
            if (response.isSuccessful()) {
                informacoesJSON = response.body().string();
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return informacoesJSON;
    }

    private Funcionario instanciarObjetoFuncionario(String json) {
        DependenciaController dependenciaController = new DependenciaController();
        Funcionario funcionario = new Funcionario();
        funcionario.setChave(converterValorJsonParaString(json, "chave_usuario"));
        funcionario.setNome(converterValorJsonParaString(json, "nome_usuario"));
        
        String depStr = converterValorJsonParaString(json, "prefixo_dependencia");
        try {
            int dependenciaId = Integer.parseInt(depStr);
            funcionario.setDependencia(dependenciaController.getDependenciaById(dependenciaId));
        } catch (Exception e) {
            System.out.println("Erro parsing dependencia: " + depStr);
        }
        return funcionario;
    }

    private String converterValorJsonParaString(String respBodyString, String chave) {
        if (respBodyString == null || respBodyString.isEmpty()) return "";
        try {
            JSONObject jsonObject = new JSONObject(respBodyString);
            return jsonObject.has(chave) ? jsonObject.getString(chave) : "";
        } catch (Exception e) {
            return "";
        }
    }
}