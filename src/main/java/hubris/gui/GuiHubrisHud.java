package hubris.gui;

import net.minecraft.client.Minecraft;

import java.text.DecimalFormat;

public class GuiHubrisHud {
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private static final DecimalFormat formatter = new DecimalFormat("#.##");

    public static double hubris = 0;

    public static void Render(){
        minecraft.fontRenderer.drawString("HUBRIS: "+formatter.format(hubris), 10, 10, 0xFFFFFF);
    }
}
