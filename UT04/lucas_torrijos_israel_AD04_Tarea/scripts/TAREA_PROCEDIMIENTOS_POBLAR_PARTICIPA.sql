CREATE OR REPLACE PROCEDURE POBLAR_TABLA_ANIDADA_PARTICIPA (CODPROYECTO NUMBER) AS
   
    --Creacion cursor: asignamos al cursor los datos de la tabla RELACIONAL
    CURSOR c1 IS 
        SELECT par.codparticipacion, par.codestudiante, par.tipoparticipacion, par.numaportaciones
        FROM PARTICIPA PAR
        WHERE PAR.CODIGOPROYECTO = CODPROYECTO;
        
    fila c1%ROWTYPE;
    estudiante_ref REF fila_estudiante;
    existe boolean:=false;
    filanueva fila_participa;
BEGIN
    -- Abrimos el cursor
    OPEN c1;
  
    LOOP
        -- Recuperamos una fila del cursor
        FETCH c1 INTO fila;
        
        --Control de salida del bucle
        EXIT WHEN c1%NOTFOUND;
        
        --Evaluamos si ya existe el registro en la tabla anidada
        existeParticipa(fila.codparticipacion, codproyecto, existe);
    
        --Si no existe creamos el registro y lo asignamos a la tabla anidada de tipo OBJETO
        if not existe then
          -- Obtenemos la referencia de la entidad
          SELECT REF(e) INTO estudiante_ref
          FROM TBL_ESTUDIANTES e
          WHERE e.codestudiante = fila.codestudiante;
      
          filanueva := fila_participa(fila.codparticipacion, estudiante_ref, fila.tipoparticipacion, fila.numaportaciones);
          
          -- Insertamos filas
          INSERT INTO TABLE (SELECT t.PARTICIPA FROM TBL_PROYECTOS t WHERE t.codigoproyecto = CODPROYECTO) 
          VALUES (filanueva);
        else
          dbms_output.put_line('Ya existe la fila con número de participacion: ' || fila.codparticipacion || ', y no se insertará.');
        end if;
        
        existe:=false;
        
    END LOOP;
       
    CLOSE c1; -- Cerramos el cursor
  
END;
/
