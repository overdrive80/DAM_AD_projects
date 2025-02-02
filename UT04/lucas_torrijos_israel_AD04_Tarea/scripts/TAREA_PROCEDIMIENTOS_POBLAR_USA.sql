CREATE OR REPLACE PROCEDURE POBLAR_TABLA_ANIDADA_USA (CODPROYECTO NUMBER) AS
   
    --Creacion cursor: asignamos al cursor los datos de la tabla RELACIONAL
    CURSOR c1 IS 
        SELECT us.coduso, us.codrecurso, us.cantidad, us.fechaasignacion
        FROM USA us
        WHERE us.CODIGOPROYECTO = CODPROYECTO;
        
    fila c1%ROWTYPE;
    recurso_ref REF fila_recurso;
    existe boolean:=false;
    filanueva fila_usa;
BEGIN
    -- Abrimos el cursor
    OPEN c1;
  
    LOOP
        -- Recuperamos una fila del cursor
        FETCH c1 INTO fila;
        
        --Control de salida del bucle
        EXIT WHEN c1%NOTFOUND;
        
        --Evaluamos si ya existe el registro en la tabla anidada
        existeUso(fila.coduso, codproyecto, existe);
    
        --Si no existe creamos el registro y lo asignamos a la tabla anidada de tipo OBJETO
        if not existe then
          -- Obtenemos la referencia de la entidad
          SELECT REF(r) INTO recurso_ref
          FROM tbl_recursos r
          WHERE r.codrecurso = fila.codrecurso;
      
          filanueva := fila_usa(fila.coduso, recurso_ref, fila.cantidad, fila.fechaasignacion);
          
          -- Insertamos filas
          INSERT INTO TABLE (SELECT t.USA FROM TBL_PROYECTOS t WHERE t.codigoproyecto = CODPROYECTO) 
          VALUES (filanueva);
        else
          dbms_output.put_line('Ya existe la fila con número de usa: ' || fila.coduso || ', y no se insertará.');
        end if;
        
        existe:=false;
        
    END LOOP;
       
    CLOSE c1; -- Cerramos el cursor
  
END;
/
