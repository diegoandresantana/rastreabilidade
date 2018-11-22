package framework.util.mascara;

import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class MascaraCpf implements TypeConverter  {
	
	  public Object coerceToBean(java.lang.Object val, Component comp) {
		  System.out.println("aqui");
	    if ("___.___.___-__".equals(val)) { 	
	    	
	       return val; //or TypeConverter.IGNORE <-- will not do the "save" at all
	    }	    	   	
	    	
	      
	    if (val==null) {
		       return "___.___.___-__"; //or TypeConverter.IGNORE <-- will not do the "save" at all
		    }
	    System.out.println("aqui22");
		MaskFormatter f = null;
		JFormattedTextField jt = null;
		 try {
		
				f = new MaskFormatter("###.###.###-##");
				f.setPlaceholderCharacter('_');
				jt = new JFormattedTextField(f);
				jt.setText(((String) val).trim());
			
			
			
		 } catch (ParseException e) {
			e.printStackTrace();
		}
		 System.out.println(""+jt.getText());
		 return jt != null ? jt.getText() : "";
	    	
	  }


	@Override
	public Object coerceToUi(Object val, Component comp) {
		 System.out.println("aqui2");
		  if ("___.___.___-__".equals(val)) {
		       return val; //or TypeConverter.IGNORE <-- will not do the "save" at all
		    }
		  
		    if (val==null) {
			       return "___.___.___-__"; //or TypeConverter.IGNORE <-- will not do the "save" at all
			    }
		  
			 return val;
		
	}

}
