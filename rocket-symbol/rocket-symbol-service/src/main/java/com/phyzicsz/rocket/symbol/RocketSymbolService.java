/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package com.phyzicsz.rocket.symbol;

import com.phyzicsz.rocket.symbol.common.SymbologyConstants;
import com.phyzicsz.rocket.symbol.render.MilStdSymbolRenderer;
import com.phyzicsz.rocket.symbol.kvstore.KVKey;
import com.phyzicsz.rocket.symbol.kvstore.KVStore;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;


public class RocketSymbolService {

    private final MilStdSymbolRenderer renderer = new MilStdSymbolRenderer();
    private final KVStore kv = new KVStore();

    public RocketSymbolService() {

    }

    public RocketSymbolService withShowIcon(final Boolean value) {
        kv.setValue(SymbologyConstants.SHOW_ICON, value);
        return this;
    }

    public RocketSymbolService withShowFrame(final Boolean value) {
        kv.setValue(SymbologyConstants.SHOW_FRAME, value);
        return this;
    }

    public RocketSymbolService withShowFill(final Boolean value) {
        kv.setValue(SymbologyConstants.SHOW_FILL, value);
        return this;
    }

    public RocketSymbolService withFillColor(final Color value) {
        kv.setValue(KVKey.COLOR, value);
        return this;
    }

    public BufferedImage asBufferedImage(final String symbolCode) throws IOException {
        return renderer.createIcon(symbolCode, kv);
    }

    public byte[] asPng(final String symbolCode) throws IOException {
        BufferedImage image = renderer.createIcon(symbolCode, kv);

        byte[] bytes;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            baos.flush();
            bytes = baos.toByteArray();
        }
        return bytes;
    }
    
    public void pngToFile(final String symbolCode, final String path) throws IOException {
        BufferedImage image = renderer.createIcon(symbolCode, kv);

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
        BufferedImage image = renderer.createIcon(symbolCode, kv);

        byte[] bytes;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            baos.flush();
            bytes = baos.toByteArray();
        }
        
        Files.write(path, bytes);
    }

}
