#!/bin/bash
pg_dump -a compositordb -hlocalhost -Ucompositordb > backup.txt
