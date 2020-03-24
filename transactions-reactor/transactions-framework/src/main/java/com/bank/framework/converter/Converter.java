package com.bank.framework.converter;

public interface Converter<T, K> {

    K convert(T object);
}