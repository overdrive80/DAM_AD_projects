<html>
    <head>        
        <title>INSERCI&Oacute;N DE DEPARTAMENTOS</title>
    </head>
    <body>
        <br>
        <h1 align="center">INSERCI&Oacute;N DE DEPARTAMENTO</h1>
        <%
            String mensaje = (String) request.getAttribute("mensaje");
        %>
        <p align="center"><font color="red">
                <%=mensaje%></font></p>
        <p align='center'>
            <a href="/WebApplication2/Controlador?accion=alta">Alta de Departamento</a></p>  
    <p align="center">
        <a href="index.html">Inicio</a> </p>
</body>
</html>