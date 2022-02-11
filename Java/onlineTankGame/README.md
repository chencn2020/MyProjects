# 在线双人联机坦克大战小游戏

## 1. 介绍

大二上学期JAVA语言结课大作业

通过使用面向对象的编程思想，采用数据库、Http协议、Spring图像可视化、多线程、发送邮件等技术实现在线双人联机坦克大战小游戏


### 1.1 文件结构

```
----onlineTankGame/
    |----code/
    |    |----tankGame/
    |    |    |----img/
    |    |    |----Music/
    |    |    |----src/
    |    |    |    |----main/
    |    |    |    |    |----java/
    |    |    |    |    |    |----actions/
    |    |    |    |    |    |----allUI/
    |    |    |    |    |    |----getElements/
    |    |    |    |    |    |----music/
    |    |    |    |    |    |----runGame/
    |    |    |    |    |----resources/
    |    |    |----target/
    |    |    |    |----classes/
    |    |    |    |    |----actions/
    |    |    |    |    |----allUI/
    |    |    |    |    |----getElements/
    |    |    |    |    |----music/
    |    |    |    |    |----runGame/
    |    |    |----.classpath
    |    |    |----.project
    |    |----tankGameServer/
    |    |    |----bin/
    |    |    |    |----lib/
    |    |    |    |----tankGameServer/
    |    |    |----src/
    |    |    |    |----lib/
    |    |    |    |----tankGameServer/
    |    |    |----.classpath
    |    |    |----.project
    |    |    |----certificate.pdf
    |    |    |----example.pdf
    |    |    |----MAP.txt
    |    |    |----tankGameServer.iml
    |    |    |----坦克地图.xlsx
    |    |----tankgame.sql
    |----Documents/
    |    |----用户手册.pdf
    |    |----项目文档.pdf
    |----image/
    |    |----README/
    |    |    |----IDEA/
    |    |    |    |----op1.jpg
    |    |    |    |----op2.jpg
    |    |    |    |----op3.jpg
    |    |    |    |----op4.jpg
    |    |    |    |----op5.jpg
    |    |    |    |----op6.jpg
    |    |    |----navicat/
    |    |    |    |----op1.jpg
    |    |    |    |----op2.jpg
    |    |    |    |----op3.jpg
    |    |    |    |----op4.jpg
    |    |    |    |----op5.jpg
    |    |    |    |----op6.jpg
    |    |    |----others/
    |    |    |    |----ascii2utf8.jpg
    |----README.md
```

### 1.2 环境配置

- Java SDK 17/16/15
- Mysql（任意版本，不要太老就行）
- IDEA
- Navicat

```
注意⚠：放置代码时，建议文件路径中不要出现中文，否则会报错。
```
## 2. 实现功能

1. 基于**数据库系统**的用户信息注册、改密、分数记录、分数查询功能
2. 基于**Http传输协议**的双人联机小游戏功能、在线聊天室、用户反馈信息功能
3. 基于**SMTP协议**的邮箱验证码、荣誉证书发放功能
3. 完备的游戏逻辑（**时间限制、投降机制、计分机制、排名机制、输赢机制**）
4. 采用**多线程机制**实现坦克发射子弹后，依旧可以控制坦克移动或者重复发送弹药以及倒计时功能
5. 采用**Spring编程**，实现游戏界面可视化

