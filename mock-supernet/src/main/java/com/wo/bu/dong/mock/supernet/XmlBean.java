package com.wo.bu.dong.mock.supernet;

import java.io.StringWriter;
import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class XmlBean {

    public static <T> String toXml(T t) {
        XStream xstream = new XStream();
        xstream.processAnnotations(t.getClass());
        return xstream.toXML(t);
    }

    public static <T> T toBean(String xmlStr, Class<T> cls) {
        XStream xstream = new XStream();
        xstream.processAnnotations(cls);
        @SuppressWarnings("unchecked")
        T t = (T) xstream.fromXML(xmlStr);
        return t;
    }
    
    public static <T> String toXmlWithOutFormat(T t) {
        XStream xstream = new XStream();
        xstream.processAnnotations(t.getClass());
        Writer writer = new StringWriter();  
        xstream.marshal(t, new CompactWriter(writer));  
        return writer.toString();
    }
    
    
    
    

}
