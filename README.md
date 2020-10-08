## minicat

> 手写迷你版的Tomcat

### 开始

运行```com.github.codingdebugallday.minicat.Bootstrap```该类main方法即可，然后请求如下示例接口

将web项目编译，放入webapps中，格式要统一，java编译后的class文件，主要是servlet，让入WEB-INF/classes下，
以及web.xml也放入WEB-INF下，静态资源直接让如web项目下即可，详情示例参考此项目中的webapps示例
```
http://localhost:8080/demo1/index.html
http://localhost:8080/demo1/demo
http://localhost:8080/demo2/index.html
http://localhost:8080/demo2/demo
```

### tomcat

Tomcat按照Servlet规范的要求去实现了Servlet容器，同时它也具有HTTP服务器的功能

Tomcat的两个重要身份

1）http服务器

2）Tomcat是⼀个Servlet容器

#### tomcat体系结构

主要包括
* Server
* Service
* Connector
* Engine
* Host
* Context

[tomcat组件图](docs/images/tomcat组件图.png)

#### Tomcat Servlet容器处理流程

当⽤户请求某个URL资源时

1）HTTP服务器会把请求信息使⽤ServletRequest对象封装起来

2）进⼀步去调⽤Servlet容器中某个具体的Servlet

3）在 2）中，Servlet容器拿到请求后，根据URL和Servlet的映射关系，找到相应的Servlet

4）如果Servlet还没有被加载，就⽤反射机制创建这个Servlet，并调⽤Servlet的init⽅法来完成初始化

5）接着调⽤这个具体Servlet的service⽅法来处理请求，请求处理结果使⽤ServletResponse对象封装

6）把ServletResponse对象返回给HTTP服务器，HTTP服务器会把响应发送给客户端

[tomcat启动流程图](docs/images/tomcat启动流程图.png)

[tomcat请求流程示意图](docs/images/tomcat请求流程示意图.png)

