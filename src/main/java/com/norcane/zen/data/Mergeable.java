package com.norcane.zen.data;

public interface Mergeable<T> {

    T merge(T other);
}
