package model.util;

import java.util.Calendar;
import java.util.Date;

public class DateMath {
	 
	    private DateMath(){} // classes utilitárias não são instanciáveis.
	 
	   private static Calendar referenceCalendar;
	     
	   public static void setReferenceCalendar(Calendar calendar){
	        referenceCalendar = calendar;
	   }
	     
	   public static Date today(){
	        return new Date();
	   }
	    
	   public static Date tomorrow(){
	        return nextDayAfter(today());
	   }
	    
	   public static Date yesterday(){
	        return nextDayBefore(today());
	   }
	 
	   public static Date nextDayAfter(Date date){
	        return daysAfter(date, 1);
	   }
	    
	   public static Date nextDayBefore(Date date){
	        return daysBefore(date, 1);
	   }
	   
	   public static Date daysAfter(Date date, int numberOfDays){
	        return calculateRelativeDateAfter(date, numberOfDays);
	   }
	    
	   public static Date daysBefore(Date date, int numberOfDays){
	        return calculateRelativeDateAfter(date, -numberOfDays);
	   }
	    
	   public boolean isBetween (Date date, Date start, Date end){
	        return date.compareTo(start) >=0 || date.compareTo(end) <=0;
	   }
	    
	   public Date min (Date a, Date b){
	        if (a.compareTo(b) > 0){
	            return b;
	        }
	         
	        return a;
	   }
	    
	   public Date max (Date a, Date b){
	        if (a.compareTo(b) < 0){
	            return b;
	        }
	         
	        return a;
	   }
	    
	   public static Date toDateOnly(Date date){
	        // ignora informação de horas
	        Calendar calendar = (Calendar) referenceCalendar.clone();
	        calendar.setTime(date);	         
	        calendar.clear(Calendar.HOUR_OF_DAY);
	        calendar.clear(Calendar.MINUTE);
	        calendar.clear(Calendar.SECOND);
	        calendar.clear(Calendar.MILLISECOND);	         
	        return calendar.getTime();
	   }
	    
	   /**
	   * Calcula o número de dias entre duas datas.
	   */
	   public static int daysBetween(Date start, Date end){
	    
	       if (start.compareTo(end) < 0){
	          throw new IllegalArgumentException("Data inicial tem que ser maior ou igual à final");
	        } else if (start.compareTo(end)==0){
	            return 0; // É o mesmo dia.
	        }
	         
	        Calendar calendar = (Calendar) referenceCalendar.clone();
	        calendar.setTime(toDateOnly(start));
	         
	        Date endDate = toDateOnly(end);
	         
	        int count =0;
	        while (calendar.getTime().compareTo(endDate)!=0){
	           calendar.add(Calendar.DATE, 1);
	          count++;
	        }
	         
	        return count;
	   }
	       /**
		    * Calcula o número meses entre duas datas.
		   */
		   public static  int monthBetween(Date start, Date end){
		    
		       if (start.compareTo(end) > 0){
		          throw new IllegalArgumentException("Data inicial tem que ser maior ou igual à final");
		        } else if (start.compareTo(end)==0){
		            return 0; // É o mesmo dia.
		        }
		         
		        Calendar calendar = (Calendar) referenceCalendar.clone();
		        calendar.setTime(toDateOnly(start));
		         
		        Date endDate = toDateOnly(end);
		         
		        int count =0;
		        while (calendar.getTime().compareTo(endDate)!=0){
		           calendar.add(Calendar.MONTH, 1);
		          count++;
		        }		         
		        return count;
		   } 
	    
	   private static Date calculateRelativeDateAfter(Date date, int numberOfDays){
	        if( numberOfDays ==0 ){
	            return date;
	        }
	         
	        Calendar calendar = (Calendar) referenceCalendar.clone();	         
	        calendar.setTime(date);
	        calendar.add(Calendar.DATE, numberOfDays);
	         
	        return calendar.getTime();
	   }
	    
}
