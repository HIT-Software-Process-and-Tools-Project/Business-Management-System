# Business-Management-System

General Wholesale and Retail Business Management System - Project of HIT Software Process and Tools

## 1 项目环境配置

1. 首次使用请clone本项目，之后checkout到`dev`分支，本地仓库中将出现最新的一次commit（下图中以gitkraken演示）

   ![image](https://user-images.githubusercontent.com/78716774/201571278-4239636a-c443-4e8c-ba72-601f3cc64807.png)

2. 粘贴pom.xml文件至`wms/`文件夹下，之后在pom.xml里自动更新一下依赖；IDEA会提示`Maven scripts found`或者在右侧边栏可以看到Maven，选择`Refresh all maven projects`即可

3. 配置`Run/Debug Configurations`。

   ![image](https://user-images.githubusercontent.com/78716774/201572357-73c7b3f5-57c0-4979-b7dc-d7dfa94279f9.png)
   ![image](https://user-images.githubusercontent.com/78716774/201572369-5085c95e-f8c6-484b-8950-6068e08ad84e.png)

4. 进入wms_web文件夹下以管理员身份打开cmd运行`npm install`，运行完会多出一个node_modules文件夹；然后运行`npm install axios --save`；然后运行`npm i 
   vue-router@3.5.4`；然后运行`npm i vuex@3.0.0`

5. 打开数据库连接，在`wms/src/main/resources/`下新建一个`application.yml`,填入以下代码（username和password换成自己数据库的；url中的端口号和数据库名demo改成自己的）：
   ```
   server:
     port: 8090
   
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
       driver-class-name: com.mysql.cj.jdbc.Driver
       username: root
       password: 123456
   
   Logging:
     level:
       com.wms: debug
   ```
   推荐在项目中配置数据库：[IntelliJ IDEA 如何配置MySQL数据库](https://blog.csdn.net/zhao_66/article/details/103806350)

6. 运行整个项目（第3步的全部配置都跑起来），这里试着在navicat等上面查看自己的连接下是否多了一个名为`demo`的数据库，方便自己后续开发时使用。

   ![image](https://user-images.githubusercontent.com/78716774/201687832-516b1173-8048-4f54-a3d9-4be96f472aaf.png)
   
------

![image](https://user-images.githubusercontent.com/78716774/215258750-0f555a14-d7b0-41b0-8f10-2d5bee8041c2.png)
![image](https://user-images.githubusercontent.com/78716774/215258755-a0844e75-bea7-4b56-9130-2913995901a4.png)
![image](https://user-images.githubusercontent.com/78716774/215258889-021a373a-a240-432d-8d7c-e7b74bfbd327.png)
![image](https://user-images.githubusercontent.com/78716774/215258875-b2773390-3eee-4ecf-81ea-36e9d23c9d4f.png)
![image](https://user-images.githubusercontent.com/78716774/215258769-f6ee15cb-c7e2-4416-b1b8-c0193eebe7f1.png)
![image](https://user-images.githubusercontent.com/78716774/215258897-f2d58db9-7a0d-4506-b215-7a34a8439c85.png)
![image](https://user-images.githubusercontent.com/78716774/215258774-d3424c8d-9050-40b2-bee3-1830c01de219.png)
![image](https://user-images.githubusercontent.com/78716774/215258780-d6d9a42e-b581-4317-87a8-e45030500389.png)
![image](https://user-images.githubusercontent.com/78716774/215258788-c17d8309-72a5-4a27-aad0-e019263170be.png)
