/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package com.phyzicsz.milo.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation class for the {@link AVList} interface. Classes
 * implementing <code>AVList</code> can subclass or aggregate this class to
 * provide default <code>AVList</code> functionality. This class maintains a
 * hash table of attribute-value pairs.
 * <p>
 * This class implements a notification mechanism for attribute-value changes.
 * The mechanism provides a means for objects to observe attribute changes or
 * queries for certain keys without explicitly monitoring all keys. See {@link
 * java.beans.PropertyChangeSupport}.
 *
 * @author Tom Gaskins
 * @version $Id: AVListImpl.java 2255 2014-08-22 17:36:32Z tgaskins $
 */
public class AVListImpl implements AVList {

    private static final Logger logger = LoggerFactory.getLogger(AVListImpl.class);

    // Identifies the property change support instance in the avlist
    private static final String PROPERTY_CHANGE_SUPPORT = "avlist.PropertyChangeSupport";

    // To avoid unnecessary overhead, this object's hash map is created only if needed.
    private Map<String, Object> avList;

    /**
     * Creates an empty attribute-value list.
     */
    public AVListImpl() {
    }

    /**
     * Constructor enabling aggregation
     *
     * @param sourceBean The bean to be given as the source for any events.
     */
    public AVListImpl(Object sourceBean) {
        if (sourceBean != null) {
            this.setValue(PROPERTY_CHANGE_SUPPORT, new PropertyChangeSupport(sourceBean));
        }
    }

    private boolean hasAvList() {
        return this.avList != null;
    }

    private Map<String, Object> createAvList() {
        if (!this.hasAvList()) {
            // The map type used must accept null values. java.util.concurrent.ConcurrentHashMap does not.
            this.avList = new java.util.HashMap<String, Object>(1);
        }

        return this.avList;
    }

    private Map<String, Object> avList(boolean createIfNone) {
        if (createIfNone && !this.hasAvList()) {
            this.createAvList();
        }

        return this.avList;
    }

    @Override
    synchronized public Object getValue(String key) {
        if (key == null) {
            logger.error("key is null");
            throw new IllegalArgumentException("key is null");
        }

        if (this.hasAvList()) {
            return this.avList.get(key);
        }

        return null;
    }

    @Override
    synchronized public Collection<Object> getValues() {
        return this.hasAvList() ? this.avList.values() : this.createAvList().values();
    }

    @Override
    synchronized public Set<Map.Entry<String, Object>> getEntries() {
        return this.hasAvList() ? this.avList.entrySet() : this.createAvList().entrySet();
    }

    @Override
    synchronized public String getStringValue(String key) {
        if (key == null) {
            logger.error("key is null");
            throw new IllegalArgumentException("key is null");
        }
        try {
            Object value = this.getValue(key);
            return value != null ? value.toString() : null;
        } catch (ClassCastException e) {
            logger.error("key not a string: {}", key);
            throw new IllegalArgumentException("key is not a sring", e);
        }
    }

    @Override
    synchronized public Object setValue(String key, Object value) {
        if (key == null) {
            logger.error("key is null");
            throw new IllegalArgumentException("key is null");
        }

        return this.avList(true).put(key, value);
    }

    @Override
    synchronized public AVList setValues(AVList list) {
        if (list == null) {
            logger.error("attribute list is null");
            throw new IllegalArgumentException("attribute list is null");
        }

        Set<Map.Entry<String, Object>> entries = list.getEntries();
        for (Map.Entry<String, Object> entry : entries) {
            this.setValue(entry.getKey(), entry.getValue());
        }

        return this;
    }

    @Override
    synchronized public boolean hasKey(String key) {
        if (key == null) {
            logger.error("key is null");
            throw new IllegalArgumentException("key is null");
        }

        return this.hasAvList() && this.avList.containsKey(key);
    }

    @Override
    synchronized public Object removeKey(String key) {
        if (key == null) {
            logger.error("key is null");
            throw new IllegalArgumentException("key is null");
        }

        return this.hasKey(key) ? this.avList.remove(key) : null;
    }

