package com.hugh.byteadvance.aop;

/**
 * Created by chenyw on 2020/8/12.
 */
public class AopEntity {
    public static class innerB {
        private String name;

        public void setAopName(String name) {
            this.name = name;
        }

        public String getAopName() {
            return name;
        }
    }
}
