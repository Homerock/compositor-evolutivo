begin;
-- elimino todas las tablas

delete from tonicas;
delete from tempos;
delete from ocurrenciasestilos;
delete from duracion;
delete from ocurrenciasacordes ;
delete from estilos;
delete from acordes;



-- elimino las secuencias
SELECT setval(' acordes_id_seq' ,1,false);
SELECT setval(' estilos_id_seq' ,1,false);
SELECT setval(' ocurrenciasacordes_id_seq' ,1,false);
SELECT setval(' duracion_id_seq' ,1,false);
SELECT setval(' ocurrenciasestilos_id_seq' ,1,false);
SELECT setval(' tempos_id_seq' ,1,false);
SELECT setval(' tonicas_id_seq' ,1,false);

commit;
