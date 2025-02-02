create or replace procedure existeUso (codigouso number, codproyecto number, existe out boolean) is

  CUENTA NUMBER:=0;
begin

  SELECT COUNT(*) INTO CUENTA
  FROM TABLE (SELECT usa FROM tbl_proyectos p WHERE p.codigoproyecto = codproyecto) us
  where us.coduso = codigouso;

  if CUENTA > 0 then
    existe:=true;
    return;
  end if;
  
  existe:=false;
end existeUso;
/