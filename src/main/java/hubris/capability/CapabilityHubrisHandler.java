package hubris.capability;

import hubris.handlers.ForgeConfigHandler;
import hubris.handlers.MisfortuneHandler;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import hubris.Hubris;
import org.lwjgl.Sys;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

/**
 * This class handles interaction of the capability with everything else
 */
public class CapabilityHubrisHandler {
    public static final ResourceLocation CAP_HUBRIS_KEY = new ResourceLocation(Hubris.MODID, "hubris");

    @CapabilityInject(ICapabilityHubris.class)
    public static Capability<ICapabilityHubris> CAP_HUBRIS;

    public static void registerCapability() {
        CapabilityManager.INSTANCE.register(ICapabilityHubris.class, new Storage(), CapabilityHubris::new); //Note: the factory here in the last parameter is only used in Capability.getDefaultImplementation which has no default usage. Otherwise the Provider is used
        MinecraftForge.EVENT_BUS.register(EventHandler.class);
    }

    /**
     * Attaching the capability and various ways of interaction
     */
    public static class EventHandler {
        static Random rand = new Random();

        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if(!Hubris.completedLoading) return; //only needed when other mods instantiate objects during startup that would get the capability (example: JEI creating ItemStacks)
            Entity entity = event.getObject();
            if (entity.world.isRemote) return;
            if(!(event.getObject() instanceof EntityPlayer)) return;
            if(entity.hasCapability(CAP_HUBRIS, null)) return;

            event.addCapability(CAP_HUBRIS_KEY, new Provider(entity /*, various constructor parameters*/));
        }

        @SubscribeEvent
        public static void onPlayerClone(PlayerEvent.Clone event){
            if (event.isWasDeath()){
                ICapabilityHubris cap = event.getOriginal().getCapability(CAP_HUBRIS, null);
                if (cap == null) return;
                event.getEntityPlayer().getCapability(CAP_HUBRIS, null).readFromNBT(cap.writeToNBT());
            }
        }

        @SubscribeEvent
        public static void onAdvancement(AdvancementEvent event){
            EntityPlayer plr = event.getEntityPlayer();
            if (plr == null) return;
            if (!plr.hasCapability(CAP_HUBRIS, null)) return;
            plr.getCapability(CAP_HUBRIS, null).changeHubris(.3);
        }

        @SubscribeEvent
        public static void onAttack(AttackEntityEvent event){
            EntityPlayer plr = event.getEntityPlayer();
            if (plr == null) return;
            if (!plr.hasCapability(CAP_HUBRIS, null)) return;
            plr.getCapability(CAP_HUBRIS, null).changeHubris(.1);
        }

        @SubscribeEvent
        public static void onHurt(LivingHurtEvent event){
            Entity entity = event.getEntity();
            if (entity == null) return;
            if (!(entity instanceof EntityPlayer)) return;
            if (!entity.hasCapability(CAP_HUBRIS, null)) return;
            entity.getCapability(CAP_HUBRIS, null).changeHubris(-.1);
        }

        @SubscribeEvent
        public static void onDeath(LivingDeathEvent event){
            Entity entity = event.getEntity();
            if (entity == null) return;
            if (!(entity instanceof EntityPlayer)) return;
            if (!entity.hasCapability(CAP_HUBRIS, null)) return;
            entity.getCapability(CAP_HUBRIS, null).changeHubris(-1);
        }

        @SubscribeEvent
        public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
            Entity entity = event.getEntity();
            if (entity == null) return;
            if (!(entity instanceof EntityPlayer)) return;
            if (!entity.hasCapability(CAP_HUBRIS, null)) return;
            ICapabilityHubris cap = event.getEntity().getCapability(CAP_HUBRIS, null);
            if(cap == null) return;
            double hubris = cap.getHubris();
            if (hubris >= ForgeConfigHandler.ServerConfig.MisfortuneThreshold){
                double val = rand.nextDouble();
                if (ForgeConfigHandler.ServerConfig.DynHubris !=0){
                    val*=hubris* ForgeConfigHandler.ServerConfig.DynHubris;
                }
                if (val <= ForgeConfigHandler.ServerConfig.MisfortuneChance) {
                    MisfortuneHandler.ApplyMisfortune((EntityPlayer) event.getEntity());
                    cap.changeHubris(-ForgeConfigHandler.ServerConfig.MisfortuneDiminish);
                }
            }
        }
    }

    /**
     * creating instances of the capability
     */
    public static class Provider implements ICapabilitySerializable<NBTTagCompound> {
        private final ICapabilityHubris instance;

        public Provider(Entity entity /*, add various constructor parameters here*/) {
            this.instance = new CapabilityHubris(entity /*, constructor parameters*/);
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CAP_HUBRIS;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == CAP_HUBRIS ? CAP_HUBRIS.cast(instance) : null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return (NBTTagCompound) CAP_HUBRIS.writeNBT(instance, null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            CAP_HUBRIS.readNBT(instance, null, nbt);
        }
    }

    /**
     * NBT handling for the capability (read/write)
     */
    public static class Storage implements Capability.IStorage<ICapabilityHubris> {
        //Doesn't need to redirect to capability-internal handling if all the relevant data is accessible from outside

        @Override
        public NBTBase writeNBT(Capability<ICapabilityHubris> capability, ICapabilityHubris instance, EnumFacing side) {
            return instance.writeToNBT();
            //Note: ItemStacks don't sync capability NBT between server + client, need to do custom handling for that (either via the get/readNBTShareTag or custom packets)
        }

        @Override
        public void readNBT(Capability<ICapabilityHubris> capability, ICapabilityHubris instance, EnumFacing side, NBTBase nbt) {
            instance.readFromNBT((NBTTagCompound) nbt);
        }
    }
}
