package hubris.capability;

import hubris.handlers.PacketHandler;
import hubris.packets.PacketHubris;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class CapabilityHubris implements ICapabilityHubris {
    private final Entity entity;
    private double hubrisData = 0;

    public CapabilityHubris(){
        this(null);
    }

    public CapabilityHubris(Entity entity /*, constructor parameters*/) {
        this.entity = entity;
    }

    public void replicate(){
        EntityPlayerMP plr = (EntityPlayerMP) this.entity;
        PacketHandler.networkChannel.sendTo(new PacketHubris(this.hubrisData), plr);
    }

    @Override
    public double getHubris(){
        return this.hubrisData;
    }

    @Override
    public void changeHubris(double change){
        EntityPlayerMP plr = (EntityPlayerMP) this.entity;
        this.hubrisData=Math.max(0, Math.round((this.hubrisData+change) * 100.0) / 100.0);
        replicate();
    }

    @Override
    public NBTTagCompound writeToNBT() {
        NBTTagCompound tags = new NBTTagCompound();
        tags.setDouble("hubris", this.hubrisData);
        return tags;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.hubrisData = nbt.getInteger("hubris");
    }
}
