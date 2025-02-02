create or replace procedure existePatrocinador (codpatrocinador number, codproyecto number, existe out boolean) is

  CUENTA NUMBER:=0;
begin

  SELECT COUNT(*) INTO CUENTA
  FROM TABLE (SELECT p.patrocina FROM tbl_proyectos p WHERE p.codigoproyecto = codproyecto) pa
  where pa.codpatrocinio = codpatrocinador;

  if CUENTA > 0 then
    existe:=true;
    return;
  end if;
  
  existe:=false;
end;
/