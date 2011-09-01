

select  
	ca.estiloestrofa,
	ca.acorde,
	ca.numeroestrofa,
	ca.numerocompas,
	ca.numeroacorde

from 	cancionacordes ca,
	cancion c
where
	c.id = ca.cancionid
	and c.id=1
order by ca.numeroestrofa,ca.numerocompas,ca.numeroacorde;