## 3. 使用说明
### 3.1 数据库准备
1. 下载安装好[Mysql](https://www.mysql.com/cn/downloads/)和[Navicat](https://www.navicat.com.cn/)后，打开Navicat
2. 新建一个Mysql连接
![Mysql连接](image/README/navicat/op1.jpg)
3. 连接名填```tankGame```；密码填安装Mysql时，设置账户时自己设置的密码（可自行百度如何安装Mysql）；点击测试连接，显示成功后，一路点确定
![Mysql连接](image/README/navicat/op2.jpg)
4. 双击设置好的连接名```tankGame```，右键新建数据库，命名为```tankgame```
![新建数据库](image/README/navicat/op3.jpg)
5. 右键新建好的数据库，选择```运行SQL文件```
![导入数据](image/README/navicat/op4.jpg)
6. 选取代码中提供的[sql文件](code/tankgame.sql)，点击```开始```等待运行结束
![导入文件](image/README/navicat/op5.jpg)
7. 刷新一下即可看见数据库结构和数据均已导入到数据库中
![导入完成](image/README/navicat/op6.jpg)
8. 习惯用命令行操作的大佬可以直接再命令行里新建库（**库名记得对应**），然后运行[sql文件](code/tankgame.sql)即可
9. 后续的所有游戏记录、用户信息均会被服务器保存在数据库中
10. 对数据库表结构的介绍在这就不做过多赘述
```
注意⚠：由于是本地数据库，请在每次运行代码前，提前打开数据库
（在Navicat里双击tankgame数据库，显示为绿色即可）
否则代码会因找不到数据库而报错
```
### 3.2 运行程序
```
注意事项⚠：当时编写代码时，文件可能是以ASCII格式进行保存的
所以再次打开可能会出现乱码的情况
只需以GBK格式载入文件
或者把*.java文件先用记事本打开，然后选择另存为，最后再在保存界面的右下角选择UTF-8格式重新保存一下即可
```
![格式转换](image/README/others/ascii2utf8.jpg)
#### 3.2.1 打开后端服务器（先运行）
1. 从IDEA上导入后端服务器文件夹```code/tankGameServer```
![导入项目](image/README/IDEA/op1.jpg)
2. 可运行Main程序在```code/tankGameServer/src/tankGameServer/TankGameServer.java```中
直接运行到命令行出现```Server: Wainting connection~~~```即可
![后端运行成功界面](image/README/IDEA/op2.jpg)
3. 在该文件夹中，可以利用```code/tankGameServer/坦克地图.xlsx```来设计地图，设计好后，把对应的文本数据复制粘贴到```code/tankGameServer/MAP.txt```中即可
![设计地图](image/README/IDEA/op3.jpg)
4. 对于读取数据库部分，需要将```TankGameServer.java```中的```TankGameServer```类中的```yourUserName```以及```yourUserRoot```修改成自己本地或者云数据库中的用户名和密码

``` Java
private TankGameServer() {
    File file = new File(fileName);
    try {
        // Read the map information from the map file
        BufferedReader buf = new BufferedReader(new FileReader(file));
        String s = "";
        int i = 0;
        while ((s = buf.readLine()) != null) {
            map += s + "/n";
            i++;
        }
        map += "end123";
        // Connect the sql
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/tankGame?useSSL=false&serverTimezone=UTC";
        conn = DriverManager.getConnection(url, "yourUserName", "yourUserRoot");
        stat = conn.createStatement();
        DatabaseMetaData dbMetaData = conn.getMetaData();

    } catch (Exception e) {
        e.printStackTrace();
    }

}
```

5. 对于邮件发送功能（代码中支持的是QQ邮箱），由于涉及到账号隐私安全，这里把```code/tankGameServer/src/tankGameServer/SendMail.java```里面的邮箱和授权码省掉。关于如何申请邮箱授权码，可自行百度
需要自行填写邮箱和授权码的部分为：
```java
...

// Set a session object
Session session = Session.getDefaultInstance(properties, new Authenticator() {
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("yourMail", "yourAuthorizationCode");
    }
});

...
// Set the sender mail
mimeMessage.setFrom(new InternetAddress("yourMail"));

...

// Set the receiver mail
mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("yourAnotherEmail"));

// Connect the server
// Change the 'smtp.qq.com' can make it apply to different types of e-mails
// Like smtp.163.com for 163 mail, smtp.qq.com for qq mail
transport.connect("smtp.qq.com", "yourMail", "yourAuthorizationCode");

...

```

#### 3.2.2 打开前端游戏（后运行）
1. 从IDEA上导入前端游戏文件夹```code/tankGame```
![导入项目](image/README/IDEA/op1.jpg)

2. 可执行main文件在```code/tankGame/src/main/java/runGame/BeginGame.java```中，直接运行项目即可
![导入项目](image/README/IDEA/op4.jpg)
3. 注册或输入正确的用户名和密码即可进入游戏主页面。此刻会显示等待其他玩家连接
![开始游戏](image/README/IDEA/op5.jpg)
4. 若在公网或局域网中双人游戏，请修改```code/tankGame/src/main/java/allUI/LoginUI.java```中以下代码的```("127.0.0.1", 15319)```部分为公网IP即可。如果修改端口号，务必记得在服务器文件中也对应修改。

```java
public LoginUI() throws UnknownHostException, IOException {
    final JFrame frame = new JFrame();
    // Connect with the server
    final Socket server = new Socket("127.0.0.1", 15319);
```

5. 若只想在本地进行游戏测试，可以使用编译器或者命令行重复运行该项目即可（注意不要把之前开启的第一个游戏界面给挤掉）

6. 使用```WASDJ```操控坦克开始游戏吧
![开始游戏](image/README/IDEA/op6.jpg)

## 4. One More Thing

更多更详细的内容可见[用户手册](Documents/用户手册.pdf)、[设计文档](Documents/项目文档.pdf)

仅供学习交流，文档均已加密且添加水印

请勿直接```git clone```后提交作业

尽管游戏能正常运行，但依旧存在一些懒得改的Bug，欢迎大家提Issue交流学习

## 5. 最后说一句

这个项目是我大学完成的**第一个最自豪的大项目**

个人项目，纯独立原创完成，工作量可想而知

遇到过很多坑，但在自己的摸索中也一个个地解决了，积累了很多经验，也自学了很多东西

虽然网上有很多现成的轮子可以直接CV，但当时的自己还有着坚定的**多造轮子，少CV**的信念。因此这个大项目的完成经历，为我后面代码编写规范、新语言（C++、Python、Html等）的学习都打下了坚实的基础（虽然很多都没学精）。




## 版权声明©

该项目所有代码均为[Zevin](https://github.com/chencn2020)原创

如果有问题，欢迎大家提ISSUE

整理不易，如果觉得还不错的话记得给个star哦
