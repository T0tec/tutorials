# Helloworld with standard JPA + Annotations 

## How to execute application

1.  Start HSQLDB Server: java -classpath ${PROJECT_DIRECTORY}/lib/hsqldb-2.2.9.jar org.hsqldb.Server

2.  Export Schema:  
    
    a.  run `ant schemaexport`
    
    b.  Take a look at the generated `helloworld-ddl.sql` file

3.  Run program:
    
    a.  run `ant run`
    
    or
    
    b.  Eclipse: Run As... Java Application
    
4.  run dbmanager
    
    run `ant dbmanager`
    
    run `SELECT * FROM "PUBLIC"."MESSAGES"`
    
5.  See result