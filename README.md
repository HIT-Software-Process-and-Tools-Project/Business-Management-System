# Business-Management-System

General Wholesale and Retail Business Management System - Project of HIT Software Process and Tools

## 1 Environment Configuration

1. Clone this repo and checkout to the latest commit(demonstrated with [GitKraken](https://www.gitkraken.com) below)
   <div align=center>
      <img src="https://user-images.githubusercontent.com/78716774/201571278-4239636a-c443-4e8c-ba72-601f3cc64807.png" width="600"/>
   </div>

2. Update your dependencies in `wms/pom.xml` automatically. `Maven scripts found` will be informed if you use [IntelliJ IDEA](https://www.jetbrains.com/idea/); or you can see `Maven` at the right sidebar, select `Refresh all maven projects`.

3. Configure `Run/Debug Configurations`.
   <div align=center>
      <img src="https://user-images.githubusercontent.com/78716774/201572357-73c7b3f5-57c0-4979-b7dc-d7dfa94279f9.png" width="700"/>
      <img src="https://user-images.githubusercontent.com/78716774/201572369-5085c95e-f8c6-484b-8950-6068e08ad84e.png" width="700"/>
   </div>
4. Open `cmd` as an administrator and run following commands.

   ```
   $ cd ~/wms_web/
   $ npm install
   $ npm install axios vue-router@3.5.4 vuex@3.0.0 --save 
   ```

5. Connect to your database, then run:
   
   ```
   $ cd ..
   $ cd wms/src/main/resources/
   $ cd . > application.yml
   ```

   Fill in the following code in `application.yml`:

   ```
   server:
     port: 8090
   
   spring:
     datasource:
       url: jdbc:mysql://localhost:<your_port>/<your_database>?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
       driver-class-name: com.mysql.cj.jdbc.Driver
       username: <your_username>
       password: <your_password>
   
   Logging:
     level:
       com.wms: debug
   ```

   It is also recommended to configure the database in the project by yourself.

7. Run this project (all configurations). Succeed! 
   <div align=center>
      <img src="https://user-images.githubusercontent.com/78716774/201687832-516b1173-8048-4f54-a3d9-4be96f472aaf.png" width="600"/>
   </div>

## 2 Acknowledgements

Most ideas of our project are mainly from [RuoYi](https://github.com/YunaiV/ruoyi-vue-pro). Thanks for [RuoYi](https://github.com/YunaiV/ruoyi-vue-pro) and other open source repos sincerely.

## 3 License

The source code is released under [GPLv3](https://www.gnu.org/licenses/) license.

## 4 Maintenance

Feel free to contact us if you have any question.

## 5 Interfaces:
   <div align=center>
      <img src="https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20230128175002.png" width="700"/>
      <img src="https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750021.png" width="700"/>
      <img src="https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750028.png" width="700"/>
      <img src="https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750022.png" width="700"/>
      <img src="https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750023.png" width="700"/>
      <img src="https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750024.png" width="700"/>
      <img src="https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750025.png" width="700"/>
      <img src="https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750026.png" width="700"/>
      <img src="https://cdn.jsdelivr.net/gh/pandalandala/imgbed@main/2023/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_202301281750027.png" width="700"/>
   </div>
