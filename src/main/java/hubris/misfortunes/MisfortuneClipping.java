package hubris.misfortunes;

import hubris.potion.PotionClipping;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class MisfortuneClipping extends Misfortune {
    public static final MisfortuneClipping INSTANCE = new MisfortuneClipping();

    public MisfortuneClipping() {
        super("misfortune_clipping", "See ya!");
    }

    @Override
    public void Apply(EntityPlayer plr){
        super.Apply(plr);
        plr.addPotionEffect(new PotionEffect(PotionClipping.INSTANCE, 200));
    }
}