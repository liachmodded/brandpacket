/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.github.liachmodded.brandpacket;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public final class BrandPacket implements ModInitializer {

  static final Logger LOGGER = LogManager.getLogger("brandpacket");
  private static BrandPacket instance;
  private BrandPacketSettings settings;

  public static BrandPacket getInstance() {
    return instance;
  }

  public BrandPacketSettings getSettings() {
    return settings;
  }

  public void setSettings(BrandPacketSettings settings) {
    this.settings = settings;
  }

  @Override
  public void onInitialize() {
    instance = this;
    FabricLoader loader = FabricLoader.getInstance();
    Path settingFile = loader.getConfigDir().resolve("brandpacket.properties");
    settings = BrandPacketSettings.loadFrom(settingFile);
  }
}
