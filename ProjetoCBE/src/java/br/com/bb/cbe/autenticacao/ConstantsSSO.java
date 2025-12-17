package br.com.bb.cbe.autenticacao;

public class ConstantsSSO {
    public static final String SCOPE = "bbprofile";
    public static final String RESPONSE_TYPE = "code";
    public static final String REDIRECT_URI = "http://pxw0hosp0084:8080/ProjetoCBE/login";
    public static final String AMBIENTE = "intranet";
    public static final String CLIENT_ID = "dirco";
    public static final String CLIENT_ID_BASE64 = "ZGlyY286UHJvZHVjMTIz";
    public static final String URL_LOGIN = "https://login." + AMBIENTE + ".bb.com.br/sso/oauth2/authorize?scope=" + SCOPE + "&response_type=" + RESPONSE_TYPE + "&redirect_uri=" + REDIRECT_URI + "&client_id=" + CLIENT_ID;
    public static final String URL_ACCESS_TOKEN = "https://login." + AMBIENTE + ".bb.com.br/sso/oauth2/access_token";
    public static final String URL_USER_INFO = "https://login." + AMBIENTE + ".bb.com.br/sso/oauth2/userinfo";
    public static final String GRANT_TYPE = "authorization_code";
}