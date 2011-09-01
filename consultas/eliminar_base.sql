begin;
-- elimino todas las tablas

drop table tonicas;
drop table tempos;
drop table ocurrenciasestilos;
drop table duracion;
drop table ocurrenciasacordes ;
drop table cancionacordes;
drop table cancion;
drop table acordes;
drop table estilos;
-- elimino las secuencias
drop sequence acordes_id_seq ;
drop sequence estilos_id_seq ;
drop sequence ocurrenciasacordes_id_seq ;
drop sequence duracion_id_seq ;
drop sequence ocurrenciasestilos_id_seq ;
drop sequence tempos_id_seq ;
drop sequence tonicas_id_seq ;
drop sequence cancion_id_seq;
drop sequence cancionacordes_id_seq;


commit;
