/*
 * Copyright 2014 Higher Frequency Trading http://www.higherfrequencytrading.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.chronicle.map;

import net.openhft.lang.model.DataValueClasses;
import net.openhft.lang.values.IntValue;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class DefaultValueTest {

    @Test
    public void test() throws IllegalAccessException, InstantiationException, IOException {
        File file = Builder.getPersistenceFile();
        try {

            ArrayList<Integer> defaultValue = new ArrayList<Integer>();
            defaultValue.add(42);
            try (ChronicleMap<String, List<Integer>> map = ChronicleMapBuilder
                    .of(String.class, (Class<List<Integer>>) ((Class) List.class))
                    .defaultValue(defaultValue)
                    .create(file)) {

                assertEquals(defaultValue, map.get("a"));
                assertEquals(1, map.size());

                map.put("b", Arrays.asList(1, 2));
                assertEquals(Arrays.asList(1, 2), map.get("b"));
            }

            try (ChronicleMap<String, List<Integer>> map = ChronicleMapBuilder
                    .of(String.class, (Class<List<Integer>>) ((Class) List.class)).create(file)) {
                assertEquals(defaultValue, map.get("c"));
            }
        } finally {
            file.delete();
        }
    }
}