    @Override
    synchronized public AVList copy() {
        AVListImpl clone = new AVListImpl();

        if (this.avList != null) {
            clone.createAvList();
            clone.avList.putAll(this.avList);
        }

        return clone;
    }

    @Override
    synchronized public AVList clearList() {
        if (this.hasAvList()) {
            this.avList.clear();
        }
        return this;
    }

    synchronized protected PropertyChangeSupport getChangeSupport() {
        Object pcs = this.getValue(PROPERTY_CHANGE_SUPPORT);
        if (pcs == null || !(pcs instanceof PropertyChangeSupport)) {
            pcs = new PropertyChangeSupport(this);
            this.setValue(PROPERTY_CHANGE_SUPPORT, pcs);
        }

        return (PropertyChangeSupport) pcs;
    }

    // Static AVList utilities.
    public static String getStringValue(AVList avList, String key, String defaultValue) {
        String v = getStringValue(avList, key);
        return v != null ? v : defaultValue;
    }

    public static String getStringValue(AVList avList, String key) {
        try {
            return avList.getStringValue(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer getIntegerValue(AVList avList, String key, Integer defaultValue) {
        Integer v = getIntegerValue(avList, key);
        return v != null ? v : defaultValue;
    }

    public static Integer getIntegerValue(AVList avList, String key) {
        Object o = avList.getValue(key);
        if (o == null) {
            return null;
        }

        if (o instanceof Integer) {
            return (Integer) o;
        }

        String v = getStringValue(avList, key);
        if (v == null) {
            return null;
        }

        try {
            return Integer.parseInt(v);
        } catch (NumberFormatException e) {
            logger.error("Number format conversion error: {}", v);
            return null;
        }
    }

    public static Long getLongValue(AVList avList, String key, Long defaultValue) {
        Long v = getLongValue(avList, key);
        return v != null ? v : defaultValue;
    }

    public static Long getLongValue(AVList avList, String key) {
        Object o = avList.getValue(key);
        if (o == null) {
            return null;
        }

        if (o instanceof Long) {
            return (Long) o;
        }

        String v = getStringValue(avList, key);
        if (v == null) {
            return null;
        }

        try {
            return Long.parseLong(v);
        } catch (NumberFormatException e) {
            logger.error("Number format conversion error: {}", v);
            return null;
        }
    }

    public static Double getDoubleValue(AVList avList, String key, Double defaultValue) {
        Double v = getDoubleValue(avList, key);
        return v != null ? v : defaultValue;
    }

    public static Double getDoubleValue(AVList avList, String key) {
        Object o = avList.getValue(key);
        if (o == null) {
            return null;
        }

        if (o instanceof Double) {
            return (Double) o;
        }

        String v = getStringValue(avList, key);
        if (v == null) {
            return null;
        }

        try {
            return Double.parseDouble(v);
        } catch (NumberFormatException e) {

            logger.error("Number format conversion error: {}", v);
            return null;
        }
    }

//    public void getRestorableStateForAVPair(String key, Object value, RestorableSupport rs,
//            RestorableSupport.StateObject context) {
//        if (value == null) {
//            return;
//        }
//
//        if (key.equals(PROPERTY_CHANGE_SUPPORT)) {
//            return;
//        }
//
//        if (rs == null) {
//            String message = Logging.getMessage("nullValue.RestorableStateIsNull");
//            Logging.logger().severe(message);
//            throw new IllegalArgumentException(message);
//        }
//
//        rs.addStateValueAsString(context, key, value.toString());
//    }
    public static Boolean getBooleanValue(AVList avList, String key, Boolean defaultValue) {
        Boolean v = getBooleanValue(avList, key);
        return v != null ? v : defaultValue;
    }

    public static Boolean getBooleanValue(AVList avList, String key) {
        Object o = avList.getValue(key);
        if (o == null) {
            return null;
        }

        if (o instanceof Boolean) {
            return (Boolean) o;
        }

        String v = getStringValue(avList, key);
        if (v == null) {
            return null;
        }

        try {
            return Boolean.parseBoolean(v);
        } catch (NumberFormatException e) {
            logger.error("Number format conversion error: {}", v);
            return null;
        }
    }
}
