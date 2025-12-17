package br.com.bb.cbe.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DataUtils {
    
    public static int validaTrimestre() {
        
    Calendar calendar = Calendar.getInstance();
    int mes = calendar.get(GregorianCalendar.MONTH);
                
    if(mes < 3){
        return 1;   
    }
    if(mes < 6){
        return 2;
    }
    if(mes < 9){
        return 3;
    }
    return 4;
    }
 
    public static String formatarData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }
     
}
