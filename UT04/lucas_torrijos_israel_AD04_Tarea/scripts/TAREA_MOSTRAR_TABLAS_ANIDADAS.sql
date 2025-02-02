--Mostramos el contenido PATROCINA
--MODELO OBJETO
  select t.codigoproyecto, pa.codpatrocinio
  from tbl_proyectos t, table(t.patrocina) pa 
  order by t.codigoproyecto, pa.codpatrocinio;
  
--MODELO RELACIONAL
  select pro.codigoproyecto, pa.codpatrocinio
  from proyectos pro inner join patrocina pa on pro.codigoproyecto = pa.codigoproyecto
  order by pro.codigoproyecto, pa.codpatrocinio;
/

--Mostramos el contenido USA
--MODELO OBJETO
  select t.codigoproyecto, usa.coduso
  from tbl_proyectos t, table(t.usa) usa 
  order by t.codigoproyecto, usa.coduso;
  
--MODELO RELACIONAL
  select pro.codigoproyecto, us.coduso
  from proyectos pro inner join usa us on pro.codigoproyecto = us.codigoproyecto
  order by pro.codigoproyecto, us.coduso;
/

--Mostramos el contenido PARTICIPA
--MODELO OBJETO
  select t.codigoproyecto, par.codparticipacion
  from tbl_proyectos t, table(t.participa) par 
  order by t.codigoproyecto, par.codparticipacion;

--MODELO RELACIONAL
  select pro.codigoproyecto, par.codparticipacion
  from proyectos pro inner join participa par on pro.codigoproyecto = par.codigoproyecto
  order by pro.codigoproyecto, par.codparticipacion;
/
