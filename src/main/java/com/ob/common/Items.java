package com.ob.common;


import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * @author: oubin
 * @date: 2019/3/27 15:36
 * @Description:
 */
public class Items<T> {

    private Collection<T> items;

    public static <T> Items<T> of(Collection<T> list) {
        Items<T> items = new Items<>();
        Optional<Collection<T>> optionalTS = Optional.ofNullable(list);
        items.setItems(optionalTS.orElseGet(Collections::emptyList));
        return items;
    }

    public Collection<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        this.items = items;
    }
}
