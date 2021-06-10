PID=`ps -ef  |  grep -e "java" |grep -e "-DTodoListApp" | grep -v grep | awk '{ print $2 }'`

if [ -n "$PID" ]
        then
          echo "TodoListApp" is already running!!!.
else
        echo "starting TodoListApp"
        cd /usr/app;
        java -Dfile.encoding=UTF-8 -DTodoListApp -cp "/usr/app/app.jar:/usr/app/lib/*:/usr/app/config/*" com.todo.TodoListApplication 2>&1
fi