package hubris.handlers;

import hubris.misfortunes.Misfortune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MisfortuneHandler {
    private static final IForgeRegistry<Misfortune> registry = GameRegistry.findRegistry(Misfortune.class);
    private static Random random = new Random();

    public static void ApplyMisfortune(EntityPlayer plr){
        List<Misfortune> misfortunes = new ArrayList<>(registry.getValuesCollection());
        misfortunes.get(random.nextInt(misfortunes.size())).Apply(plr);
    }
}
