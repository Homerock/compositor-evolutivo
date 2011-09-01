

select  
	e.nombre,
	a.nombre,
	ca.numeroestrofa,
	ca.numerocompas,
	ca.numeroacorde

from 	cancionacordes ca
	JOIN estilos e 
		on ca.estiloestrofaid=e.id
	JOIN acordes a 
		on ca.acordeid=a.id,
	cancion c
where
	c.id = ca.cancionid
	and c.id=1
order by ca.numeroestrofa,ca.numerocompas,ca.numeroacorde;
