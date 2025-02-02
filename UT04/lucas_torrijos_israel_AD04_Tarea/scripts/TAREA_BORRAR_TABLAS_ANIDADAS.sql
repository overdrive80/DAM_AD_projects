SET SERVEROUTPUT ON;

declare
  cursor proyectos is select * from tbl_proyectos;
  numProyecto number;
begin
  for fila in proyectos loop
    numProyecto:= fila.codigoproyecto;
    
    /* BORRAMOS EL CONTENIDO PREVIO DE LAS TABLAS ANIDADAS */
    delete from table (
      SELECT tbl_p.PATROCINA
      FROM tbl_proyectos tbl_p
      WHERE tbl_p.codigoproyecto = numProyecto); 
      
    delete from table (
      SELECT tbl_p.USA
      FROM tbl_proyectos tbl_p
      WHERE tbl_p.codigoproyecto = numProyecto); 
      
    delete from table (
      SELECT tbl_p.PARTICIPA
      FROM tbl_proyectos tbl_p
      WHERE tbl_p.codigoproyecto = numProyecto); 
    
  end loop;
  

end;
/

