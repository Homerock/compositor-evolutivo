begin;
-- elimino todas las tablas

delete from tonicas;
delete from tempos;
delete from ocurrenciasestilos;
delete from duracion;
delete from ocurrenciasacordes ;
delete from estilos;
delete from acordes;
--delete from cancionAcordes;
--delete from cancion;


-- elimino las secuencias
SELECT setval(' acordes_id_seq' ,1);
SELECT setval(' estilos_id_seq' ,1);
SELECT setval(' ocurrenciasacordes_id_seq' ,1);
SELECT setval(' duracion_id_seq' ,1);
SELECT setval(' ocurrenciasestilos_id_seq' ,1);
SELECT setval(' tempos_id_seq' ,1);
SELECT setval(' tonicas_id_seq' ,1);
--SELECT setval(' cancion_id_seq' ,1);
--SELECT setval(' cancionacordes_id_seq' ,1);

commit;
