package hubris.gui;

import hubris.enums.EnumHudStyle;
import hubris.handlers.ForgeConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.text.DecimalFormat;

public class GuiHubrisHud {
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private static final DecimalFormat formatter = new DecimalFormat("#.##");

    public static double hubris = 0.0;

    public static void Render(){
        if (ForgeConfigHandler.ClientConfig.HudStyle == EnumHudStyle.NONE) return;
        if (ForgeConfigHandler.ClientConfig.DynHUD && hubris <= 0.0) return;
        if (ForgeConfigHandler.ClientConfig.HudStyle == EnumHudStyle.BAR || ForgeConfigHandler.ClientConfig.HudStyle == EnumHudStyle.HYBRID) {
            Gui.drawRect(ForgeConfigHandler.ClientConfig.HudX, ForgeConfigHandler.ClientConfig.HudY, 6 + (int) (hubris * 5), 20, 0xFFFF0000);
        }
        if (ForgeConfigHandler.ClientConfig.HudStyle == EnumHudStyle.LABEL || ForgeConfigHandler.ClientConfig.HudStyle == EnumHudStyle.HYBRID) {
            minecraft.fontRenderer.drawString("HUBRIS: " + formatter.format(hubris), ForgeConfigHandler.ClientConfig.HudX+4, ForgeConfigHandler.ClientConfig.HudY+4, 0xFFFFFF);
        }
    }
}