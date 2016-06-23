package com.harry.common;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.harry.servlet.DispatherServlet;

public class BeanFactory {

    public static Map<String, BeanConfig> beans = new HashMap<String, BeanConfig>();
    public static Map<String, Object> singltonObj = new HashMap<String, Object>();

    public static BeanFactory beanFactory;

    static {
        init();
    }

    private static void init() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(DispatherServlet.class.getResourceAsStream("/com/harry/Bean.xml"));
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Element beanElement = (Element) beanNodeList.item(i);

                String id = beanElement.getAttribute("id");
                String className = beanElement.getAttribute("class");
                String sigleton = beanElement.getAttribute("scope");
                boolean isSigleton = false;

                if ("singleton".equalsIgnoreCase(sigleton)) {
                    isSigleton = true;
                }

                BeanConfig beanConfig = new BeanConfig(id, className, isSigleton);
                NodeList propertyList = beanElement.getElementsByTagName("Property");
                if (propertyList != null) {
                    for (int j = 0; j < propertyList.getLength(); j++) {
                        Element propertyEelement = (Element) propertyList.item(j);

                        String propertyName = propertyEelement.getAttribute("name");
                        String propertyRef = propertyEelement.getAttribute("ref");
                        beanConfig.addBeanProperty(new BeanProperty(propertyName, propertyRef));
                    }
                }
                beans.put(id, beanConfig);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BeanFactory() {
    }

    public static BeanFactory getIntance() {
        if (beanFactory == null) {
            beanFactory = new BeanFactory();
        }
        return beanFactory;
    }

    public Object getBean(String id) {
        if (beans.containsKey(id)) {
            BeanConfig bean = beans.get(id);
            if (bean.isSingleton() && singltonObj.containsKey(id)) {
                return singltonObj.get(id);
            }
            String className = bean.getClassName();
            try {
                Class<?> clz = Class.forName(className);
                Object object = clz.newInstance();
                if (bean.isSingleton()) {
                    singltonObj.put(id, object);
                }

                List<BeanProperty> beanProperties = bean.getBeanProperty();
                if (beanProperties != null && !beanProperties.isEmpty()) {
                    for (BeanProperty beanProperty : beanProperties) {
                        String name = beanProperty.getName();
                        String firstString = name.substring(0, 1).toUpperCase();
                        String leaveString = name.substring(1);
                        String methodName = "set" + firstString + leaveString;

                        Method method = null;
                        Method[] methods = clz.getMethods();
                        for (Method methodInClass : methods) {
                            if (methodName.equals(methodInClass.getName())) {
                                method = methodInClass;
                            }
                        }

                        String ref = beanProperty.getRef();
                        if (ref != null && !ref.isEmpty()) {
                            Object refObject = this.getBean(ref);
                            method.invoke(object, refObject);
                        }
                    }
                }
                if (object.getClass().getPackage().getName().equals("com.harry.service.impl")) {
                    ConnectionDymicProxy connecDymicProxy = new ConnectionDymicProxy(object);
                    Object proxyobject = Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), connecDymicProxy);
                    return proxyobject;
                }
                return object;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
