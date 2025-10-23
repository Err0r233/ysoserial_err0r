package ysoserial.payloads.util;

import ysoserial.Deserializer;

import java.io.*;
import java.util.Base64;

public class Base64Util {
    public static String getBase64Payload_ByBytes(byte[] b) throws Exception{
        return new String(Base64.getEncoder().encode(b));
    }

    public static String getBase64Payload(Object o) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
        objectOutputStream.writeObject(o);
        objectOutputStream.close();
        return new String(Base64.getEncoder().encode(baos.toByteArray()));
    }

    public static void DeserializeByBase64(String str) throws Exception{
        byte[] res = Base64.getDecoder().decode(str);
        Deserializer.deserialize(res);
    }

}
