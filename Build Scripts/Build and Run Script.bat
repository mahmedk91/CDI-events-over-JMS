cd "C:\Users\Ahmed\Desktop\Thesis Bat Scripts"
set CATALINA_HOME="C:\apache-tomcat-8.5.5"
call "Shutdown Tomcat.bat"
call "Build Project1.bat"
call "Build Project2.bat"
set source1="C:\Users\IBM_ADMIN\workspace\CDI-events-over-JMS\webapp1\target\webapp1.war"
set source2="C:\Users\IBM_ADMIN\workspace\CDI-events-over-JMS\webapp2\target\webapp2.war"
set destination="C:\apache-tomcat-8.5.5\webapps"
del "C:\apache-tomcat-8.5.5\webapps\webapp1.war"
del "C:\apache-tomcat-8.5.5\webapps\webapp2.war"
rmdir "C:\apache-tomcat-8.5.5\webapps\webapp1" /s /q
rmdir "C:\apache-tomcat-8.5.5\webapps\webapp2" /s /q
xcopy %source1% %destination% /y
xcopy %source2% %destination% /y
call "Startup Tomcat.bat"