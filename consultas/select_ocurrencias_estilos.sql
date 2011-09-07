SELECT 
	oe.id,
	e.nombre as estilo_ppal,
	e1.nombre as estilo_Sec, 
	cantidad  
from 
	ocurrenciasestilos oe, 
	estilos e, 
	estilos e1 
where 
	oe.estiloprincipalid = e.id 
	and oe.estilosecundarioid=e1.id ;

