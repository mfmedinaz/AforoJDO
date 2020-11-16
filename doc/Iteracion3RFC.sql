
--RFC7 - Analizar la operación de AFOROCC

--Hora mayor afluencia
--Primero, buscamos cuántos Espacios de un determinado tipo_establecimiento fueron visitados en un determinado tiempo (para después comparar estos valores y ver cuál hora es la de mayor afluencia en AforoJDO)
SELECT COUNT(Espacio.id)
FROM VISITANTE
INNER JOIN VISITA
ON VISITANTE.id = VISITA.visitante
INNER JOIN ESPACIO
ON VISITA.lector = ESPACIO.lector
INNER JOIN LOCAL_COMERCIAL
ON LOCAL_COMERCIAL.id_espacio = ESPACIO.id
WHERE local_comercial.tipo_establecimiento = 'TIENDA' AND hora_inicial BETWEEN TO_DATE('2020-11-02-23:59:59', 'YYYY-MM-DD-HH24:MI:SS')-11 AND TO_DATE('2020-11-02-23:59:59', 'YYYY-MM-DD-HH24:MI:SS'); 

--RFC8: ENCONTRAR LOS CLIENTES FRECUENTES
SELECT VISITANTE.*
FROM VISITANTE
INNER JOIN VISITA
ON VISITANTE.id = VISITA.visitante
INNER JOIN ESPACIO
ON VISITA.lector = ESPACIO.lector
INNER JOIN LOCAL_COMERCIAL
ON LOCAL_COMERCIAL.id_espacio = ESPACIO.id
WHERE ESPACIO.id = 7 AND VISITANTE.tipo_visitante = 'CONSUMIDOR' AND hora_inicial BETWEEN TO_DATE('2020-11-02-23:59:59', 'YYYY-MM-DD-HH24:MI:SS')-11 AND TO_DATE('2020-11-02-23:59:59', 'YYYY-MM-DD-HH24:MI:SS');





--RFC9: Encontrar los visitantes que estuvieron en contacto con otro determinado visitante


--Primero, conseguimos las visitas que ha hecho el determinado visitante
SELECT VISITA.*    
FROM VISITANTE
INNER JOIN VISITA
ON VISITANTE.id = VISITA.visitante
INNER JOIN ESPACIO
ON VISITA.lector = ESPACIO.lector
WHERE VISITANTE.id = 1 AND hora_inicial BETWEEN TO_DATE('2020-11-02-23:59:59', 'YYYY-MM-DD-HH24:MI:SS')-11 AND TO_DATE('2020-11-02-23:59:59', 'YYYY-MM-DD-HH24:MI:SS');  

-- luego esto, se buscan los visitantes que comparten la visita
SELECT VISITANTE.*
FROM VISITANTE
INNER JOIN VISITA
ON VISITANTE.id = VISITA.visitante
INNER JOIN ESPACIO
ON VISITA.lector = ESPACIO.lector
WHERE VISITA.lector = 3 AND hora_inicial <  TO_DATE('2020-11-01-16:03:00', 'YYYY-MM-DD-HH24:MI:SS') AND hora_final > TO_DATE('2020-11-11-15:50:00', 'YYYY-MM-DD-HH24:MI:SS');
