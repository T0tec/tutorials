# Helloworld with reverse engineering feature of Hibernate

## How to execute application

1.  Start HSQLDB Server: java -classpath ${PROJECT_DIRECTORY}/lib/hsqldb-2.2.9.jar org.hsqldb.Server

2.  Run program:
    
    a.  run `ant reveng.hbmxml`  
    
    or
    
    b.  run `ant reveng.pojos`
	
	or
	
	c.  run `ant reveng.entities`
    
3.  run dbmanager
    
    run `ant dbmanager`
    
    run `SELECT * FROM "PUBLIC"."MESSAGES"`
    
4.  See result