package hubris.misfortunes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MisfortunePotion extends Misfortune {
    public static final MisfortunePotion INSTANCE = new MisfortunePotion();

    private static List<Potion> potions;
    private static Random random = new Random();

    public MisfortunePotion() {
        super("misfortune_potion");
        potions = new ArrayList<>();
        for (Potion potion : ForgeRegistries.POTIONS){
            if (potion.isBadEffect()) potions.add(potion);
        }
    }

    @Override
    public void Apply(EntityPlayer plr){
        super.Apply(plr);
        for (int i=0; i<3; i++){
            plr.addPotionEffect(new PotionEffect(potions.get(random.nextInt(potions.size())), 300));
        }
    }
}