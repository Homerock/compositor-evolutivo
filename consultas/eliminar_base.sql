begin;
-- elimino todas las tablas

drop table tonicas;
drop table tempos;
drop table ocurrenciasestilos;
drop table duracion;
drop table ocurrenciasacordes ;
drop table estilos;
drop table acordes;

-- elimino las secuencias
drop sequence acordes_id_seq ;
drop sequence estilos_id_seq ;
drop sequence ocurrenciasacordes_id_seq ;
drop sequence duracion_id_seq ;
drop sequence ocurrenciasestilos_id_seq ;
drop sequence tempos_id_seq ;
drop sequence tonicas_id_seq ;

commit;
