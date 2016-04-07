# Backend for ShareEat prototype
	
	Stack :
	 - HTTP REST Framework : Jersey https://jersey.java.net/
	 - Json Serialization/Deserialization : Gson https://github.com/google/gson
	 - HTTP Server : Grizzly https://grizzly.java.net/
	 - ORM : ormlite http://ormlite.com
	 - Cryto : jBCrypt https://github.com/jeremyh/jBCrypt
	 - Logging : log4j2 http://logging.apache.org/log4j/2.x/

	DB structure : 
	![alt text](https://github.com/ThisIsMac47/share_eat_back/raw/master/db_pattern.png "DB pattern")

# How to run it
1. Import Maven project
2. Run as -> Java Application -> Search for fr.vmarchaud.shareeat.Core
3. In run properties, select JDK8 and and these JVM args : -Djava.util.logging.config.file=log4j2.xml  -Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager
4. Its running.

# How to build it
1. Import Maven project
2. Run as -> Maven build ... 
3. Select JDK8 for java compiler
4. Use flag install for maven
5. Its built in target/
