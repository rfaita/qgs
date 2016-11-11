package com.qgs.model;

import java.io.Serializable;

/**
 *
 * @author rafael
 * @param <ID>
 */
public abstract class BaseBean<ID> implements Serializable {

    abstract public ID getId();

}
