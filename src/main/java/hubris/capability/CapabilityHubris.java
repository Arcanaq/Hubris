package hubris.capability;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;

/**
 * This is your implementation of the ICapability. All handling should be encapsulated and only exposed via the methods of ICapability
 */
public class CapabilityHubris implements ICapabilityHubris {
    private final Entity entity; //Can be TileEntity, Entity, Village, World, Chunk, ItemStack

    private double hubrisData = 0;

    //Only for Capability.getDefaultImplementation, which should only be used if the capability is owner-agnostic (so if there's no need to store entity here)
    public CapabilityHubris(){
        this(null);
    }

    public CapabilityHubris(Entity entity /*, constructor parameters*/) {
        this.entity = entity;
    }

    @Override
    public double getHubris(){
        return this.hubrisData;
    }

    @Override
    public void changeHubris(double change){
        this.hubrisData=Math.max(0, this.hubrisData+change);
        this.entity.sendMessage(new TextComponentString(String.format("Your hubris level is now %f!", this.hubrisData*100/100)));
    }

    @Override
    public NBTTagCompound writeToNBT() {
        //For large data, implement caching here, using a markDirty system. Only write the data to NBT if it has changed, otherwise use the cached NBTTagCompound
        NBTTagCompound tags = new NBTTagCompound();
        tags.setDouble("hubris", this.hubrisData);
        return tags;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.hubrisData = nbt.getInteger("hubris");
    }
}
