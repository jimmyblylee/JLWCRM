/**
 * CommonUtil.java
 * 共同的工具类
 */
package com.jssm.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ClassUtil {

    /** 构造方法私有 */
    private ClassUtil() {
        super();
    }

    /** 得到class的名字
     * @param classObject
     * @return class的名字 */
    public static String getClassName(Object classObject) {
        String className = classObject.getClass().getName();
        return className.substring(0, className.lastIndexOf('$'));
    }

    /** 給对象的字段赋值
     * @param object 赋值的对象
     * @param filedName 赋值的字段
     * @param param 赋值参数
     * @throws ReflectiveOperationException */
    public static void setFiled(Object object, String filedName, Object param)
                    throws ReflectiveOperationException {
        Field field = object.getClass().getDeclaredField(filedName);
        field.setAccessible(true);
        field.set(object, param);
    }

    /** @param object 获取方法的对象
     * @param methodName 方法的名字
     * @param parameterTypes 方法的参数类型
     * @return Method
     * @throws ReflectiveOperationException */
    public static Method getMethod(Object object, String methodName, Class<?>... parameterTypes)
                    throws ReflectiveOperationException {
        Method method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method;
    }

    /** 复制对象
     * @param src
     * @return T */
    @SuppressWarnings("unchecked")
    public static <T> T cloneTo(T src) {
        T dist = null;
        try {
            ByteArrayOutputStream memoryBuffer = new ByteArrayOutputStream();
            ObjectOutputStream out = null;
            ObjectInputStream in = null;
            try {
                out = new ObjectOutputStream(memoryBuffer);
                out.writeObject(src);
                out.flush();
                in = new ObjectInputStream(new ByteArrayInputStream(memoryBuffer.toByteArray()));
                dist = (T) in.readObject();
            } finally {
                if (out != null) {
                    out.close();
                    out = null;
                }
                if (in != null) {
                    in.close();
                    in = null;
                }
            }
        } catch (Exception e) {
            dist = null;
        }
        return dist;
    }
}