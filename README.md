## 基于CAS的SSO插件

## 说明
	
		- simple-cas 通过插件的方式将cas精华压缩到一个配置文件中（sso.properties），简单的配置，优雅的代码，快捷的部署，
		- 使得cas单点登录集成变得简单，高效，方便，快捷。

## 背景
- 1、 单点登录原理

- 1.1、 什么是单点登录？

单点登录：Single Sign On,简称SSO，SSO使得在多个应用系统中，用户只需要登录一次就可以访问所有相互信任的应用系统。


- 1.2、 什么是CAS？

CAS框架：CAS（Central Authentication Service，即：统一认证服务）是实现SSO单点登录的框架。


- 1.3、 CAS框架结构

CAS分为两部分，CAS Server和CAS Client。

CAS Server用来负责用户的认证工作，就像是把第一次登录用户的一个标识

存在这里，以便此用户在其他系统登录时验证其需不需要再次登录。

CAS Client就是我们自己开发的应用程序，需要接入CAS Server端。当用户

访问我们的应用时，首先需要重定向到CAS Server端进行验证，要是原来登陆过，就免去登录，重定向到下游系统，否则进行用户名密码登陆操作。

- 1.4、 CAS中3个术语

Ticket Granting ticket (TGT) ：可以认为是CAS Server根据用户名密码生成的一张票，存在Server端

Ticket-granting cookie (TGC) ：其实就是一个Cookie，存放用户身份信息，由Server发给Client端

Service ticket (ST) ：由TGT生成的一次性票据，用于验证，只能用一次。相当于Server发给Client一张票，然后Client拿着这个票再来找Server验证，看看是不是Server签发的。

- 1.5、 CAS处理流程
<p align="center">
    <h3 align="center">单点流程</h3>
    <p align="center">
            <img src="http://t10.baidu.com/it/u=122695350,3228589382&fm=173&app=25&f=JPEG?w=640&h=417&s=08A27832191E44CC8AF5A0CA0000A0B3" >
    </p>    
</p>

1、 用户访问网站，第一次来，重定向到 CAS Server，发现没有cookie，所以再重定向到CAS Server端的登录页面，并且URL带有网站地址，便于认证成功后跳转，形如 http ://cas-server:8088/login?service=http ://localhost:8081

注意：service后面这个地址就是登录成功后要重定向的下游系统URL。

2、 在登陆页面输入用户名密码认证，认证成功后cas-server生成TGT，再用TGT生成一个ST。 然后再第三次重定向并返回ST和cookie(TGC)到浏览器

3、浏览器带着ST再访问想要访问的地址http ://localhost:8088/?ticket=ST-25939-sqbDVZcuSvrvBC6MQlg5

注意：ticket后面那一串就是ST

4、浏览器的服务器收到ST后再去cas-server验证一下是否为自己签发的，验证通过后就会显示页面信息，也就是重定向到第1步service后面的那个URL

首次登陆完毕。

5、再登陆另一个接入CAS的网站，重定向到CAS Server，server判断是第一次来（但是此时有TGC，也就是cookie，所以不用去登陆页面了），但此时没有ST，去cas-server申请一个于是重定向到cas-server，形如：http: //cas-server:8100/login?service=http ://localhost:8082 && TGC(cookie) (传目标地址和cookie）

6、cas-server生成了ST后重定向给浏览器http ://localhost:8082/?ticket=ST-25939-sqfsafgefesaedswqqw5-xxxx

7、浏览器的服务器收到ST后再去cas-server验证一下是否为自己签发的，验证通过后就会显示页面信息（同第4步）

## 功能
- 1、简洁：API直观简洁，可快速上手；
- 2、轻量级：环境依赖小，部署与接入成本较低；
- 3、单点登录：只需要登录一次就可以访问所有相互信任的应用系统;
- 4、插件：便于移植，方便接入;
- 5、部署：将项目直接打成jar包，依赖于项目中，启动容器即可;


## 开发

使用很简单，需要在项目的根目录下建一个sso.properties的配置文件，并将如下内容维护一下：

sso=false  --是否开启单点登录验证
sso.app_url=http://localhost:8088/casClient1 --单点登录的应用服务器
sso.cas_url=http://192.168.1.105:8081/cas --单点登录认证服务器
sso.filter_mapping=/* -- 过滤范围
 
欢迎大家的关注和使用。


## 捐赠
No matter how much the amount is enough to express your thought, thank you very much ：）      

无论金额多少都足够表达您这份心意，非常感谢 ：）    
