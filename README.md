# Business-Management-System

General Wholesale and Retail Business Management System - Project of HIT Software Process and Tools

## 1 项目环境配置

1. 首次使用请clone本项目，之后checkout到`dev`分支，本地仓库中将出现最新的一次commit（下图中以gitkraken演示）

   ![image](https://user-images.githubusercontent.com/78716774/201571278-4239636a-c443-4e8c-ba72-601f3cc64807.png)

2. IDEA会提示`Maven scripts found`或者在右侧边栏可以看到Maven，选择`Refresh all maven projects`即可

3. 这时在pom.xml里找到`groupId`为`com.baomidou`的两个依赖，版本均改为`3.4.1`；之后在pom.xml里自动更新一下依赖

4. 配置`Run/Debug Configurations`。

   ![image](https://user-images.githubusercontent.com/78716774/201572357-73c7b3f5-57c0-4979-b7dc-d7dfa94279f9.png)
   ![image](https://user-images.githubusercontent.com/78716774/201572369-5085c95e-f8c6-484b-8950-6068e08ad84e.png)

5. 进入wms_web文件夹下以管理员身份打开cmd运行`npm install`，运行完会多出一个node_modules文件夹

6. 打开数据库连接，在`wms/src/main/resources/`下新建一个`application.yml`,填入以下代码（username和password换成自己数据库的；url中的端口号和数据库名demo改成自己的）：
   ```
   server:
     port: 8090
   
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
       driver-class-name: com.mysql.jdbc.Driver
       username: root
       password: 123456
   
   Logging:
     level:
       com.wms: debug
   ```
