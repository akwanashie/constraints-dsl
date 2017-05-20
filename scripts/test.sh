#!/usr/bin/env bash

echo "
2a + 1b <= 20
a + b >= 1
max a + b

solve
print
clear

2a + 1b <= 20
a >= 10
max a + b
solve
print
clear

exit
" | sbt run