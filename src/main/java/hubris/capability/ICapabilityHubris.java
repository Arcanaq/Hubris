package hubris.capability;

import net.minecraft.nbt.NBTTagCompound;

/**
 * This is the actual capability. Expose various ways to interact with the data stored in your implementation
 */
public interface ICapabilityHubris {
    double getHubris();
    void changeHubris(double change);

    NBTTagCompound writeToNBT();
    void readFromNBT(NBTTagCompound nbt);
}