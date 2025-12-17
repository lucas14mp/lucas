package br.com.bb.cbe.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Map;

public class NumeroUtils {

    /**
     * Esse método transforma um Double em uma String formatada
     * 
     * Formata um Double em uma String inserindo um ponto a cada
     * 3 caracteres e substituindo o ponto das casas decimais por
     * uma virgula.
     * ex: 1234567.89 -> 1.234.567,89
     * 
     * @param num numero do tipo Double
     * @return string formatada
     */
    public static String doubleToString(Double num) {
        String numeroFormatado = String.format("%.2f", num);
        String regex = "(?<=\\d)(?=(\\d{3})+(?!\\d))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numeroFormatado);
        return matcher.replaceAll("\\.");
    }

    /**
     * Esse método transforma uma String em um Double
     * 
     * Recebe uma String numérica formatada (ex: '1.234.567,89'),
     * remove os pontos, substitui a virgula por um ponto e
     * transforma em um objeto do tipo Double 
     * @param numString String no formato 1.234.567,89 ou 1234567.89 ou 1234567,89
     * @return numero passado, mas no tipo Double
     */
    public static Double stringToDouble(String numString) {
        if (numString.isEmpty()) {
            return (-0.01);
        }
        String stringFormatada = numString.trim();
        if (stringFormatada.contains("%")){
            stringFormatada = stringFormatada.replace("%", "");
        }
        if (numString.contains(",")) {
            String stringSemPonto = stringFormatada.replaceAll("\\.", "");
            stringFormatada = stringSemPonto.replaceAll(",", ".");
        }
        Double numero = Double.parseDouble(stringFormatada);
        return numero;
    }
    
    /**
     * Esse método transforma uma String em um int
     *
     * Recebe uma String numérica formatada (ex: '1.234.567'), remove os pontos e transforma em um objeto do tipo int
     *
     * @param numString String no formato 1.234.567 ou 1234567
     * @return número passado, mas no tipo int
     */
    public static int stringParaInt(String numString) {
        if (numString.isEmpty()) {
            return -1;
        }
        String stringFormatada = numString.trim();
        String stringSemPonto = stringFormatada.replaceAll("\\.", "");
        return Integer.parseInt(stringSemPonto);
    }
    
    public static double formatAndConvertToFloat(String numero) throws ParseException {
        if (numero == null || numero.isEmpty()) {
            throw new IllegalArgumentException("O número fornecido é inválido.");
        }

        if ("-".equals(numero)){
            System.out.println("TRAÇO");
            numero = "0";            
            return NumeroUtils.stringToDouble(numero);
        }
        else if("0".equals(numero)){
            System.out.println("ZERO");
            numero = "0";            
            return NumeroUtils.stringToDouble(numero);
        }
        
       // Verifica qual padrão está sendo usado
        boolean usaPontoComoMilhar = numero.contains(".") && numero.lastIndexOf(".") < numero.lastIndexOf(",");
        boolean usaVirgulaComoMilhar = numero.contains(",") && numero.lastIndexOf(",") < numero.lastIndexOf(".");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();

        if (usaPontoComoMilhar) {
            // Formato: 1.123.324,12 (ponto para milhar e vírgula para decimal)
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');
        } else if (usaVirgulaComoMilhar) {
            // Formato: 1,123,324.12 (vírgula para milhar e ponto para decimal)
            symbols.setGroupingSeparator(',');
            symbols.setDecimalSeparator('.');
        } else if (numero.contains(",")) {
            // Caso sem separadores de milhar, mas com vírgula como decimal
            symbols.setDecimalSeparator(',');
        } else if (numero.contains(".")) {
            // Caso sem separadores de milhar, mas com ponto como decimal
            symbols.setDecimalSeparator('.');
        } else {
            throw new IllegalArgumentException("Formato de número não reconhecido: " + numero);
        }

        // Configura o DecimalFormat com os símbolos apropriados
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(symbols);
        decimalFormat.setParseBigDecimal(true);

        // Converte o número para BigDecimal e depois para Double
        return decimalFormat.parse(numero).doubleValue();

    }
    
        public static String verificarString(String entrada) {
        // Verifica se a string contém o padrão "Dólar -"
        if (entrada != null && entrada.contains("Dólar  -")) {
            return "Dólar americano";
        }
        // Retorna a string original caso não contenha o padrão
        return entrada;
    }
        
    public static String removerParenteses(String entrada) {
        if (entrada == null || entrada.isEmpty()) {
            throw new IllegalArgumentException("A string de entrada não pode ser nula ou vazia.");
        }

        // Expressão regular para remover parênteses e o conteúdo dentro deles
        return entrada.replaceAll("\\(\\d+\\)", "").trim();
    }
    
    public static String removerNumParenteses(String entrada) {
        if (entrada == null || entrada.isEmpty()) {
            entrada = "0"; // Define um valor padrão se a entrada for nula ou vazia
        }
        entrada = entrada.trim();
        
        if (entrada.startsWith("(") && entrada.endsWith(")")) {
            System.out.println("ENTROU nos parênteses");
            // Remove os parênteses e adiciona o sinal de menos
            return "-" + entrada.substring(1, entrada.length() - 1);
        }

        System.out.println("ENTRADA original: " + entrada);
        // Remove qualquer parêntese diretamente
        return entrada.replaceAll("[()]", "").trim();
    }
    





    public static double converterParaNumero(String porcentagem) {
        if (porcentagem == null || porcentagem.isEmpty()) {
            throw new IllegalArgumentException("A string da porcentagem não pode ser nula ou vazia.");
        }

        // Remove o símbolo de porcentagem (%) e converte a String para número
        porcentagem = porcentagem.trim().replace("%", "");

        try {
            // Converte para decimal dividindo por 100
            return Double.parseDouble(porcentagem) / 100;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato inválido para porcentagem: " + porcentagem);
        }
    }
    
    public static double converterFloatParaDouble(float valorFloat) {
        // Conversão implícita de float para double
        return (double) valorFloat;
    }
    

    public static String extrairConteudoParenteses(String entrada) {
            if (entrada == null || entrada.isEmpty()) {
                throw new IllegalArgumentException("A string de entrada não pode ser nula ou vazia.");
            }

            // Expressão regular para capturar o conteúdo dentro dos parênteses
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\((.*?)\\)");
            java.util.regex.Matcher matcher = pattern.matcher(entrada);

            // Retorna o conteúdo encontrado
            if (matcher.find()) {
                return matcher.group(1); // Captura o conteúdo dentro dos parênteses
            } else {
                return null; // Retorna null se nenhum parêntese for encontrado
            }
        }

public static double extrairValorCompra(Map<String, Object> map) {
    Object obCompra = map.get("compra");

    if (obCompra == null) {
        return 0.0;
    }

    String numeroCP = obCompra.toString().trim();

    // Remove parênteses e outros símbolos
    numeroCP = NumeroUtils.removerNumParenteses(numeroCP);

    // Remove símbolos como R$, %, etc.
    numeroCP = numeroCP.replaceAll("[R$%]", "");

    // Remove separadores de milhar e ajusta decimal
    numeroCP = numeroCP.replaceAll("\\.", "").replace(",", ".");

    // Remove qualquer outro caractere não numérico (exceto ponto e sinal de menos)
    numeroCP = numeroCP.replaceAll("[^0-9.-Ee]", "");

    try {
        return Double.parseDouble(numeroCP);
    } catch (NumberFormatException e) {
        System.out.println("Erro ao converter valor de compra: " + numeroCP);
        return 0.0;
    }
}


    
}