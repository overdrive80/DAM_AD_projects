SET SERVEROUTPUT ON;

declare
  cursor proyectos is select * from tbl_proyectos;
begin
  for fila in proyectos loop
    poblar_tabla_anidada_patrocina(fila.codigoproyecto);
    poblar_tabla_anidada_usa(fila.codigoproyecto);
    poblar_tabla_anidada_participa(fila.codigoproyecto);
  end loop;
  

end;
/

