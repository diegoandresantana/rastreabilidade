package framework.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	private static Connection con;

	public static Connection getConexao( ) {
		 String DRIVER = UtilsParam.getValuePropeties("DRIVER").trim();
		 String URL = UtilsParam.getValuePropeties("URL").trim();
		 String LOGIN = UtilsParam.getValuePropeties("LOGIN").trim();
		 String SENHA = UtilsParam.getValuePropeties("SENHA").trim();
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, LOGIN, SENHA);
			System.out.print("conexao ok");
		} catch (ClassNotFoundException e) {
			System.out.print("Driver não encontrado: " + e.getMessage());
		} catch (SQLException e) {
			System.out.print("Erro abrindo conexão: " + e.getMessage());
		}
		return con;
	}
}