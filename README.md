ars
===

AircraftReservationService

Please make sure that Java 8 is installed and used to run the javafx frontend.

Also make sure the following Security Domains are available:
Example for wildfly:

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