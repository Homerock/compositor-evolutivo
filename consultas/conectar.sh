#!/bin/bash

# -------------------------------------------------------------
# SCRIPT PARA CONECTARSE
# A LA BASE DE DATOS.
# -------------------------------------------------------------
# USO : #sh conectar.sh 
# -------------------------------------------------------------

export PGHOST=localhost
export PGUSER=compositordb
export PGPASSWORD=hendrix
export PGDATABASE="compositordb"
export PGDATESTYLE=European,SQL

psql 
