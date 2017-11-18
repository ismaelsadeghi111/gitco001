@call 01-set-params.bat
@echo Running following command
@echo mysql -u %db.username% -p%db.password%  %db.database%  < 51-ALL-seed-data.sql


mysql -u %db.username% -p%db.password%  %db.database%  < 51-ALL-seed-data.sql

pause