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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for icon retrievers. This class provides methods for loading and
 * manipulating icons.
 * <h2>Icon retrieval</h2>
 * <p>
 * Each symbol in a symbology set must have a unique identifier. The
 * IconRetriever's job is to create a BufferedImage to represent a symbol given
 * the symbol identifier. Usually this means retrieving an image from the file
 * system or the network, and optionally manipulating the symbol (for example,
 * changing the color to represent a hostile or friendly entity).
 * <p>
 * Each instance of AbstractIconRetriever is configured with a retrieval path
 * which specifies the location of a symbol repository on the file system or the
 * network. {@link #readImage(String) readImage} retrieves images relative to
 * this base path. The retrieval path may be a file URL to a directory on the
 * local file system (for example, file:///symbols/mil-std-2525). A URL to a
 * network resource (http://myserver.com/milstd2525/), or a URL to a JAR or ZIP
 * file (jar:file:milstd2525-symbols.zip!).
 * <p>
 * A simple icon retriever might use a symbol repository that is a simple
 * directory of PNG files, where each file name matches a symbol identifier.
 * Such an icon retriever could be implemented like this:
 * <pre>
 * class SimpleIconRetriever extends AbstractIconRetriever
 * {
 *     public BufferedImage createIcon(String symbolId)
 *     {
 *         // Retrieves retrievalPath/symbolId.png
 *         return this.readImage(symbolId + ".png");
 *     }
 * }
 * </pre>
 * <h2>Composite icons</h2>
 * <p>
 * Complicated symbols may be made up of several different graphical elements. {@link
 * #drawImage(java.awt.image.BufferedImage, java.awt.image.BufferedImage) drawImage}
 * helps build a complex symbol from simple pieces. For example, if a symbol is
 * composed of a frame and an icon, the icon retriever could load the frame and
 * icon independently, draw the icon over the frame, and return the composite
 * image:
 * <pre>
 * // Load the frame and icon as separate pieces.
 * BufferedImage frame = this.readImage("path/to/frame.png");
 * BufferedImage icon = this.readImage("path/to/icon.png");
 *
 * // Draw the icon on top of the frame. This call modifies the frame image.
 * BufferedImage fullImage = this.drawImage(icon, frame);
 *
 * // Return the composite image.
 * return fullImage;
 * </pre>
 * <h2>Changing the color of an icon</h2>
 * <p>
 * {@link #multiply(java.awt.image.BufferedImage, java.awt.Color) multiply} can
 * change the color of an image by multiplying each pixel in the image by a
 * color. The multiplication color will replace any white pixels and black
 * pixels will be unaffected. For example, a symbol set in which hostile symbols
 * are drawn in red and friendly symbols are drawn in green could be implemented
 * by creating white icons, and then multiplying by either red or green when the
 * retriever constructs the icon.
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public abstract class AbstractMilStdSymbolRenderer implements SymbolRenderer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMilStdSymbolRenderer.class);
    /**
     * Path in the file system or network to the symbol repository.
     */
    protected final String baseImagePath = "/symbols";

    public AbstractMilStdSymbolRenderer() {

    }

    public String getBasePath() {
        return this.baseImagePath;
    }

    protected BufferedImage readImage(String path) {
        if (path == null) {
            logger.error("retrieverPath is null");
            throw new IllegalArgumentException("retrieverPath is null");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(this.getBasePath());
        sb.append("/");
        sb.append(path);

         try (InputStream is = getClass().getResourceAsStream(sb.toString())) {
             if (null != is) {
                 return ImageIO.read(is);
             }
             return ImageIO.read(is);
        }catch (IOException ex) {
            logger.error("ExceptionWhileReading", ex);
        }
        return null;
    }

    /**
     * Draw one image into another image. The image is drawn at location (0, 0).
     *
     * @param src Image to draw.
     * @param dest Image to draw into.
     *
     * @return {@code dest} BufferedImage.
     */
    protected BufferedImage drawImage(BufferedImage src, BufferedImage dest) {
        if (src == null) {
            logger.error("src is null");
            throw new IllegalArgumentException("src is null");
        }

        if (dest == null) {
            logger.error("dest is null");
            throw new IllegalArgumentException("dest is null");
        }

        Graphics2D g = null;
        try {
            g = dest.createGraphics();
            g.drawImage(src, 0, 0, null);
        } finally {
            if (g != null) {
                g.dispose();
            }
        }

        return dest;
    }

    /**
     * Multiply each pixel in an image by a color. White pixels are replaced by
     * the multiplication color, black pixels are unaffected.
     *
     * @param image Image to operate on.
     * @param color Color to multiply by.
     *
     * @see #replaceColor(java.awt.image.BufferedImage, java.awt.Color)
     */
    protected void multiply(BufferedImage image, Color color) {
        if (image == null) {
            logger.error("image is null");
            throw new IllegalArgumentException("image is null");
        }

        if (color == null) {
            logger.error("color is null");
            throw new IllegalArgumentException("color is null");
        }

        int w = image.getWidth();
        int h = image.getHeight();

        if (w == 0 || h == 0) {
            return;
        }

        int[] pixels = new int[w];
        int c = color.getRGB();
        float ca = ((c >> 24) & 0xff) / 255f;
        float cr = ((c >> 16) & 0xff) / 255f;
        float cg = ((c >> 8) & 0xff) / 255f;
        float cb = (c & 0xff) / 255f;

        for (int y = 0; y < h; y++) {
            image.getRGB(0, y, w, 1, pixels, 0, w);

            for (int x = 0; x < w; x++) {
                int s = pixels[x];
                float sa = ((s >> 24) & 0xff) / 255f;
                float sr = ((s >> 16) & 0xff) / 255f;
                float sg = ((s >> 8) & 0xff) / 255f;
                float sb = (s & 0xff) / 255f;

                int fa = (int) (ca * sa * 255 + 0.5);
                int fr = (int) (cr * sr * 255 + 0.5);
                int fg = (int) (cg * sg * 255 + 0.5);
                int fb = (int) (cb * sb * 255 + 0.5);

                pixels[x] = (fa & 0xff) << 24
                        | (fr & 0xff) << 16
                        | (fg & 0xff) << 8
                        | (fb & 0xff);
            }

            image.setRGB(0, y, w, 1, pixels, 0, w);
        }
    }

    /**
     * Replace the color of each pixel in an image. This method retains the
     * alpha channel of each pixel, but completely replaces the red, green, and
     * blue components with the replacement color. Unlike {@link
     * #multiply(java.awt.image.BufferedImage, java.awt.Color) multiply}, this
     * method changes the color of all pixels.
     *
     * @param image Image to operate on.
     * @param color Color to apply to to each pixel.
     *
     * @see #multiply(java.awt.image.BufferedImage, java.awt.Color)
     */
    protected void replaceColor(BufferedImage image, Color color) {
        if (image == null) {
            logger.error("image is null");
            throw new IllegalArgumentException("image is null");
        }

        if (color == null) {
            logger.error("color is null");
            throw new IllegalArgumentException("color is null");
        }

        int w = image.getWidth();
        int h = image.getHeight();

        if (w == 0 || h == 0) {
            return;
        }

        int[] pixels = new int[w];
        int c = color.getRGB();
        float cr = ((c >> 16) & 0xff) / 255f;
        float cg = ((c >> 8) & 0xff) / 255f;
        float cb = (c & 0xff) / 255f;

        for (int y = 0; y < h; y++) {
            image.getRGB(0, y, w, 1, pixels, 0, w);

            for (int x = 0; x < w; x++) {
                int s = pixels[x];
                float sa = ((s >> 24) & 0xff) / 255f;

                int fa = (int) (sa * 255 + 0.5);
                int fr = (int) (cr * 255 + 0.5);
                int fg = (int) (cg * 255 + 0.5);
                int fb = (int) (cb * 255 + 0.5);

                pixels[x] = (fa & 0xff) << 24
                        | (fr & 0xff) << 16
                        | (fg & 0xff) << 8
                        | (fb & 0xff);
            }

            image.setRGB(0, y, w, 1, pixels, 0, w);
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.baseImagePath);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractMilStdSymbolRenderer)) {
            return false;
        }
        final AbstractMilStdSymbolRenderer other = (AbstractMilStdSymbolRenderer) obj;
        if (!Objects.equals(this.baseImagePath, other.baseImagePath)) {
            return false;
        }
        return true;
    }

}
