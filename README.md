# ysoserial_err0r

一个自己简单魔改的ysoserial，支持rawbytes输出和base64编码输出

同时新增了部分payload，例如jackson、fastjson1、fastjson2原生反序列化等

用法：

```shell
java -jar ysoserial.jar [PayloadType] 'Command'
```



使用例：

![image](.\pic\05ff692c-73e6-4d98-8cda-e6a5774304c0.png)

jar包编译环境为java8，请使用java8打开