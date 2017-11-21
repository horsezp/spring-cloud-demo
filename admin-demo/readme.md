1 第一次启动是失败的 发现和原来的Demo 对比 spring-cloud-netflix-core 的版本从1.3 升到了1.35
	出现了 org/springframework/cloud/netflix/zuul/ZuulConfiguration class no found 问题 
	因为spring-boot-admin 的版本是1.50  升级到1.55 后解决
	
2 通过 http://localhost:8090 可以访问到 admin 的网页 显示的内容非常好
	

