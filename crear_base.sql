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
	cuatroacordesencompas integer default 0,
	esprincipal boolean default false
);
alter table estilos add constraint estilos_unique_key unique(nombre);

create table ocurrenciasAcordes(
	id integer not null PRIMARY KEY DEFAULT nextval('ocurrenciasacordes_id_seq'),
	acordePrincipalID integer not null references acordes(id),
	acordeSecundarioID integer not null references acordes(id),
	cantidad integer default 0,
	estilosID integer not null references estilos(id)
);

alter table ocurrenciasAcordes add constraint ocurrenciasAcordes_unique_key unique (acordePrincipalID,acordeSecundarioID,estilosID);

create table duracion(
	id integer not null PRIMARY KEY DEFAULT nextval('duracion_id_seq'),
	duracion integer default 0,
	cantidad integer default 0,
	estilosID integer not null references estilos(id)
);
alter table duracion add constraint duracion_unique_key unique(duracion,estilosID);


create table ocurrenciasEstilos(
	id integer not null PRIMARY KEY DEFAULT nextval('ocurrenciasestilos_id_seq'),
	estiloPrincipalID integer not null references estilos(id),
	estiloSecundarioID integer not null references estilos(id),
	cantidad integer default 0
);
alter table ocurrenciasEstilos add constraint ocurrenciasEstilos_unique_key unique(estiloPrincipalID,estiloSecundarioID);

create table tempos(
	id integer not null PRIMARY KEY DEFAULT nextval('tempos_id_seq'),
	tempo text not null,
	cantidad integer default 0,
	estilosID integer not null references estilos(id)
);
alter table tempos add constraint tempos_unique_key unique(tempo,estilosID);


create table tonicas(
	id integer not null PRIMARY KEY DEFAULT nextval('tonicas_id_seq'),
	acordeID integer default null references acordes(id),
	cantidad integer default 0,
	estilosID integer not null references estilos(id)
);
alter table tonicas add constraint tonicas_unique_key unique(acordeID,estilosID);

commit;
