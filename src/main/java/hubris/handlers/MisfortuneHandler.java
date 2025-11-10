package hubris.handlers;

import hubris.misfortunes.MisfortuneBedExplosion;
import hubris.misfortunes.MisfortuneClipping;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Random;

public class MisfortuneHandler {
    static Random random = new Random();
    public static void ApplyMisfortune(EntityPlayer plr){
        switch (random.nextInt(2)){
            case 0:
                MisfortuneBedExplosion.apply(plr);
                break;
            case 1:
                MisfortuneClipping.apply(plr);
                break;
        }
    }
}
