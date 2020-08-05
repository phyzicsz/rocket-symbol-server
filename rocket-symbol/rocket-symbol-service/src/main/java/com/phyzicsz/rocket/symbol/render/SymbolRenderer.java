/*
 * Copyright 2020 phyzicsz <phyzics.z@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phyzicsz.rocket.symbol.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import com.phyzicsz.rocket.symbol.common.SymbolServiceProperties;

/**
 * Retrieves icons for symbols in a symbol set from a local disk or the network.
 * Typically, an icon retriever will be implemented for a specific symbol set.
 * 
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public interface SymbolRenderer {

    /**
     * Create an icon to represent a symbol in a symbol set.
     *
     * @param symbolId Identifier for the symbol. The format of this identifier
     * depends on the symbology set.
     * @param props
     *
     * @return A BufferedImage containing the requested icon, or null if the
     * icon cannot be retrieved.
     * @throws java.io.IOException
     */
    BufferedImage createIcon(String symbolId, SymbolServiceProperties props) throws IOException;
}
