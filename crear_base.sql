begin ;

--------------------------------------------
-- usuario: compositordb
-- password: hendrix
-- base de datos:  compositordb
--------------------------------------------


create sequence acordes_id_seq start 1;
create sequence estilos_id_seq start 1;
create sequence ocurrenciasacordes_id_seq start 1;
create sequence duracion_id_seq start 1;
create sequence ocurrenciasestilos_id_seq start 1;
create sequence tempos_id_seq start 1;
create sequence tonicas_id_seq start 1;

create table acordes (
	id integer not null PRIMARY KEY DEFAULT nextval('acordes_id_seq') ,
	nombre text default null,
	cantApariciones integer default 0	
);

create table estilos(
	id integer not null PRIMARY KEY DEFAULT nextval('estilos_id_seq'),
	nombre text default null,
	cantuncompas integer default null,
	cantdoscompases integer default null,
	cantcuatrocompases integer default null,
	cantochocompases integer default null
);

create table ocurrenciasAcordes(
	id integer not null PRIMARY KEY DEFAULT nextval('ocurrenciasacordes_id_seq'),
	acordePrincipalID integer default null references acordes(id),
	acordeSecundarioID integer default null references acordes(id),
	cantidad integer default null,
	estilosID integer default null references estilos(id)
);

create table duracion(
	id integer not null PRIMARY KEY DEFAULT nextval('duracion_id_seq'),
	duracion integer default null,
	cantidad integer default null,
	estilosID integer default null references estilos(id)
);

create table ocurrenciasEstilos(
	id integer not null PRIMARY KEY DEFAULT nextval('ocurrenciasestilos_id_seq'),
	estiloPrincipalID integer default null references estilos(id),
	estiloSecundarioID integer default null references estilos(id),
	cantidad integer default null
);

create table tempos(
	id integer not null PRIMARY KEY DEFAULT nextval('tempos_id_seq'),
	cantidad integer default null,
	tempo integer default null,
	estilosID integer default null references estilos(id)
);

create table tonicas(
	id integer not null PRIMARY KEY DEFAULT nextval('tonicas_id_seq'),
	acordeID integer default null references acordes(id),
	cantidad integer default null,
	estilosID integer default null references estilos(id)
);

commit;
