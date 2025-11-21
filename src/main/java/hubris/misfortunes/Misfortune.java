package hubris.misfortunes;

import hubris.Hubris;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class Misfortune extends IForgeRegistryEntry.Impl<Misfortune> {
    public String announcement;

    /**
     * @param name The name of the misfortune to be used in its id
     * @param announcement The text sent to a player when a misfortune is applied
     */

    public Misfortune(String name, String announcement) {
        this.setRegistryName(Hubris.MODID, name);
        this.announcement = announcement;
    }

    public Misfortune(String name) {
        this.setRegistryName(Hubris.MODID, name);
    }

    public void Apply(EntityPlayer plr){
        if (this.announcement != null){
            plr.sendMessage(new TextComponentString(this.announcement));
        }
    }
}