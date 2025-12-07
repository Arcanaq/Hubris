package hubris.handlers;

import hubris.capability.CapabilityHubrisHandler;
import hubris.gui.GuiHubrisHud;
import hubris.misfortunes.Misfortune;
import hubris.misfortunes.MisfortunePotion;
import hubris.misfortunes.MisfortuneExplosion;
import hubris.packets.PacketHubris;
import hubris.potion.PotionClipping;
import hubris.potion.PotionFlammable;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import hubris.Hubris;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = Hubris.MODID)
public class ModRegistry {
        private static int packet_id = 0;

        /*public static ItemArmor.ArmorMaterial EXAMPLE_ARMOR = EnumHelper.addArmorMaterial("example_armor", Hubris.MODID + ":example_armor", 26, new int[]{2,4,6,2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

        public static Item exampleHelmet = new ItemExampleArmor("example_helmet", EXAMPLE_ARMOR, 2, EntityEquipmentSlot.HEAD);
        public static Item exampleChestplate = new ItemExampleArmor("example_chestplate", EXAMPLE_ARMOR, 1, EntityEquipmentSlot.CHEST);
        public static Item exampleLeggings = new ItemExampleArmor("example_leggings", EXAMPLE_ARMOR, 2, EntityEquipmentSlot.LEGS);
        public static Item exampleBoots = new ItemExampleArmor("example_boots", EXAMPLE_ARMOR, 1, EntityEquipmentSlot.FEET);
        */
        public static PotionType potionClipping = new PotionType("clipping", new PotionEffect(PotionClipping.INSTANCE, 300)).setRegistryName(new ResourceLocation(Hubris.MODID, "clipping"));
        public static PotionType potionFlammable = new PotionType("flammable", new PotionEffect(PotionFlammable.INSTANCE, 500)).setRegistryName(new ResourceLocation(Hubris.MODID, "flammable"));

        public static IForgeRegistry<Misfortune> misfortuneRegistry;

/*
        @SubscribeEvent
        public static void registerItemEvent(RegistryEvent.Register<Item> event) {
                event.getRegistry().registerAll(
                        exampleHelmet,
                        exampleChestplate,
                        exampleLeggings,
                        exampleBoots
                );
        }

        @SubscribeEvent
        public static void registerRecipeEvent(RegistryEvent.Register<IRecipe> event) {
                event.getRegistry().register(new RecipeExample().setRegistryName(new ResourceLocation(Hubris.MODID, "example")));
        }
*/
        @SubscribeEvent
        public static void registerPotionEvent(RegistryEvent.Register<Potion> event) {
                event.getRegistry().register(PotionClipping.INSTANCE);
                event.getRegistry().register(PotionFlammable.INSTANCE);
        }

        @SubscribeEvent
        public static void registerPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
                event.getRegistry().registerAll(
                        potionClipping,
                        potionFlammable
                );
                PotionHelper.addMix(PotionTypes.AWKWARD, Items.CHORUS_FRUIT, ModRegistry.potionClipping);
                PotionHelper.addMix(PotionTypes.FIRE_RESISTANCE, Items.FERMENTED_SPIDER_EYE, ModRegistry.potionClipping);
        }

        @SubscribeEvent
        public static void registerMisfortuneEvent(RegistryEvent.Register<Misfortune> event){
                event.getRegistry().registerAll(
                        MisfortuneExplosion.INSTANCE,
                        MisfortunePotion.INSTANCE
                );
        }

        @SubscribeEvent
        public static void onNewRegistry(RegistryEvent.NewRegistry event){
                misfortuneRegistry = new RegistryBuilder<Misfortune>()
                        .setName(new ResourceLocation(Hubris.MODID, "misfortune_registry"))
                        .setType(Misfortune.class)
                        .create();
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void onRenderOverlay(RenderGameOverlayEvent.Post event){
                GuiHubrisHud.Render();
        }

        public static void registerPackets(){
                PacketHandler.networkChannel.registerMessage(PacketHandler.class, PacketHubris.class, packet_id++, Side.CLIENT);
        }

        public static void preInit() {
                registerPackets();
                CapabilityHubrisHandler.registerCapability();
        }
}