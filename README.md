# ysoserial_err0r
一个简单魔改的ysoserial，支持base64输出和raw bytes保存到本地
命令:

```
java -jar ysoserial-0.0.6-SNAPSHOT-all.jar [payloadType] 'Command'
```
其实是和普通ysoserial的使用方式是一样的
该ysoserial将原版的rawbytes的payload保存到了本地文件，而在终端打印base64编码的payload，这样两种情况下的payload都可以兼容

同时还为ysoserial加了部分payload，例如jackson和fastjson1、fastjson2原生反序列化
编译版本为java8，记得使用java8环境打开

 使用截图：
<img width="1730" height="924" alt="image" src="https://github.com/user-attachments/assets/acf58716-51bd-497f-a9c9-93ca820afad6" />

