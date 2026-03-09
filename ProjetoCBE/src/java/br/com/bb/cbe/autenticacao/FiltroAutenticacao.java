package br.com.bb.cbe.autenticacao;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FiltroAutenticacao implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        //comentar as duas linhas abaixo antes de commitar/subir no server MATRICULA FELIPE: t1092011 MATRICuLA GABRIEL: T1092407
        session.setAttribute("chave", "F0738318");
        session.setAttribute("uorEquipe", "283575");
        String chaveFuncionario = (String) session.getAttribute("chave");
        String uri = req.getRequestURI();
        if (chaveFuncionario != null) {
            chain.doFilter(request, response);
        } else if (uri.equals("/ProjetoCBE/login")) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(ConstantsSSO.URL_LOGIN);
        }
    }
}
