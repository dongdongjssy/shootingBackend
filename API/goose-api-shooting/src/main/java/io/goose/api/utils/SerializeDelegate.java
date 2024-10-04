package io.goose.api.utils;

import java.io.InputStream;


public interface SerializeDelegate {

	/**
     * JSON字符串转Bean.
     */
    <T> T jsonToBean(String json, Class<T> clazz);

    /**
     * Bean转JSON字符串.
     */
    String beanToJson(Object object);

    /**
     * 将xml转换为对象.
     */
    <T> T xmlToBean(String xml, Class<T> c);
    
    /**
     * 将xml转换为对象.
     */
    <T> T xmlInputStreamToBean(InputStream xml, Class<T> c);

    /**
     * 将对象转换为xml.
     */
    String beanToXml(Object obj);
}
