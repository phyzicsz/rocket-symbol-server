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
package com.phyzicsz.milo.core;

import com.phyzicsz.rocket.symbol.RocketSymbolService;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Disabled;

/**
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public class RocketSymbolServerTestImageGenerator {

    public RocketSymbolServerTestImageGenerator() {
    }

    /**
     * Test of createIcon method, of class MilStd2525IconRetriever.
     */
    @Test
    @Disabled
    public void testCreateIcon() throws Exception {
        System.out.println("createIcon");
        String sidc = "SFUPSK---------";
        RocketSymbolService instance = new RocketSymbolService();

        Path path = Paths.get("/tmp", sidc + ".png");
        instance.pngToFile(sidc, path);
    }

    @Test
    @Disabled
    public void generateFriendSymbology() throws IOException {
        List<String> friend = Arrays.asList(
                "SFPP------*****",
                "SFPPS-----*****",
                "SFPPV-----*****",
                "SFPPT-----*****",
                "SFPPL-----*****",
                "SFAP------*****",
                "SFAPM-----*****",
                "SFAPMFKB--*****",
                "SFAPMFO---*****",
                "SFAPMFQA--*****",
                "SFAPMFM---*****",
                "SFAPML----*****",
                "SFAPWMS---*****",
                "SFGPUUSO--*****");

        RocketSymbolService instance = new RocketSymbolService();

        for (String symbolCode : friend) {
            Path path = Paths.get("/tmp", symbolCode + ".png");
            instance.pngToFile(symbolCode, path);
        }
    }

    @Test
    @Disabled
    public void generateHostileSymbology() throws IOException {
        List<String> hostile = Arrays.asList(
                "SHPP------*****",
                "SHPPS-----*****",
                "SHPPV-----*****",
                "SHPPT-----*****",
                "SHPPL-----*****",
                "SHAP------*****",
                "SHAPM-----*****",
                "SHAPMFKB--*****",
                "SHAPMFO---*****",
                "SHAPMFQA--*****",
                "SHAPMFM---*****",
                "SHAPML----*****",
                "SHAPWMS---*****",
                "SHGPUUSO--*****");

        RocketSymbolService instance = new RocketSymbolService();

        for (String symbolCode : hostile) {
            Path path = Paths.get("/tmp", symbolCode + ".png");
            instance.pngToFile(symbolCode, path);
        }
    }

    @Test
    @Disabled
    public void generateNeutralSymbology() throws IOException {
        List<String> neutral = Arrays.asList(
                "SNPP------*****",
                "SNPPS-----*****",
                "SNPPV-----*****",
                "SNPPT-----*****",
                "SNPPL-----*****",
                "SNAP------*****",
                "SNAPM-----*****",
                "SNAPMFKB--*****",
                "SNAPMFO---*****",
                "SNAPMFQA--*****",
                "SNAPMFM---*****",
                "SNAPML----*****",
                "SNAPWMS---*****",
                "SNGPUUSO--*****");

        RocketSymbolService instance = new RocketSymbolService();

        for (String symbolCode : neutral) {
            Path path = Paths.get("/tmp", symbolCode + ".png");
            instance.pngToFile(symbolCode, path);
        }
    }

    @Test
//    @Disabled
    public void generateUnknownSymbology() throws IOException {
        List<String> unknown = Arrays.asList(
                "SUPP------*****",
                "SUPPS-----*****",
                "SUPPV-----*****",
                "SUPPT-----*****",
                "SUPPL-----*****",
                "SUAP------*****",
                "SUAPM-----*****",
                "SUAPMFKB--*****",
                "SUAPMFO---*****",
                "SUAPMFQA--*****",
                "SUAPMFM---*****",
                "SUAPML----*****",
                "SUAPWMS---*****",
                "SUGPUUSO--*****");

        RocketSymbolService instance = new RocketSymbolService();

        for (String symbolCode : unknown) {
            Path path = Paths.get("/tmp", symbolCode + ".png");
            instance.pngToFile(symbolCode, path);
        }
    }

}
