/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.github.liachmodded.brandpacket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import net.minecraft.server.dedicated.AbstractPropertiesHandler;

public final class BrandPacketSettings extends AbstractPropertiesHandler<BrandPacketSettings> {

  private final Properties properties;
  public final boolean noPacket;
  public final String brand;

  private BrandPacketSettings(Properties properties) {
    super(properties);
    this.properties = properties;
    this.noPacket = parseBoolean("no-packet", false);
    this.brand = getString("brand", "vanilla");
  }

  @Override
  protected BrandPacketSettings create(Properties var1) {
    return new BrandPacketSettings(var1);
  }

  static BrandPacketSettings loadFrom(Path path) {
    Properties properties = new Properties();
    if (Files.exists(path)) {
      try (BufferedReader reader = Files.newBufferedReader(path)) {
        properties.load(reader);
      } catch (IOException ignored) {
      }
    }

    BrandPacketSettings ret = new BrandPacketSettings(properties);
    ret.store(path);
    return ret;
  }

  @Override
  public void store(Path path) {
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      this.properties.store(writer, "brandpacket settings");
    } catch (IOException ex) {
      BrandPacket.LOGGER.error("Cannot save config file to {}!", path, ex);
    }
  }
}
