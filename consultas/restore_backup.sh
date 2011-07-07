#!/bin/bash
psql compositordb -Ucompositordb -hlocalhost < backup.txt
