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
	nombre text not null,
	cantApariciones integer default 0	
);
alter table acordes add constraint nombre_unique_key unique(nombre);

create table estilos(
	id integer not null PRIMARY KEY DEFAULT nextval('estilos_id_seq'),
	nombre text not null,
	cantuncompas integer default 0,
	cantdoscompases integer default 0,
	cantcuatrocompases integer default 0,
	cantochocompases integer default 0,
	unacordeencompas integer default 0,
	dosacordesencompas integer default 0,
	tresacordesencompas integer default 0,
	cuatroacordesencompas integer default 0
);
alter table estilos add constraint estilos_unique_key unique(nombre);

create table ocurrenciasAcordes(
	id integer not null PRIMARY KEY DEFAULT nextval('ocurrenciasacordes_id_seq'),
	acordePrincipalID integer default null references acordes(id),
	acordeSecundarioID integer default null references acordes(id),
	cantidad integer default 0,
	estilosID integer default null references estilos(id)
);

create table duracion(
	id integer not null PRIMARY KEY DEFAULT nextval('duracion_id_seq'),
	duracion integer default 0,
	cantidad integer default 0,
	estilosID integer default null references estilos(id)
);
alter table duracion add constraint duracion_unique_key unique(duracion);


create table ocurrenciasEstilos(
	id integer not null PRIMARY KEY DEFAULT nextval('ocurrenciasestilos_id_seq'),
	estiloPrincipalID integer default null references estilos(id),
	estiloSecundarioID integer default null references estilos(id),
	cantidad integer default 0
);

create table tempos(
	id integer not null PRIMARY KEY DEFAULT nextval('tempos_id_seq'),
	tempo text not null,
	cantidad integer default 0,
	estilosID integer default null references estilos(id)
);
alter table tempos add constraint tempos_unique_key unique(tempo);


create table tonicas(
	id integer not null PRIMARY KEY DEFAULT nextval('tonicas_id_seq'),
	acordeID integer default null references acordes(id),
	cantidad integer default 0,
	estilosID integer default null references estilos(id)
);


commit;
