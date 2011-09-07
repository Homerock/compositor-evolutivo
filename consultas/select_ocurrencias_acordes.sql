

select 
	a1.nombre as acorde_ppal,
	a2.nombre as acorde_sec,
	e.nombre as estilo,
	oa.cantidad

from
	ocurrenciasacordes as oa,
	acordes as a1,
	acordes as a2,
	estilos as e
where 
	oa.acordeprincipalid = a1.id
	and oa.acordesecundarioid=a2.id
	and oa.estilosid = e.id
;
