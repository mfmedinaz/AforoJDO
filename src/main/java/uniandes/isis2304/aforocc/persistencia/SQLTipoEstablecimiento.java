package uniandes.isis2304.aforocc.persistencia;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.aforocc.negocio.Espacio;
import uniandes.isis2304.aforocc.negocio.EstadoVisitante;
import uniandes.isis2304.aforocc.negocio.TipoEstablecimiento;

public class SQLTipoEstablecimiento 
{
	private final static String SQL = PersistenciaAforoCC.SQL;

	private PersistenciaAforoCC pa;

	public SQLTipoEstablecimiento (PersistenciaAforoCC pa)
	{
		this.pa = pa;
	}
}
