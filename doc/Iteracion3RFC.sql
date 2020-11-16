
--RFC7 - Analizar la operación de AFOROCC

--Hora mayor afluencia
SELECT COUNT(Espacio.id)
FROM VISITANTE
INNER JOIN VISITA
ON VISITANTE.id = VISITA.visitante
INNER JOIN ESPACIO
ON VISITA.lector = ESPACIO.lector;  
