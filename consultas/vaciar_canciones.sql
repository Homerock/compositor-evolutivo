begin;

delete from cancionAcordes;
delete from cancion;
SELECT setval(' cancion_id_seq' ,1,false);
SELECT setval(' cancionacordes_id_seq' ,1,false);

commit;