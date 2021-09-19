package com.github.liachmodded.brandpacket.modmenu;

import com.github.liachmodded.brandpacket.BrandPacket;
import com.github.liachmodded.brandpacket.BrandPacketSettings;
import io.github.prospector.modmenu.api.ModMenuApi;
import java.util.function.Function;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;

public final class BrandPacketConfigHook implements ModMenuApi {

  @Override
  public String getModId() {
    return "brandpacket";
  }

  @Override
  public Function<Screen, ? extends Screen> getConfigScreenFactory() {
    return ConfigScreen::new;
  }

  static final class ConfigScreen extends Screen {

    private final Screen parent;
    private BrandPacketSettings currentSettings;
    private TextFieldWidget brandField;
    private ButtonWidget noPacketButton;

    ConfigScreen(Screen parent) {
      super(new LiteralText(""));
      this.parent = parent;
    }

    @Override
    public void init() {
      super.init();

      currentSettings = BrandPacket.getInstance().getSettings();

      this.brandField = new TextFieldWidget(this.font, this.width / 2 - 100, 60, 200, 20, "brand");
      this.brandField.setText(this.currentSettings.brand.get());
      this.brandField.setChangedListener((string_1) -> currentSettings = currentSettings.brand.set(this.brandField.getText()));
      this.children.add(this.brandField);

      this.addButton(new ButtonWidget(this.width / 2 - 155, 100, 150, 20, "no-packet",
          (buttonWidget_1) -> currentSettings = currentSettings.noPacket.set(!this.currentSettings.noPacket.get())) {
        @Override
        public String getMessage() {
          return "no-packet: " + currentSettings.noPacket.get();
        }
      });

      this.addButton(new ButtonWidget(this.width / 2 - 155, this.height - 28, 150, 20, I18n.translate("gui.done"), buttonWidget -> {
        BrandPacket.getInstance().setSettings(this.currentSettings);
        this.minecraft.openScreen(this.parent);
      }));
      this.addButton(new ButtonWidget(this.width / 2 + 5, this.height - 28, 150, 20, I18n.translate("gui.cancel"),
          buttonWidget -> this.minecraft.openScreen(this.parent)));
    }
  }
}
