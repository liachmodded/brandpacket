/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.github.liachmodded.brandpacket.mixin;

import com.github.liachmodded.brandpacket.BrandPacket;
import com.github.liachmodded.brandpacket.BrandPacketSettings;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

  @Redirect(method = "onGameJoin", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;send(Lnet/minecraft/network/Packet;)V"))
  public void handleGameJoin(ClientConnection connection, Packet<?> packet) {
    if (!(packet instanceof CustomPayloadC2SPacket payloadPacket) || !payloadPacket.getChannel().equals(CustomPayloadC2SPacket.BRAND)) {
      connection.send(packet);
      return;
    }
    BrandPacketSettings settings = BrandPacket.getInstance().getSettings();
    if (settings.noPacket.get()) {
      return;
    }

    connection.send(new CustomPayloadC2SPacket(CustomPayloadC2SPacket.BRAND, new PacketByteBuf(Unpooled.buffer()).writeString(settings.brand.get())));
  }
}
