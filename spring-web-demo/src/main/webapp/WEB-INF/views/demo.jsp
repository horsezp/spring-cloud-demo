<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>here you see Leo's demo</h1>
	</br>
	WEB-INF是Java的WEB应用的安全目录。所谓安全就是客户端无法访问，只有服务端可以访问的目录。
	如果想在页面中直接访问其中的文件，必须通过web.xml文件对要访问的文件进行相应映射才能访问。
	</br>
	想要设置成UTF-8 出了增加配置 页面的编码也是需要UTF-8的
	spring.http.encoding.force=true
    spring.http.encoding.charset=UTF-8
    spring.http.encoding.enabled=true
    server.tomcat.uri-encoding=UTF-8
    
    
</body>
</html>