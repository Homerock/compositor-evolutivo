#!/bin/bash
psql compositordb -Ucompositordb -hlocalhost < $1
