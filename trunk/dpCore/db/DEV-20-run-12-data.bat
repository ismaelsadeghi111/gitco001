@call 01-set-params.bat
@echo Running following command
@echo mysql -u %db.username% -p%db.password%  %db.database%  < 13-DEV-data.sql

mysql -u %db.username% -p%db.password%  %db.database%  < 13-DEV-data.sql
pause