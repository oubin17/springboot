package com.ob.other.test;

/**
 * @Author: oubin
 * @Date: 2020/7/3 16:01
 * @Description:
 */
public class StaticClassTest {

    private final int hashCode = 1;

    static class StaticClass {
        private String staticStr;

        StaticClass(String staticStr) {
            this.staticStr = staticStr;
        }

        public String getStaticStr() {
            return staticStr;
        }
    }
}
