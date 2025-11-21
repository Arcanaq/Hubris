package hubris.handlers;

import hubris.misfortunes.Misfortune;
import hubris.misfortunes.MisfortunePotion;
import hubris.misfortunes.MisfortuneExplosion;
import hubris.potion.PotionClipping;
import hubris.potion.PotionFlammable;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import hubris.Hubris;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = Hubris.MODID)
public class ModRegistry {

        /*public static ItemArmor.ArmorMaterial EXAMPLE_ARMOR = EnumHelper.addArmorMaterial("example_armor", Hubris.MODID + ":example_armor", 26, new int[]{2,4,6,2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

        public static Item exampleHelmet = new ItemExampleArmor("example_helmet", EXAMPLE_ARMOR, 2, EntityEquipmentSlot.HEAD);
        public static Item exampleChestplate = new ItemExampleArmor("example_chestplate", EXAMPLE_ARMOR, 1, EntityEquipmentSlot.CHEST);
        public static Item exampleLeggings = new ItemExampleArmor("example_leggings", EXAMPLE_ARMOR, 2, EntityEquipmentSlot.LEGS);
        public static Item exampleBoots = new ItemExampleArmor("example_boots", EXAMPLE_ARMOR, 1, EntityEquipmentSlot.FEET);
        */
        public static PotionType potionClipping = new PotionType("clipping", new PotionEffect(PotionClipping.INSTANCE, 300)).setRegistryName(new ResourceLocation(Hubris.MODID, "clipping"));
        public static PotionType potionFlammable = new PotionType("flammable", new PotionEffect(PotionFlammable.INSTANCE, 500)).setRegistryName(new ResourceLocation(Hubris.MODID, "flammable"));

        public static IForgeRegistry<Misfortune> misfortuneRegistry;

        public static void init() {

        }
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
}