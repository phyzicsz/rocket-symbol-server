/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package com.phyzicsz.rocket.symbol.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import com.phyzicsz.rocket.symbol.kvstore.AbstractKVStore;

/**
 * Retrieves icons for symbols in a symbol set from a local disk or the network.
 * Typically, an icon retriever will be implemented for a specific symbol set.
 * For example, the
 * {@link gov.nasa.worldwind.symbology.milstd2525.MilStd2525IconRetriever}
 * retrieves icons for symbols in the MIL-STD-2525 symbology set. See the <a href="https://worldwind.arc.nasa.gov/java/tutorials/icon-retriever/"
 * target="_blank">Icon Retriever Usage Guide</a> for more information.
 *
 * @author ccrick
 * @version $Id: IconRetriever.java 1171 2013-02-11 21:45:02Z dcollins $
 */
public interface SymbolRenderer {

    /**
     * Create an icon to represent a symbol in a symbol set.
     *
     * @param symbolId Identifier for the symbol. The format of this identifier
     * depends on the symbology set.
     * @param params Parameters that affect icon retrieval.
     *
     * @return A BufferedImage containing the requested icon, or null if the
     * icon cannot be retrieved.
     */
    BufferedImage createIcon(String symbolId, AbstractKVStore params) throws IOException;
}
