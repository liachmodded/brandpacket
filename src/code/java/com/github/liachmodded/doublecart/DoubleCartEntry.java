/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.github.liachmodded.doublecart;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class DoubleCartEntry implements ModInitializer {

    public static final String ID = "doublecart";

    public static Identifier name(String name) {
        return new Identifier(ID, name);
    }

    @Override
    public void onInitialize() {

    }
}
