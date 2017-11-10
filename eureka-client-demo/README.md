Eureka Instance
Any application that registers itself with the Eureka Server to be discovered by others

About that @EnableEurekaClient annotation, it tells the following to the framework: hi there Spring, I am an instance of microservice X, so please register me to the Eureka Server (with eureka.instance.* properties), and I also want to discover other services, so create the necessary discovery client bean for me (with eureka.client.* properties).


#将 actuator health中的健康状态传播到Eureka Server
eureka.client.healthcheck.enabled=true 只应该在application.yml中设置。如果设置在bootstrap.yml中将会导致一些不良的副作用，例如在Eureka中注册的应用名称是UNKNOWN等。

从这里可以看出 instance 是将自己注册进入Eureka server 的配置
而client 是想从Eureka Server 发现其他服务的配置

1、  自定义实例ID
eureka.instance.instanceId=${spring.application.name}:${random.value}
说明：random.value是随机值，可以确保唯一性。Spring Cloud里面要求实例ID是唯一的
 
2、  显示IP地址
eureka.instance.prefer-ip-address=true

3、  设置拉取服务注册信息时间，默认60s
eureka.client.registry-fetch-interval-seconds=30

说明：如果要迅速获取服务注册状态，可以缩小该值。比如：网关接口启动需要马上调用
 
4、  指定续约更新频率，默认是30s
eureka.instance.lease-renewal-interval-in-seconds=15

说明：可以解决Eureka注册服务慢的问题，注意：在生产中，最好坚持使用默认值，因为在服务器内部有一些计算，他们对续约做出假设。
    
     5、设置过期剔除时间，默认90s
eureka.instance.lease-expiration-duration-in-seconds=45
说明：表示eureka server至上一次收到client的心跳之后，等待下一次心跳的超时时间。  
如果该值太大，则很可能将流量转发过去的时候，该instance已经不存活了。
如果该值设置太小了，则instance则很可能因为临时的网络抖动而被摘除掉。
该值至少应该大于leaseRenewalIntervalInSeconds。



eureka.client.service-url.

  

指定服务注册中心地址，类型为 HashMap，并设置有一组默认值，默认的Key为 defaultZone；默认的Value为 http://localhost:8761/eureka ，如果服务注册中心为高可用集群时，多个注册中心地址以逗号分隔。

如果服务注册中心加入了安全验证，这里配置的地址格式为： http://<username>:<password>@localhost:8761/eureka 其中 <username> 为安全校验的用户名；<password> 为该用户的密码

eureka.client.fetch-registery

默认值：true

检索服务

eureka.client.registery-fetch-interval-seconds

默认值：30

从Eureka服务器端获取注册信息的间隔时间，单位：秒

eureka.client.register-with-eureka

默认值：true

启动服务注册

eureka.client.eureka-server-connect-timeout-seconds

默认值：5

连接 Eureka Server 的超时时间，单位：秒

eureka.client.eureka-server-read-timeout-seconds

默认值：8

读取 Eureka Server 信息的超时时间，单位：秒

eureka.client.filter-only-up-instances

默认值：true

获取实例时是否过滤，只保留UP状态的实例

eureka.client.eureka-connection-idle-timeout-seconds

默认值：30

Eureka 服务端连接空闲关闭时间，单位：秒

eureka.client.eureka-server-total-connections

默认值：200

从Eureka 客户端到所有Eureka服务端的连接总数

eureka.client.eureka-server-total-connections-per-host

默认值：50

从Eureka客户端到每个Eureka服务主机的连接总数