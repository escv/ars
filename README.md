ars
===

# ARS How to Install

## Requirements
+ JBoss Wildfly 8.0
+ JPA 2.0 compliant Database (tested with MySQL 5.6 CE)
+ Java JDK 8 (JavaFX Frontend)

## Wildfly Setup
+ Database Driver must be accessible as module (e.g. in WILDFLY\modules\com\mysql\main)
+ DataSource java:jboss/datasources/ArsDS must exist. Minimal Setup might be:
```
<datasources>
	[...]
	<datasource jndi-name="java:jboss/datasources/ArsDS" pool-name="ArsDS" enabled="true" use-java-context="true">
		<connection-url>jdbc:mysql://127.0.0.1:3306/ars?useUnicode=yes&amp;characterEncoding=UTF-8</connection-url>
		<driver>com.mysql</driver>
		<security>
			<user-name>ars</user-name>
			<password>SECRET</password>
		</security>
	</datasource>
	<drivers>
		<driver name="com.mysql" module="com.mysql">
			<driver-class>com.mysql.jdbc.Driver</driver-class>
		</driver>
	</drivers>
</datasources>
```
+ Security Domain for Service Protection (both for testing and production usage)
```
<security-domain name="ars" cache-type="default">
	<authentication>
		<login-module code="Database" flag="required">
			<module-option name="dsJndiName" value="java:jboss/datasources/ArsDS"/>
			<module-option name="principalsQuery" value="select password_digest from _user where name=?"/>
			<module-option name="rolesQuery" value="select r.name AS 'role', 'Roles' from user_role r inner join _user_roles ur on r.id=ur.roles inner join _user u on u.id=ur._user where u.name =?"/>
			<module-option name="hashAlgorithm" value="SHA1"/>
			<module-option name="hashEncoding" value="hex"/>
			<module-option name="unauthenticatedIdentity" value="guest"/>
		</login-module>
	</authentication>
</security-domain>
<security-domain name="other" cache-type="default">
	<authentication>
		<login-module code="UsersRoles" flag="sufficient"/>
	</authentication>
</security-domain>
```

## Obtain and Build

Sourcecode is hosted on GitHub to be obtained using

git clone https://github.com/escv/ars.git

After all Files were downloaded successfully, enter directory ars/ars-assembly and invoke
`mvn clean package`

The resulting zip can be found inside of the target folder. It contains the SQL DDL Statements which must be executed on Datebase representing your DataSource configured above.

The Zip also contains an EAR Archive which can be deployed on your configured JBoss Wildfly instance.
To launch the fronted, simply double click it or invoke "java -jar ars-frontend-VERSION.jar"
