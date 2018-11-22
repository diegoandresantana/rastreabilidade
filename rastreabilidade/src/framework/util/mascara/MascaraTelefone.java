package framework.util.mascara;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class MascaraTelefone implements TypeConverter  {
	
	  public Object coerceToBean(java.lang.Object val, Component comp) {
	    if ("(__) ____-____".equals(val)) {
	       return null; //or TypeConverter.IGNORE <-- will not do the "save" at all
	    }
	    if (val==null) {
		       return "(__) ____-____"; //or TypeConverter.IGNORE <-- will not do the "save" at all
		    }
	    return val;
	  }


	@Override
	public Object coerceToUi(Object val, Component comp) {
		  if ("(__) ____-____".equals(val)) {
		       return null; //or TypeConverter.IGNORE <-- will not do the "save" at all
		    }
		    if (val==null) {
			       return "(__) ____-____"; //or TypeConverter.IGNORE <-- will not do the "save" at all
			    }
		    return val;
		
	}

}
