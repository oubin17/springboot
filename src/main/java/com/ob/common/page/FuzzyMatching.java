package com.ob.common.page;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: oubin
 * @Date: 2019/10/22 15:04
 * @Description:
 */
@Data
public class FuzzyMatching {

    private final List<Fuzzy> fuzzyList;

    public FuzzyMatching(String property, String fuzzyValue) {
        if (null == property || null == fuzzyValue) {
            throw new IllegalArgumentException("You have to provide at least one property!");
        }
        Fuzzy fuzzy = new Fuzzy(property, fuzzyValue);
        this.fuzzyList = Lists.newArrayList(fuzzy);
    }

    public FuzzyMatching(List<Fuzzy> fuzzyList) {
        if (null == fuzzyList || fuzzyList.isEmpty()) {
            throw new IllegalArgumentException("You have to provide at least one property!");
        }
        this.fuzzyList = fuzzyList;
    }

    public static class Fuzzy implements Serializable {

        private String property;

        private String fuzzyValue;

        public Fuzzy(String property, String fuzzyValue) {
            this.property = property;
            this.fuzzyValue = fuzzyValue;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getFuzzyValue() {
            return fuzzyValue;
        }

        public void setFuzzyValue(String fuzzyValue) {
            this.fuzzyValue = fuzzyValue;
        }
    }

    public FuzzyMatching and(Fuzzy fuzzy) {
        if (null == fuzzy) {
            return this;
        }
        this.fuzzyList.add(fuzzy);
        return this;
    }

    public String toSql() {
        if (null == this.fuzzyList) {
            return "";
        }
        StringBuilder sql = new StringBuilder();
        for (Fuzzy fuzzy : this.fuzzyList) {
            sql.append(" and ").append(fuzzy.property)
                    .append(" like '%")
                    .append(fuzzy.getFuzzyValue())
                    .append("%'");
        }
        return sql.toString();
    }
}
