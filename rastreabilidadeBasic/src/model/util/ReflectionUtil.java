package model.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {
	public static List<Field> getAllFields(Class<?> clazz, List<Field> lisfields) {
		if (lisfields == null) {
			lisfields = new ArrayList<Field>();
		}
		Field[] arrFields = clazz.getDeclaredFields();
		for (Field f : arrFields) {
			lisfields.add(f);
		}
		clazz = clazz.getSuperclass();
		if (clazz != null) {
			// Chamando recursivamente o metodo agora com o nome da SuperClasse
			getAllFields(clazz, lisfields);
		}

		return lisfields;

	}


	public static String capitalizeFirst(String s) {
		char[] chars = s.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		s = new String(chars);
		return s;
	}

}
