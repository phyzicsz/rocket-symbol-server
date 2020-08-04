/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package com.phyzicsz.rocket.symbol.common;

import java.io.Closeable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tom Gaskins
 * @version $Id: WWIO.java 1171 2013-02-11 21:45:02Z dcollins $
 */
public class WWIO {

    private static final Logger logger = LoggerFactory.getLogger(WWIO.class);

    public static String stripTrailingSeparator(String s) {
        if (s == null) {
            logger.error("string is null");
            throw new IllegalArgumentException("string is null");
        }

        if (s.endsWith("/") || s.endsWith("\\")) {
            return s.substring(0, s.length() - 1);
        } else {
            return s;
        }
    }

    public static String stripLeadingSeparator(String s) {
        if (s == null) {
            logger.error("string is null");
            throw new IllegalArgumentException("string is null");
        }

        if (s.startsWith("/") || s.startsWith("\\")) {
            return s.substring(1, s.length());
        } else {
            return s;
        }
    }

    /**
     * Converts a string to a URL.
     *
     * @param path the string to convert to a URL.
     *
     * @return a URL for the specified object, or null if a URL could not be
     * created.
     *
     * @see #makeURL(Object)
     * @see #makeURL(Object, String)
     */
    public static URL makeURL(String path) {
        try {
            return new URL(path);
        } catch (MalformedURLException e) {
            return null;
        }
    }

   
    /**
     * Close a stream and catch any {@link IOException} generated in the
     * process. This supports any object that implements the
     * {@link java.io.Closeable} interface.
     *
     * @param stream the stream to close. If null, this method does nothing.
     * @param name the name of the stream to place in the log message if an
     * exception is encountered.
     */
    public static void closeStream(Object stream, String name) {
        if (stream == null) {
            return;
        }

        try {
            if (stream instanceof Closeable) {
                ((Closeable) stream).close();
            } else {
                logger.warn("stream type not supported: ", name);
            }
        } catch (IOException e) {

            logger.warn("stream type not supported: ", name != null ? name : "Unknown");
        }
    }

    /**
     * Returns the file suffix string corresponding to the specified mime type
     * string. The returned suffix starts with the period character '.' followed
     * by the mime type's subtype, as in: ".[subtype]".
     *
     * @param mimeType the mime type who's suffix is returned.
     *
     * @return the file suffix for the specified mime type, with a leading ".".
     *
     * @throws IllegalArgumentException if the mime type is null or malformed.
     */
    public static String makeSuffixForMimeType(String mimeType) {
        if (mimeType == null) {
            logger.error("mime type is null");
            throw new IllegalStateException("mime type is null");
        }

        if (!mimeType.contains("/") || mimeType.endsWith("/")) {
            logger.error("invalid image format");
            throw new IllegalStateException("invalid image format");
        }

        // Remove any parameters appended to this mime type before using it as a key in the mimeTypeToSuffixMap. Mime
        // parameters do not change the mapping from mime type to suffix.
        int paramIndex = mimeType.indexOf(";");
        if (paramIndex != -1) {
            mimeType = mimeType.substring(0, paramIndex);
        }

        String suffix = mimeTypeToSuffixMap.get(mimeType);

        if (suffix == null) {
            suffix = mimeType.substring(mimeType.lastIndexOf("/") + 1);
        }

        suffix = suffix.replaceFirst("bil32", "bil"); // if bil32, replace with "bil" suffix.
        suffix = suffix.replaceFirst("bil16", "bil"); // if bil16, replace with "bil" suffix.

        return "." + suffix;
    }

    protected static Map<String, String> mimeTypeToSuffixMap = new HashMap<String, String>();

    static {
        mimeTypeToSuffixMap.put("application/acad", "dwg");
        mimeTypeToSuffixMap.put("application/bil", "bil");
        mimeTypeToSuffixMap.put("application/bil16", "bil");
        mimeTypeToSuffixMap.put("application/bil32", "bil");
        mimeTypeToSuffixMap.put("application/dxf", "dxf");
        mimeTypeToSuffixMap.put("application/octet-stream", "bin");
        mimeTypeToSuffixMap.put("application/pdf", "pdf");
        mimeTypeToSuffixMap.put("application/rss+xml", "xml");
        mimeTypeToSuffixMap.put("application/rtf", "rtf");
        mimeTypeToSuffixMap.put("application/sla", "slt");
        mimeTypeToSuffixMap.put("application/vnd.google-earth.kml+xml", "kml");
        mimeTypeToSuffixMap.put("application/vnd.google-earth.kmz", "kmz");
        mimeTypeToSuffixMap.put("application/vnd.ogc.gml+xml", "gml");
        mimeTypeToSuffixMap.put("application/x-gzip", "gz");
        mimeTypeToSuffixMap.put("application/xml", "xml");
        mimeTypeToSuffixMap.put("application/zip", "zip");
        mimeTypeToSuffixMap.put("multipart/zip", "zip");
        mimeTypeToSuffixMap.put("multipart/x-gzip", "gzip");
    }

}
