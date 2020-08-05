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
package com.phyzicsz.rocket.symbol;

import com.phyzicsz.rocket.symbol.common.SymbologyConstants;
import com.phyzicsz.rocket.symbol.render.MilStdSymbolRenderer;
import com.phyzicsz.rocket.symbol.common.SymbolServiceProperties;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

/**
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public class RocketSymbolService {

    private final MilStdSymbolRenderer renderer = new MilStdSymbolRenderer();
//    private final KVStore kv = new KVStore();
    private final SymbolServiceProperties props = new SymbolServiceProperties();

    public RocketSymbolService() {

    }

    public RocketSymbolService withShowIcon(final Boolean value) {
        props.put(SymbolServiceProperties.SHOW_ICON, value);
        return this;
    }

    public RocketSymbolService withShowFrame(final Boolean value) {
        props.put(SymbolServiceProperties.SHOW_FRAME, value);
        return this;
    }

    public RocketSymbolService withShowFill(final Boolean value) {
        props.put(SymbolServiceProperties.SHOW_FILL, value);
        return this;
    }

    public RocketSymbolService withFillColor(final Color value) {
        props.put(SymbolServiceProperties.COLOR, value);
        
        return this;
    }

    public BufferedImage asBufferedImage(final String symbolCode) throws IOException {
        return renderer.createIcon(symbolCode, props);
    }

    public byte[] asPng(final String symbolCode) throws IOException {
        BufferedImage image = renderer.createIcon(symbolCode, props);

        byte[] bytes;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            baos.flush();
            bytes = baos.toByteArray();
        }
        return bytes;
    }
    
    public void pngToFile(final String symbolCode, final String path) throws IOException {
        BufferedImage image = renderer.createIcon(symbolCode, props);

        byte[] bytes;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            baos.flush();
            bytes = baos.toByteArray();
        }
        
        Path filepath = Paths.get(path);
        Files.write(filepath, bytes);
    }
    
    public void pngToFile(final String symbolCode, final Path path) throws IOException {
        BufferedImage image = renderer.createIcon(symbolCode, props);

        byte[] bytes;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            baos.flush();
            bytes = baos.toByteArray();
        }
        
        Files.write(path, bytes);
    }

}
