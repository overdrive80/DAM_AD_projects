<%-- 
    Document   : HolaMundo
    Created on : 7 abr 2024, 11:39:52
    Author     : Isra
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>¡Hola mundo! La fecha del sistema es: <%= new java.util.Date()%></h1>

        <div>
            <h3>Ejemplo del tipo de expresiones admitidas en JSP:</h3>

            Conversión a mayúsculas:<br>
            &nbsp;&nbsp;El texto "isra" será: <%= new String("isra").toUpperCase()%>
            <br><br>
            Operaciones aritméticas:<br>
            &nbsp;&nbsp;La suma de 10 más 4 es: <%= 10 + 4%>
            <br><br>
            Operaciones booleanas:<br>
            &nbsp;&nbsp;El valor de 10 es menor de 100: <%= 10 < 100%>

        </div>

        <div>
            <h3>Ejemplo de scriptlets JSP:</h3>
            <% 
                for (int i = 0; i<10; i++){
                    out.print(i + "&lt;");
                }
                out.print("10");
            %>

        </div>
        <div>
            <h3>Ejemplo de declaraciones JSP (variables y métodos):</h3>
            <%!
                private int resultado;

                public int metodoSuma(int num1, int num2){
                    resultado = num1 + num2;
                    return resultado;
                }

                public int metodoResta(int num1, int num2){
                    resultado = num1 - num2;
                    return resultado;
                }
            
            %>
            
            Llamada al método metodoSuma(7,5): <%= metodoSuma(7,5) %>

        </div>

    </body>
</html>
