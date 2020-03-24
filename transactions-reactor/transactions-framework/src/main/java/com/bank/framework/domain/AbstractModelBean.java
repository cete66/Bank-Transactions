package com.bank.framework.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class AbstractModelBean implements Serializable{

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}