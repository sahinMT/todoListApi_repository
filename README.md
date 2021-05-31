
# ToDoList Rest Api
Spring Boot + Docker + Postgres + Repository ile hazırlanmış bir Rest api'dir.

Kullanıcı tanımı ile başlar ve her kullanıcıya kendine ait liste hazırlmasına imkan vermektedir. Aynı zamanda listelerini silebilir ve tüm içeriklerini göstermektedir.

Kullanıcılar listelerine item ekleyebilmektedir veya item silebilmektedir.


Genel kullanım şöyle olabilir:

    1- User tanımı yapılır. 
 
    2- User'lar için TodoList oluşturulur. 
 
    3 -TodoList'lere item eklenir.
 
    4- Kullanıcıya ait liste görüntülenir.
 
    5- Kullanıcıya ait bir listenin item'ları görüntülenebilir.
 
    6- Tüm kullanıcılar listelenebilir.
 
 DB DIAGRAM
 
 Database'de 3 tablo oluşmaktadır.
 
    td_user: User tanımları tutulur
 
    list_item: Listelere ait item'lar.
 
    todo_list: Listeler
 
  ![Db-diagram](https://user-images.githubusercontent.com/35562979/120171799-15bbf380-c20b-11eb-947d-e0a65504f862.png)


Hexagonal Mimari

     Projenin mimarisi şu şekildedir.

![Hexagonal](https://user-images.githubusercontent.com/35562979/120171422-b4942000-c20a-11eb-9300-82ea9d6d4581.png)

UYGULAMAYI ÇALIŞTIRMA

    [1] Docker ile örnekteki image'ı ayağa kaldırmak için Postgres DB ile docker file ayağa kaldırmak için

        docker-compose -f "your-workspace"/todoList-rest-api/srcmain/resources/docker-compose.yml up -d
    
    [2] Configleri saklamak için gerekli local path volume oluşturur
           docker volume create --name v_todo_api_config --opt type=none --opt device=c:/todoapp/config --opt o=bind
    
    [3] 2 nolu maddedeki device komutundaki path'e ("c:/todoapp/config") application.properties ve log4j2.xml in manuel kopyalanması gerekmektedir.
    
    [4] Application.properties içindeki postgres ip'si güncellenebilir. 

    [5] Logları saklamak için local path volume oluşturur
         docker volume create --name v_todo_api_logs --opt type=none --opt device=c:/todoapp/logs --opt o=bind 
         
    [6] Uygulama aşağıdaki komutla çalıştırılır.
           docker run -d -p 9090:9090 --name=todo-api-test --restart=always -v v_todo_api_logs:/usr/app/logs -v v_todo_api_config:/usr/app/config msahin25/todo-list-api:0.0.4


Eclipse üzerinden çalıştırma 

    [1] IDE üzerinden main sınıf olarak TodoListApplication seçilir.
    
    [2] Aşağıdaki url'ler üzerinden kullanılabilir. 
    
    
ECLIPSE lombok kurulumu

    Projede lombok kullanılmıştır. Eclipse'te kurulu değilse aşağıdaki gibi kurulabilir.
    https://projectlombok.org/setup/eclipse


POST Add User

      http://localhost:9090/user/addUser

      Örnek Request:

      {  
        "name": "User1",  
        "status": "A"  
      }

DELETE Delete User

      http://localhost:9090/user/deleteUser?username=User1

GET All Users

      http://localhost:9090/user/getUsers

GET TodoList with list name

      http://localhost:9090/list/getList?name=ReadBook&username=User1

POST TodoList 

    http://localhost:9090/list/add?username=User1

    Örnek Request: 

    {
      "name": "ReadBook",  
      "items": [  
        {    
          "name": "Küçük Prens",      
          "status": "O",      
          "createDate": "2021-31-05T09:12:33.001Z",      
          "updateDate": "2021-31-05T09:12:33.001Z",      
          "description": "Read küçük prens"      
        }    
      ],  
      "createDate": "2016-08-29T09:12:33.001Z",  
      "updateDate": "2021-05-30T12:48:13.422Z",  
      "status": "O",  
      "description": "Read book"  
    }

GET All TodoList  

     http://localhost:9090/list/getUserList?username=User1

DELETE TodoList

    http://localhost:9090/list/delete?name=Haberler3&username=User1

GET TodoList item with item name, list name and username

    http://localhost:9090/listItem/getItem?itemName=Küçük Prens&listName=ReadBook&username=User1

POST Add TodoList item with list name and user name

    http://localhost:9090/listItem/addItem?listName=Haberler2&username=muhammet

    Örnek Request:

    {
      "name": "ListItem1",  
      "status": "O",  
      "createDate": "2021-31-05T09:12:33.001Z",  
      "updateDate": "2021-31-05T09:12:33.001Z",  
      "description": "Add list Item"  
    }


DELETE Todolist item

    http://localhost:9090/list/deleteItem?itemName=Küçük Prens?listName=ReadBook&username=User1
 
 
 SONAR ANALİZ RAPORU
 
    Proje Sonar Cloud'da analiz edilmiştir. Rapor aşağıdaki gibidir.
 
   ![SonarReport](https://user-images.githubusercontent.com/35562979/120174124-69c7d780-c20d-11eb-9996-97084bb76d4c.png)
