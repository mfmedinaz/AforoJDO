
--RFC7 - Analizar la operación de AFOROCC

--Hora mayor afluencia
SELECT COUNT(Espacio.id)
FROM VISITANTE
INNER JOIN VISITA
ON VISITANTE.id = VISITA.visitante
INNER JOIN ESPACIO
ON VISITA.lector = ESPACIO.lector;  


--RFC9: Encontrar los visitantes que estuvieron en contacto con otro determinado visitante


--Primero, conseguimos las visitas que ha hecho el determinado visitante
SELECT VISITA.*    
FROM VISITANTE
INNER JOIN VISITA
ON VISITANTE.id = VISITA.visitante
INNER JOIN ESPACIO
ON VISITA.lector = ESPACIO.lector
WHERE VISITANTE.id = 1 AND hora_inicial BETWEEN TO_DATE('2020-11-02-23:59:59', 'YYYY-MM-DD-HH24:MI:SS')-11 AND TO_DATE('2020-11-02-23:59:59', 'YYYY-MM-DD-HH24:MI:SS');  


SELECT VISITANTE.*
FROM VISITANTE
INNER JOIN VISITA
ON VISITANTE.id = VISITA.visitante
INNER JOIN ESPACIO
ON VISITA.lector = ESPACIO.lector
WHERE ESPACIO.id = 4 AND hora_inicial <  TO_DATE('2020-11-01-16:02:00', 'YYYY-MM-DD-HH24:MI:SS') AND hora_final > TO_DATE('2020-11-11-15:51:00', 'YYYY-MM-DD-HH24:MI:SS');