create or replace procedure existeParticipa (codparticipa number, codproyecto number, existe out boolean) is

  CUENTA NUMBER:=0;
begin

  SELECT COUNT(*) INTO CUENTA
  FROM TABLE (SELECT p.participa FROM tbl_proyectos p WHERE p.codigoproyecto = codproyecto) par
  where par.codparticipacion = codparticipa;

  if CUENTA > 0 then
    existe:=true;
    return;
  end if;
  
  existe:=false;
end;
/