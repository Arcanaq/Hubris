package hubris.misfortunes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MisfortuneExplosion extends Misfortune {
    public static final MisfortuneExplosion INSTANCE = new MisfortuneExplosion();

    public MisfortuneExplosion() {
        super("misfortune_explosion");
    }

    @Override
    public void Apply(EntityPlayer plr){
        super.Apply(plr);
        World world = plr.world;
        BlockPos pos = plr.getPosition();
        world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 10, true);
    }
}