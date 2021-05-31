PID=`ps -ef  |  grep -e "java" |grep -e "-DTodoListApp" | grep -v grep | awk '{ print $2 }'`

if [ -n "$PID" ]
        then
          echo "TodoListApp" is already running!!!.
else
        echo "starting TodoListApp"
        cd /usr/app;
        java -Dfile.encoding=UTF-8 -DTodoListApp -jar /usr/app/app.jar --spring.config.location=/usr/app/config/application.properties 2>&1
fi