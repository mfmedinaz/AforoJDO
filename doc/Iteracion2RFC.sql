--RFC1
SELECT VISITANTE.*
FROM VISITANTE
INNER JOIN VISITA
ON VISITANTE.id = VISITA.visitante
INNER JOIN ESPACIO
ON VISITA.lector = ESPACIO.lector
WHERE hora_inicial BETWEEN TO_DATE('10:00:00', 'hh24:mi:ss') AND TO_DATE('20:00:00', 'hh24:mi:ss') AND ESPACIO.nombre = 'Zara';

--RFC2
WITH espacios AS (
	SELECT ESPACIO.nombre, RANK() OVER(ORDER BY COUNT(ESPACIO.id) DESC) popular_rank
    FROM VISITANTE
    INNER JOIN VISITA
    ON VISITANTE.id = VISITA.visitante
    INNER JOIN ESPACIO
    ON VISITA.lector = ESPACIO.lector
    WHERE hora_inicial BETWEEN TO_DATE('10:00:00', 'hh24:mi:ss') AND TO_DATE('20:00:00', 'hh24:mi:ss')
    GROUP BY ESPACIO.nombre, ESPACIO.id
)
SELECT 
	nombre  
FROM 
	espacios
WHERE
	popular_rank <= 20;
    
    
--RFC3
--Calculo del aforo real de todo el CC
SELECT COUNT(VISITANTE.id)
FROM VISITANTE
INNER JOIN VISITA
ON VISITANTE.id = VISITA.visitante
INNER JOIN ESPACIO
ON VISITA.lector = ESPACIO.lector
WHERE hora_inicial BETWEEN TO_DATE('10:00:00', 'hh24:mi:ss') AND TO_DATE('20:00:00', 'hh24:mi:ss');

--Calculo del aforo real de un establecimiento
SELECT COUNT(VISITANTE.id)
FROM VISITANTE
INNER JOIN VISITA
ON VISITANTE.id = VISITA.visitante
INNER JOIN ESPACIO
ON VISITA.lector = ESPACIO.lector
WHERE hora_inicial BETWEEN TO_DATE('10:00:00', 'hh24:mi:ss') AND TO_DATE('20:00:00', 'hh24:mi:ss') AND ESPACIO.id = 2;

--Calculo del aforo real de un tipo de establecimiento
SELECT COUNT(VISITANTE.id)
FROM VISITANTE
INNER JOIN VISITA
ON VISITANTE.id = VISITA.visitante
INNER JOIN ESPACIO
ON VISITA.lector = ESPACIO.lector
INNER JOIN LOCAL_COMERCIAL
ON LOCAL_COMERCIAL.id_espacio = ESPACIO.id
WHERE hora_inicial BETWEEN TO_DATE('10:00:00', 'hh24:mi:ss') AND TO_DATE('20:00:00', 'hh24:mi:ss') AND LOCAL_COMERCIAL.tipo_establecimiento = 'TIENDA';

--Calculo del area total de todos los locales comerciales
SELECT SUM(LOCAL_COMERCIAL.area)
FROM ESPACIO
INNER JOIN LOCAL_COMERCIAL
ON LOCAL_COMERCIAL.id_espacio = ESPACIO.id;

--Calculo del numero total de ascensores
SELECT COUNT(ESPACIO.id)
FROM ESPACIO
WHERE ESPACIO.tipo = 'ASCENSOR';

--Calculo del numero total de sanitarios de los baños
SELECT SUM(BANIO.numero_sanitarios)
FROM ESPACIO
INNER JOIN BANIO
ON BANIO.id_espacio = ESPACIO.id;

--Calculo del area de un establecimiento
SELECT LOCAL_COMERCIAL.area
FROM ESPACIO
INNER JOIN LOCAL_COMERCIAL
ON LOCAL_COMERCIAL.id_espacio = ESPACIO.id
WHERE ESPACIO.id = 4;

--Calculo del area de un tipo de establecimiento
SELECT LOCAL_COMERCIAL.area
FROM ESPACIO
INNER JOIN LOCAL_COMERCIAL
ON LOCAL_COMERCIAL.id_espacio = ESPACIO.id
WHERE LOCAL_COMERCIAL.tipo_establecimiento = 'TIENDA';