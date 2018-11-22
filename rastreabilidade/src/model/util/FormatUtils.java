package model.util;

/**
 * 
 */

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonides Fernando
 *
 * @since 19/12/2006
 */
public class FormatUtils {

	private static Map<Integer, String> months = new Hashtable<Integer, String>();
	
	static{
		months.put(new Integer(1), "Janeiro");
		months.put(new Integer(2), "Fevereiro");
		months.put(new Integer(3), "Março");
		months.put(new Integer(4), "Abril");
		months.put(new Integer(5), "Maio");
		months.put(new Integer(6), "Junho");
		months.put(new Integer(7), "Julho");
		months.put(new Integer(8), "Agosto");
		months.put(new Integer(9), "Setembro");
		months.put(new Integer(10), "Outubro");
		months.put(new Integer(11), "Novembro");
		months.put(new Integer(12), "Dezembro");
	}
	
	private static final Locale BRAZIL = new Locale("pt","BR");  
    /** 
     * Sï¿½mbolos especificos do Real Brasileiro 
     */  
	private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRAZIL);  
    /** 
     * Mascara de dinheiro para Real Brasileiro 
     */  
	public static final DecimalFormat DINHEIRO_REAL = new DecimalFormat("¤ ###,###,##0.00",REAL); 

	public static String mascaraDinheiro(double valor, DecimalFormat moeda){  
		return moeda.format(valor);  
	} 
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static NumberFormat getDoubleFormat(HttpServletRequest request){
		
		NumberFormat nf = NumberFormat.getInstance(new Locale(Locale.ENGLISH.getLanguage(), Locale.ENGLISH.getCountry()));
    	nf.setMinimumFractionDigits(2);
    	nf.setGroupingUsed(false);
    	return nf;
	}
	
	
	/**
	 * 
	 * @param date - data a partir da qual sera extarido o nome do mes
	 * @return - nome do mes em que a data passada como parametro foi criada
	 */
	public static String getMonthName(java.util.Date date){
		
		return (String) months.get(Integer.valueOf(getMonth(date)));
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static DateFormat getHourFormat(HttpServletRequest request) {
        return new SimpleDateFormat("HH:mm");
    }
	
	/**
	 * retorna o dia da data passada como parametro para o método
	 * @param value o dia extraido do Timestamp passado como parametro
	 * @return String que representa o dia passado como parametro.
	 */
	public static String getDay(java.util.Date value){
				
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(value);
	    
	    int day = calendar.get(Calendar.DAY_OF_MONTH);
	    return fillLeft(String.valueOf(day), '0', 2);
	}
	
	/**
	  * metodo que extrai o mes de uma data Timestamp passada como parametro
	  * @param value a data no formato Timestamp de onde se pretende extrair o mes
	  * @return String que representa o mes extraido da data passada como parametro.
	  */
	public static String getMonth(java.util.Date value){
		
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(value);
	    int month = calendar.get(Calendar.MONTH);
	    return fillLeft(String.valueOf(month + 1), '0', 2);
	}

	/**
	  * metodo que extrai o ano de uma data Timestamp passada como parâmetro.
	  * @param value a data no formato Timestamp de onde se pretende extrair o ano
	  * @return String que representa o ano extraido da data Timestamp passada como parametro.
	  */
	public static String getYear(java.util.Date value){
		
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(value);
	    int year = calendar.get(Calendar.YEAR);
	    return fillLeft(String.valueOf(year), '0', 2);
	}
	
	/**
	  * Método para retornar uma String formada pela String fornecida e preenchida
	  * à esquerda pelo char até completar a quantidade posições fornecida. <br>
	  * Ex. fillLeft("casa",'0',10) = "000000casa"
	  * @param string String a ser preenchida
	  * @param caracter a ser utilizado no preenchimento
	  * @param length comprimento final da string preenchida
	  * @return Str
	  */
	public static String fillLeft(String string, char chr, int length){
		
	    StringBuffer sb = new StringBuffer(string);
	    if (string.length() < length){
	      while (sb.length() < length){
	        sb.insert(0, chr);
	      }
	    }
	    return sb.toString();
	}
	
	/**
	 * 
	 * @param date - data a ser convertida para string
	 * @return - uma string com formato de data
	 */
	public static String dateToString(java.util.Date date) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return simpleDateFormat.format(date);
	}

	/**
	 * 
	 * @param string - a ser convertida para data
	 * @return - objeto Date com a data presente na String ou null caso a data nao seja valida 
	 * @throws ParseException
	 */
	public static java.util.Date stringToDate(String string) {

		SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
		try {
			string = string.replace('/', '-');
			return s.parse(string);
		} catch (ParseException e) {
//			data invalida
			return null;
		}
	}
	//
	public static String formataData(String de, String para, String data){
		SimpleDateFormat f = new SimpleDateFormat(de);
		SimpleDateFormat format = new SimpleDateFormat(para);
		Date d = new Date();
		String result = " ";		
		if(data != null && !data.trim().equals("")){			
			try {		
				d = f.parse(data);
				result = format.format(d);
			} 
			catch (ParseException e) {		
				e.printStackTrace();
			}
		}		
		return result;  
	}
	
	/**
	 * Retirando caracteres Especiais
	 * @param String com os caracteres
	 * @return
	 */
   public static String formatString(String s) {  
	       String temp = Normalizer.normalize(s, java.text.Normalizer.Form.NFD);  
	       return temp.replaceAll("[^\\p{ASCII}]","");  
   }
	   
}