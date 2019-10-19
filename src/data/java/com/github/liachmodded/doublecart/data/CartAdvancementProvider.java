/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.github.liachmodded.doublecart.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.advancement.Advancement;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.server.EndTabAdvancementGenerator;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CartAdvancementProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator root;
    private final List<Consumer<Consumer<Advancement>>> tabGenerators = ImmutableList.of(new CartAdvancementTab());

    public CartAdvancementProvider(DataGenerator dataGenerator_1) {
        this.root = dataGenerator_1;
    }

    public void run(DataCache dataCache_1) throws IOException {
        Path path_1 = this.root.getOutput();
        Set<Identifier> set_1 = Sets.newHashSet();
        Consumer<Advancement> consumer_1 = (advancement_1) -> {
            if (!set_1.add(advancement_1.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement_1.getId());
            }
            Path path_2 = getOutput(path_1, advancement_1);

            try {
                DataProvider.writeToPath(GSON, dataCache_1, advancement_1.createTask().toJson(), path_2);
            } catch (IOException var6) {
                LOGGER.error("Couldn't save advancement {}", path_2, var6);
            }
        };
        for (Consumer<Consumer<Advancement>> consumer_2 : this.tabGenerators) {
            consumer_2.accept(consumer_1);
        }
    }

    private static Path getOutput(Path path_1, Advancement advancement_1) {
        return path_1.resolve("data/" + advancement_1.getId().getNamespace() + "/advancements/" + advancement_1.getId().getPath() + ".json");
    }

    public String getName() {
        return "Advancements";
    }
}
