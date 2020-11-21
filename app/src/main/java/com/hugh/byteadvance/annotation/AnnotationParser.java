package com.hugh.byteadvance.annotation;

import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by chenyw on 2020/10/20.
 */
public class AnnotationParser {

    public static AnnotationParser annotationParser;

    public static AnnotationParser getInstance(){
        synchronized (AnnotationParser.class){
            if(annotationParser == null){
                annotationParser = new AnnotationParser();
            }
            return annotationParser;
        }
    }

    public void inject(Object o){
        Class<?> aClass = o.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for(Field field:declaredFields){
            Log.e("aaa","field.getName()------->"+field.getName());
            if(field.getName().equals("author")&& field.isAnnotationPresent(AuthorAnno.class)){
                AuthorAnno authorAnno = field.getAnnotation(AuthorAnno.class);
                Class<?> type = field.getType();
                if(Author.class.equals(type)){
                    try {
                        field.setAccessible(true);
                        field.set(o, new Author(authorAnno.name(), authorAnno.website()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
