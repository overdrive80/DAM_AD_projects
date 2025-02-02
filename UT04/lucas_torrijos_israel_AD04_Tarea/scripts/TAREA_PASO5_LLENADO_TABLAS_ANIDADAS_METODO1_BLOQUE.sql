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
    
    /* INSERTAMOS EL CONTENIDO DE LAS TABLAS ANIDADAS */
    INSERT INTO TABLE (SELECT PATROCINA FROM TBL_PROYECTOS tbl_p WHERE tbl_p.codigoproyecto = numProyecto) 
      (SELECT pa.codpatrocinio, REF(e), pa.importeaportacion, pa.fechaaportacion 
        FROM PATROCINA pa, tbl_entidades e
        WHERE pa.codentidad = e.codentidad 
        AND pa.codigoproyecto = numProyecto);
   
     INSERT INTO TABLE (SELECT USA FROM TBL_PROYECTOS tbl_p WHERE tbl_p.codigoproyecto = numProyecto) 
      (SELECT usa.coduso, REF(r), usa.cantidad, usa.fechaasignacion
        FROM USA usa, tbl_recursos r
        WHERE usa.codrecurso = r.codrecurso 
        AND usa.codigoproyecto = numProyecto);
  
    INSERT INTO TABLE (SELECT PARTICIPA FROM TBL_PROYECTOS tbl_p WHERE tbl_p.codigoproyecto = numProyecto) 
      (SELECT par.codparticipacion, ref(e), par.tipoparticipacion, par.numaportaciones
        FROM PARTICIPA par, tbl_ESTUDIANTES e
        WHERE par.codestudiante = e.codestudiante 
        AND par.codigoproyecto = numProyecto);
    
  end loop;
  

end;
/

