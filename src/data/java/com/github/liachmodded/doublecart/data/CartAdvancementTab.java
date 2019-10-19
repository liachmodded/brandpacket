/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.github.liachmodded.doublecart.data;

import com.github.liachmodded.doublecart.DoubleCartEntry;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.TranslatableText;

import java.util.function.Consumer;

public class CartAdvancementTab implements Consumer<Consumer<Advancement>> {

    @Override
    public void accept(Consumer<Advancement> consumer) {
        Advancement advancement = Advancement.Task.create()
                .display(makeDisplay(Items.MUSIC_DISC_STRAD , "dc.title", "dc.desc"))
                .build(DoubleCartEntry.name("my_advancement"));
        consumer.accept(advancement);
    }

    public static AdvancementDisplay makeDisplay(ItemConvertible item, String titleKey, String descKey) {
        return new AdvancementDisplay(new ItemStack(item.asItem()), new TranslatableText(titleKey), new TranslatableText(descKey), null, AdvancementFrame.TASK, true, true, false);
    }
}
