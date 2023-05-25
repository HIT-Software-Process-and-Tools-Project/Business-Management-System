# Business-Management-System

General Wholesale and Retail Business Management System - Project of HIT Software Process and Tools

## 1 项目环境配置

1. 首次使用请clone本项目，之后checkout到最新commit（下图中以gitkraken演示）

   ![image](https://user-images.githubusercontent.com/78716774/201571278-4239636a-c443-4e8c-ba72-601f3cc64807.png)

2. 在wms/pom.xml里自动更新一下依赖；IDEA会提示`Maven scripts found`或者在右侧边栏可以看到Maven，选择`Refresh all maven projects`即可

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
## 2 Some user interfaces:

<img src="https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20230128175002.png" alt="微信图片_20230128175002"  />

![202301281750021](https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750021.png)

![202301281750028](https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750028.png)

![202301281750022](https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750022.png)

![202301281750023](https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750023.png)

![202301281750024](https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750024.png)

![202301281750025](https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750025.png)

![202301281750026](https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750026.png)

![202301281750027](https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750027.png)
