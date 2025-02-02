CREATE OR REPLACE PROCEDURE POBLAR_TABLA_ANIDADA_PATROCINA (CODPROYECTO NUMBER) AS
   
    --Creacion cursor: asignamos al cursor los datos de la tabla RELACIONAL
    CURSOR c1 IS 
        SELECT pa.codpatrocinio, pa.codentidad, pa.importeaportacion,pa.fechaaportacion
        FROM PATROCINA pa
        WHERE PA.CODIGOPROYECTO = CODPROYECTO;
        
    fila c1%ROWTYPE;
    entidad_ref REF fila_entidad;
    existe boolean:=false;
    filanueva fila_patrocina;
BEGIN
    -- Abrimos el cursor
    OPEN c1;
  
    LOOP
        -- Recuperamos una fila del cursor
        FETCH c1 INTO fila;
        
        --Control de salida del bucle
        EXIT WHEN c1%NOTFOUND;
        
        --Evaluamos si ya existe el registro en la tabla anidada
        existePatrocinador(fila.codpatrocinio, codproyecto, existe);
    
        --Si no existe creamos el registro y lo asignamos a la tabla anidada de tipo OBJETO
        if not existe then
          -- Obtenemos la referencia de la entidad
          SELECT REF(e) INTO entidad_ref
          FROM TBL_ENTIDADES e
          WHERE e.codentidad = fila.codentidad;
      
          filanueva := fila_patrocina(fila.codpatrocinio, entidad_ref, fila.importeaportacion, fila.fechaaportacion);
          
          -- Insertamos filas
          INSERT INTO TABLE (SELECT t.PATROCINA FROM TBL_PROYECTOS t WHERE t.codigoproyecto = CODPROYECTO) 
          VALUES (filanueva);
        else
          dbms_output.put_line('Ya existe la fila con número de patrocinio: ' || fila.codpatrocinio || ', y no se insertará.');
        end if;
        
        existe:=false;
        
    END LOOP;
       
    CLOSE c1; -- Cerramos el cursor
  
END;
/
