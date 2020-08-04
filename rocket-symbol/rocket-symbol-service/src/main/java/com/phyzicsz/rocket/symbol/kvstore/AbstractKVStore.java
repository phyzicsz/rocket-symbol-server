/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package com.phyzicsz.rocket.symbol.kvstore;

import java.util.*;

/**
 * An interface for managing an attribute-value pair collection.
 *
 * @author Tom Gaskins
 * @version $Id: AVList.java 1171 2013-02-11 21:45:02Z dcollins $
 */
public interface AbstractKVStore {

    /**
     * Adds a key/value pair to the list. Replaces an existing key/value pair if
     * the list already contains the key.
     *
     * @param key the attribute name. May not be <code>null</code>.
     * @param value the attribute value. May be <code>null</code>, in which case
     * any existing value for the key is removed from the collection.
     *
     * @return previous value associated with specified key, or null if there
     * was no mapping for key. A null return can also indicate that the map
     * previously associated null with the specified key, if the implementation
     * supports null values.
     *
     * @throws NullPointerException if <code>key</code> is <code>null</code>.
     */
    Object setValue(String key, Object value);

    /**
     * Adds the contents of another attribute-value list to the list. Replaces
     * an existing key/value pair if the list already contains the key.
     *
     * @param avList the list to copy. May not be <code>null</code>.
     *
     * @return <code>this</code>, a self reference.
     *
     * @throws NullPointerException if <code>avList</code> is <code>null</code>.
     */
    AbstractKVStore setValues(AbstractKVStore avList);

    /**
     * Returns the value for a specified key.
     *
     * @param key the attribute name. May not be <code>null</code>.
     *
     * @return the attribute value if one exists in the collection, otherwise
     * <code>null</code>.
     *
     * @throws NullPointerException if <code>key</code> is <code>null</code>.
     */
    Object getValue(String key);

    Collection<Object> getValues();

    /**
     * Returns the value for a specified key. The value must be a
     * {@link String}.
     *
     * @param key the attribute name. May not be <code>null</code>.
     *
     * @return the attribute value if one exists in the collection, otherwise
     * <code>null</code>.
     *
     * @throws NullPointerException if <code>key</code> is <code>null</code>.
     * @throws gov.nasa.worldwind.exception.WWRuntimeException if the value in
     * the collection is not a <code>String</code> type.
     */
    String getStringValue(String key);

    Set<Map.Entry<String, Object>> getEntries();

    /**
     * Indicates whether a key is in the collection.
     *
     * @param key the attribute name. May not be <code>null</code>.
     *
     * @return <code>true</code> if the key exists in the collection, otherwise
     * <code>false</code>.
     *
     * @throws NullPointerException if <code>key</code> is <code>null</code>.
     */
    boolean hasKey(String key);

    /**
     * Removes a specified key from the collection if the key exists, otherwise
     * returns without affecting the collection.
     *
     * @param key the attribute name. May not be <code>null</code>.
     *
     * @return previous value associated with specified key, or null if there
     * was no mapping for key.
     *
     * @throws NullPointerException if <code>key</code> is <code>null</code>.
     */
    Object removeKey(String key);

    /**
     * Returns a shallow copy of this <code>AVList</code> instance: the keys and
     * values themselves are not cloned.
     *
     * @return a shallow copy of this <code>AVList</code>.
     */
    AbstractKVStore copy();

    AbstractKVStore clearList();
}
