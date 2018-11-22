package framework.util;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Servlet implementation class ServletRelatorio
 */
public class ServletRelatorio extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @return
	 * @throws JRException
	 * @see HttpServlet#HttpServlet()
	 */
	public static JasperPrint geraFillPrint(String caminho, HashMap params,
			Connection conexao, JasperPrint print) {
		try {
			print = JasperFillManager.fillReport(caminho, params, conexao);
		} catch (JRException e) {

			e.printStackTrace();
		}
		return print;

	}

	public ServletRelatorio() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String caminho = getServletContext().getRealPath("/relatorio")+ "/";

		response.setContentType("application/pdf");
		
		HashMap<String, String> params=new HashMap<String, String>();
		HashMap<String, String> url;
		
		url=(HashMap<String, String>) request.getParameterMap();
	
		for (Object key : url.keySet()) {
			params.put(key.toString(),request.getParameter(key.toString())); 
		}
		// JRDataSource jrds = new JRBeanCollectionDataSource(listRelDepto);
	
		Connection conexao = Conexao.getConexao();
		params.put("SUBREPORT_DIR", caminho);
		JasperPrint print = null;
		String nomerelatorio=params.get("report");
		params.remove("report");
		print = geraFillPrint(caminho + nomerelatorio+".jasper", params,
					conexao, print);

			byte[] bytes = null;
			try {
				bytes = JasperExportManager.exportReportToPdf(print);

			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.getOutputStream().write(bytes);
		}

	
}
