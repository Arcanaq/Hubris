package hubris.handlers;

import hubris.capability.CapabilityHubris;
import hubris.capability.CapabilityHubrisHandler;
import hubris.potion.PotionClipping;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import hubris.Hubris;
import hubris.item.ItemExampleArmor;
import hubris.recipe.RecipeExample;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = Hubris.MODID)
public class ModRegistry {

        /*public static ItemArmor.ArmorMaterial EXAMPLE_ARMOR = EnumHelper.addArmorMaterial("example_armor", Hubris.MODID + ":example_armor", 26, new int[]{2,4,6,2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

        public static Item exampleHelmet = new ItemExampleArmor("example_helmet", EXAMPLE_ARMOR, 2, EntityEquipmentSlot.HEAD);
        public static Item exampleChestplate = new ItemExampleArmor("example_chestplate", EXAMPLE_ARMOR, 1, EntityEquipmentSlot.CHEST);
        public static Item exampleLeggings = new ItemExampleArmor("example_leggings", EXAMPLE_ARMOR, 2, EntityEquipmentSlot.LEGS);
        public static Item exampleBoots = new ItemExampleArmor("example_boots", EXAMPLE_ARMOR, 1, EntityEquipmentSlot.FEET);
        */
        public static PotionType potionClipping = new PotionType("clipping", new PotionEffect(PotionClipping.INSTANCE, 200)).setRegistryName(new ResourceLocation(Hubris.MODID, "clipping"));

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
        }

        @SubscribeEvent
        public static void registerPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
                event.getRegistry().register(potionClipping);
                PotionHelper.addMix(PotionTypes.AWKWARD, Items.CHORUS_FRUIT, ModRegistry.potionClipping);
        }

        @SubscribeEvent
        public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event){
                if (event.getEntityLiving().getActivePotionEffect(PotionClipping.INSTANCE)!=null){
                        EntityLivingBase entity = event.getEntityLiving();
                        PotionEffect effect = entity.getActivePotionEffect(PotionClipping.INSTANCE);

                        if (effect != null && effect.getDuration() <= 100 && !entity.collidedVertically){
                                entity.fallDistance = 0;
                                entity.removePotionEffect(PotionClipping.INSTANCE);
                        }
                        else
                                entity.noClip = true;
                }
        }
}