package hubris.misfortunes;

import hubris.potion.PotionClipping;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;

public class MisfortuneClipping extends MisfortuneBase {

    public MisfortuneClipping() {

    }

    public static void apply(EntityPlayer plr){
        plr.sendMessage(new TextComponentString("See ya!"));
        plr.addPotionEffect(new PotionEffect(PotionClipping.INSTANCE, 200));
    }
}