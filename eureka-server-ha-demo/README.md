https://www.cnblogs.com/li3807/p/7282492.html

1、  指定环境
eureka.environment=dev

2、  指定数据中心
eureka.datacenter=roncoo
说明：如果配置-Deureka.datacenter=cloud，eureka会知道是在AWS云上

  3、关闭自我保护模式
eureka.server.enable-self-preservation=false
  说明：关闭了面板会出现提示。关闭注册中心的保护机制，Eureka 会统计15分钟之内心跳失败的比例低于85%将会触发保护机制，不剔除服务提供者，如果关闭服务注册中心将不可用的实例正确剔除

     4、设置清理无效节点的时间间隔，默认60000，即是60s
eureka.server.eviction-interval-timer-in-ms=30000



eureka.instance.prefer-ip-address

默认值：false

不使用主机名来定义注册中心的地址，而使用IP地址的形式，如果设置了

eureka.instance.ip-address 属性，则使用该属性配置的IP，否则自动获取除环路IP外的第一个IP地址

eureka.instance.ip-address

  

IP地址

eureka.instance.hostname

  

设置当前实例的主机名称

eureka.instance.appname

  

服务名，默认取 spring.application.name 配置值，如果没有则为 unknown

eureka.instance.lease-renewal-interval-in-seconds

默认值：30

定义服务续约任务（心跳）的调用间隔，单位：秒

eureka.instance.lease-expiration-duration-in-seconds

默认值：90

定义服务失效的时间，单位：秒

eureka.instance.status-page-url-path

默认值：/info

状态页面的URL，相对路径，默认使用 HTTP 访问，如果需要使用 HTTPS则需要使用绝对路径配置

eureka.instance.status-page-url

  

状态页面的URL，绝对路径

eureka.instance.health-check-url-path

默认值：/health

健康检查页面的URL，相对路径，默认使用 HTTP 访问，如果需要使用 HTTPS则需要使用绝对路径配置

eureka.instance.health-check-url
健康检查页面的URL，绝对路径