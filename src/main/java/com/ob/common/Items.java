package com.ob.common;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * @author: oubin
 * @date: 2019/3/27 15:36
 * @Description:
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Items<T> {

    @SuppressWarnings("unchecked")
    private static final Items EMPTY_ITEM = of(Collections.EMPTY_LIST) ;

    private Collection<T> items = Collections.emptyList();

    private Long count;

    public static <T> Items<T> of(Collection<T> collection) {
        Items<T> items = new Items<>();
        items.items = collection;
        items.count = (long) collection.size();
        return items;
    }

    public static <T> Items<T> of(Collection<T> collection, long count) {
        Items<T> items = of(collection);
        items.count = count;
        return items;
    }

    @SuppressWarnings("unchecked")
    public static <T> Items<T> empty() {
        return (Items<T>) EMPTY_ITEM;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Collection<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        this.items = items;
    }
}
