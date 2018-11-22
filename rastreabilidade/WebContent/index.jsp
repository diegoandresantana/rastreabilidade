<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
<%
if (request.getParameter("usuario") == null && request.getParameter("codAudit") == null) {
%>
	 var jane = window.open( "${pageContext.request.contextPath}/zk/index?sistema=40","jane01","location=no,menubar=no,titlebar=yes,toolbar=no,heigth=540,width=780,scrollbars=auto,status=yes,resizable=yes");
	 if(jane != null) {
	  jane.moveTo(0, 0);
	  jane.resizeTo(window.screen.availWidth, window.screen.availHeight);
	  jane.focus();
	 } else alert("Verifique a permissão para abertura de PopUp");
<%
}else response.sendRedirect(request.getContextPath() + "/zk/index?sistema=40" +
		                                               "&usuario=" + request.getParameter("usuario") +
		                                               "&codAudit=" + request.getParameter("codAudit")); 
%>
</script>
