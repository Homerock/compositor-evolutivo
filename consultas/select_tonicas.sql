

SELECT 
	a.nombre,
	e.nombre, 
	t.cantidad
from 
	tonicas t, 
	acordes a , 
	estilos e 
where 
	t.acordeid=a.id and 
	t.estilosid=e.id;
