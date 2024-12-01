CONTAINER=backender-db-1
USER=admin
DATABASE='abc'
SQL_FILE='/tmp/a.sql'
FILE_TO_SAVE='data.sql'
docker exec -it $CONTAINER sh -c "pg_dump -U $USER -d $DATABASE --data-only --inserts --no-owner --no-privileges -f $SQL_FILE"
docker cp $CONTAINER:$SQL_FILE $FILE_TO_SAVE
