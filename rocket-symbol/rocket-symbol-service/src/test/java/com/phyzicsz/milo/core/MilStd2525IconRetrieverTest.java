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

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
public class MilStd2525IconRetrieverTest {

    public MilStd2525IconRetrieverTest() {
    }

    /**
     * Test of createIcon method, of class MilStd2525IconRetriever.
     */
    @Test
    public void testCreateIcon() throws Exception {
        System.out.println("createIcon");
        String sidc = "SFUPSK---------";
        AVList params = null;
        MilStd2525IconRetriever instance = new MilStd2525IconRetriever("/symbols");

        BufferedImage image = instance.createIcon(sidc, params);

        File outputfile = new File("/tmp/"+ sidc + ".png");
        ImageIO.write(image, "png", outputfile);

    }

}
