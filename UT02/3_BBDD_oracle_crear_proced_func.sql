CREATE OR REPLACE FUNCTION SUMAR (n1 NUMBER,n2 NUMBER) 
RETURN NUMBER AS
BEGIN
    RETURN n1 + n2;
END sumar;
/
//Para probarla escribimos: SELECT SUMAR(2,22) FROM DUAL;
SELECT SUMAR(2,22) FROM DUAL;

CREATE OR REPLACE PROCEDURE SUBIDA AS
BEGIN
    UPDATE EMPLEADOS SET SALARIO = SALARIO +100 WHERE DEPT_NO=30;
    COMMIT;
END SUBIDA;
/
//Para probarla escribimos: exec SUBIDA();

CREATE OR REPLACE PROCEDURE subida_sal(d NUMBER, subida NUMBER) AS
BEGIN
	UPDATE empleados 
	SET salario = salario + subida 
	WHERE dept_no = d;
	COMMIT;
END;
/

CREATE OR REPLACE FUNCTION nombre_dep(d NUMBER, locali OUT VARCHAR2) 
RETURN VARCHAR2 AS 
	nom VARCHAR2(15);
BEGIN
	SELECT dnombre, loc INTO nom, locali FROM departamentos
	WHERE dept_no = d;
	RETURN nom;
	
EXCEPTION
	WHEN NO_DATA_FOUND THEN
	nom := 'INEXISTENTE';
	RETURN nom;
END;