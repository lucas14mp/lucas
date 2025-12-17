package br.com.bb.cbe.autenticacao;

import br.com.bb.cbe.Bean.Funcionario;
import br.com.bb.cbe.controllers.DependenciaController;
import br.com.bb.cbe.controllers.FuncionarioController;
import java.io.IOException;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FuncionarioController funcionarioController;
        try {
            funcionarioController = new FuncionarioController();
            String code = req.getParameter("code"); // Pega o code que é passado via url
            String accessToken = getAccessToken(code); // Pega o acess_token
            String informacoesJSON = getInformacoesUsuarioJSON(accessToken); // pega as informacoes do usuario mandadas pela API
            Funcionario funcionario = instanciarObjetoFuncionario(informacoesJSON); // instancia o objeto funcionario
            if (!funcionarioController.funcionarioExiste(funcionario.getChave())) {
                funcionarioController.criarFuncionario(funcionario);
            }
            HttpSession session = req.getSession(); // Gera um session para o usuário
            session.setAttribute("chave", funcionario.getChave());
            int uorEquipe = Integer.parseInt(converterValorJsonParaString(informacoesJSON, "codigo_uor_equipe")); // cria um session com o codigo da equipe do funcionario
            session.setAttribute("uorEquipe", uorEquipe);
            String nomeComissao = converterValorJsonParaString(informacoesJSON, "nome_comissao");
            //botao validar nao estava aparecendo para um gerente, esses sysout sao para testar
            System.out.println(funcionario.getChave());
            System.out.println(uorEquipe);
            System.out.println(nomeComissao);
            session.setAttribute("nomeComissao", nomeComissao);
            resp.sendRedirect("/ProjetoCBE");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private String getAccessToken(String code) {
        OkHttpClient cliente = new OkHttpClient();
        String reqBodyString = "grant_type=" + ConstantsSSO.GRANT_TYPE
                + "&code=" + code
                + "&redirect_uri=" + ConstantsSSO.REDIRECT_URI;
//        RequestBody reqBody = RequestBody.create(reqBodyString, MediaType.parse("application/x-www-form-urlencoded"));
        Request request = new Request.Builder()
                .url(ConstantsSSO.URL_ACCESS_TOKEN)
                .addHeader("Authorization", "Basic " + ConstantsSSO.CLIENT_ID_BASE64)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .post(reqBody)
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
        OkHttpClient cliente = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ConstantsSSO.URL_USER_INFO)
                .addHeader("Authorization", "Bearer " + accessToken)
//                .post(RequestBody.create(new byte[0]))
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
        int dependenciaId = Integer.parseInt(converterValorJsonParaString(json, "prefixo_dependencia"));
        funcionario.setDependencia(dependenciaController.getDependenciaById(dependenciaId));
        return funcionario;
    }

    private String converterValorJsonParaString(String respBodyString, String chave) {
        JSONObject jsonObject = new JSONObject(respBodyString);
        return jsonObject.getString(chave);
    }
}