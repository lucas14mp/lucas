package br.com.bb.cbe.Utils;

import br.com.bb.cbe.DAO.PtaxDAO;
import br.com.bb.cbe.DAO.Ficha01DAO;
import br.com.bb.cbe.DAO.Ficha03DAO;
import br.com.bb.cbe.DAO.Ficha08DAO;
import br.com.bb.cbe.DAO.Ficha09DAO;
import br.com.bb.cbe.DAO.Ficha11ControleDAO;
import br.com.bb.cbe.DAO.Ficha11MaiorDAO;
import br.com.bb.cbe.DAO.Ficha11MenorDAO;
import br.com.bb.cbe.DAO.Ficha16DAO;
import br.com.bb.cbe.DAO.Ficha18DAO;
import br.com.bb.cbe.Bean.Ficha01;
import br.com.bb.cbe.Bean.Ficha03;
import br.com.bb.cbe.Bean.Ficha08;
import br.com.bb.cbe.Bean.Ficha09;
import br.com.bb.cbe.Bean.Ficha11Controle;
import br.com.bb.cbe.Bean.Ficha11Maior;
import br.com.bb.cbe.Bean.Ficha11Menor;
import br.com.bb.cbe.Bean.Ficha16;
import br.com.bb.cbe.Bean.Ficha18;
import java.util.List;

public class ConsolidadoUtils {

    // CORREÇÃO: Usamos o id_moeda (PK) = 1, e não o id_csv (220)
    private static final int ID_MOEDA_DOLAR = 1; 

    public double getValorTotalEmDolar(String fichaNome, int trimestre, int ano) {
        double totalBrl = 0.0;
        
        int triPtax = trimestre; 
        int anoPtax = ano; 

        try {
            // 1. Busca a taxa do Dólar (USD -> BRL) usando o ID 1
            double taxaUsdParaBrl = PtaxDAO.getTaxaCompra(ID_MOEDA_DOLAR, triPtax, anoPtax);
            
            // LOG PARA CONFERENCIA
            if (fichaNome.equals("ficha01")) { 
                System.out.println(">>> DEBUG CONVERSAO: ID Dolar usado = " + ID_MOEDA_DOLAR);
                System.out.println(">>> TAXA ENCONTRADA: " + taxaUsdParaBrl);
            }

            if (taxaUsdParaBrl <= 0) return 0.0;

            // 2. Soma tudo convertendo para BRL
            if (fichaNome.equals("ficha01")) {
                List<Ficha01> lista = Ficha01DAO.getAllFichasByTrimestreAno(trimestre, ano);
                for (Ficha01 f : lista) {
                    double taxa = PtaxDAO.getTaxaCompra(f.getMoeda().getId(), triPtax, anoPtax);
                    totalBrl += (f.getValorDatabase() * taxa);
                }
            }
            else if (fichaNome.equals("ficha03")) {
                List<Ficha03> lista = Ficha03DAO.getAllFichasByTrimestreAno(trimestre, ano);
                for (Ficha03 f : lista) {
                    double taxa = PtaxDAO.getTaxaCompra(f.getMoeda().getId(), triPtax, anoPtax);
                    totalBrl += (f.getValorDatabase() * taxa);
                }
            }
            else if (fichaNome.equals("ficha08")) {
                List<Ficha08> lista = Ficha08DAO.getAllFichasByTrimestreAno(trimestre, ano);
                for (Ficha08 f : lista) {
                    double taxa = PtaxDAO.getTaxaCompra(f.getMoeda().getId(), triPtax, anoPtax);
                    totalBrl += (f.getSaldoDatabase() * taxa);
                }
            }
            else if (fichaNome.equals("ficha09")) {
                List<Ficha09> lista = Ficha09DAO.getAllFichasByTrimestreAno(trimestre, ano);
                for (Ficha09 f : lista) {
                    double taxa = PtaxDAO.getTaxaCompra(f.getMoeda().getId(), triPtax, anoPtax);
                    totalBrl += (f.getValorDatabase() * taxa);
                }
            }
            else if (fichaNome.equals("ficha11")) {
                List<Ficha11Menor> listaMenor = Ficha11MenorDAO.getAllFichasByTrimestreAno(trimestre, ano);
                for (Ficha11Menor f : listaMenor) {
                    double taxa = PtaxDAO.getTaxaCompra(f.getMoeda().getId(), triPtax, anoPtax);
                    totalBrl += (f.getValorParticipacao() * taxa);
                }
                List<Ficha11Maior> listaMaior = Ficha11MaiorDAO.getAllFichasByTrimestreAno(trimestre, ano);
                for (Ficha11Maior f : listaMaior) {
                    double taxa = PtaxDAO.getTaxaCompra(f.getMoeda().getId(), triPtax, anoPtax);
                    // Ajuste conforme sua regra de negócio para valor proporcional
                    double valor = f.getValorEmpresa() * (f.getPorcentoParticipacaoCapital() / 100.0);
                    totalBrl += (valor * taxa);
                }
            }
            else if (fichaNome.equals("ficha16")) {
                List<Ficha16> lista = Ficha16DAO.getAllFichasByTrimestreAno(trimestre, ano);
                for (Ficha16 f : lista) {
                    double taxa = PtaxDAO.getTaxaCompra(f.getMoeda().getId(), triPtax, anoPtax);
                    totalBrl += (f.getValorDatabase() * taxa);
                }
            }
            else if (fichaNome.equals("ficha18")) {
                List<Ficha18> lista = Ficha18DAO.getAllFichasByTrimestreAno(trimestre, ano);
                for (Ficha18 f : lista) {
                    double taxa = PtaxDAO.getTaxaCompra(f.getMoeda().getId(), triPtax, anoPtax);
                    totalBrl += (f.getValorMercado() * taxa);
                }
            }

            // 3. Conversão Final: Reais / Taxa Dolar
            return totalBrl / taxaUsdParaBrl;

        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}