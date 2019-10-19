/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.github.liachmodded.brandpacket;

import com.github.liachmodded.brandpacket.mixin.CustomPayloadC2SPacketAccessor;
import java.nio.file.Path;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.Packet;
import net.minecraft.server.network.packet.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class BrandPacket implements ModInitializer {

  static final Logger LOGGER = LogManager.getLogger("brandpacket");
  private static BrandPacket instance;
  private BrandPacketSettings settings;

  public static BrandPacket getInstance() {
    return instance;
  }

  public static Identifier getPayloadChannel(Packet<?> packet) {
    return ((CustomPayloadC2SPacketAccessor) packet).getChannel();
  }
  
  public static PacketByteBuf getPayloadData(CustomPayloadC2SPacket packet) {
    return ((CustomPayloadC2SPacketAccessor) packet).getData();
  }

  public BrandPacketSettings getSettings() {
    return settings;
  }

  @Override
  public void onInitialize() {
    instance = this;
    FabricLoader loader = FabricLoader.getInstance();
    Path settingFile = loader.getConfigDirectory().toPath().resolve("brandpacket.properties");
    settings = BrandPacketSettings.loadFrom(settingFile);
  }
}
