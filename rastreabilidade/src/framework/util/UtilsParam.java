package framework.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class UtilsParam {


		private static ResourceBundle resource = ResourceBundle.getBundle(
				"conexaozk", Locale.getDefault());

		public static String getValuePropeties(String key) {
			return resource.getString(key);
		}
}
