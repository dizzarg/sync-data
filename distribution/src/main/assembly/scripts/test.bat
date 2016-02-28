@echo off
java -DpropertyFileName=conf/sync.properties  -cp "sync-1.0-SNAPSHOT.jar;lib/*" ru.kadyrov.sync.data.Launcher %*