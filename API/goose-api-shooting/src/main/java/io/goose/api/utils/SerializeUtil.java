package io.goose.api.utils;

import java.io.InputStream;


public class SerializeUtil {

    private final static SerializeDelegate SERIALIZE_DELEGATE = new FastJsonSerialize();

    /**
     * JSON字符串转Bean.
     */
    public static <T> T jsonToBean(String json, Class<T> clazz) {
        return SERIALIZE_DELEGATE.jsonToBean(json, clazz);
    }

    /**
     * Bean转JSON字符串.
     */
    public static String beanToJson(Object object) {
        return SERIALIZE_DELEGATE.beanToJson(object);
    }

    /**
     * 将xml转换为对象.
     */
    public static <T> T xmlToBean(String xml, Class<T> clazz) {
        return SERIALIZE_DELEGATE.xmlToBean(xml, clazz);
    }

    /**
     * 将对象转换为xml.
     */
    public static String beanToXml(Object object) {
        return SERIALIZE_DELEGATE.beanToXml(object);
    }

    /**
     * 将xml流转换为对象.
     * @return 
     */
    public static <T> T xmlInputStreamToBean(InputStream xml ,Class<T> clazz) {
        return SERIALIZE_DELEGATE.xmlInputStreamToBean(xml, clazz);
    }
}
