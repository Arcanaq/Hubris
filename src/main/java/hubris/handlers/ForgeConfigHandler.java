package hubris.handlers;

import fermiumbooter.annotations.MixinConfig;
import hubris.enums.EnumHudStyle;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import hubris.Hubris;

@Config(modid = Hubris.MODID)
public class ForgeConfigHandler {
	
	@Config.Comment("Server-Side Options")
	@Config.Name("Server Options")
	public static final ServerConfig server = new ServerConfig();

	@Config.Comment("Client-Side Options")
	@Config.Name("Client Options")
	public static final ClientConfig client = new ClientConfig();

	@MixinConfig(name = Hubris.MODID) //Needed on config classes that contain MixinToggles for those mixins to be added
	public static class ServerConfig {
		@Config.Comment("Minimum amount of HUBRIS needed to cause misfortune")
		@Config.Name("Misfortune Threshold")
		@Config.RangeDouble(min = 0)
		public static double MisfortuneThreshold = 20;

		@Config.Comment("Chance out of 1 for a misfortune to happen each tick")
		@Config.Name("Misfortune Chance")
		@Config.RangeDouble(min = 0, max = 1)
		public static double MisfortuneChance = 0.025;

		@Config.Comment("If non-zero, the chance of a misfortune will multiply by the amount of HUBRIS and this value")
		@Config.Name("Dynamic Hubris")
		@Config.RangeDouble(min = 0)
		public static double DynHubris = 1;

		@Config.Comment("Amount of HUBRIS added when completing an advancement or unlocking recipes")
		@Config.Name("Advancement Increasing Value")
		public static double AdvancementIncrease = 1f;

		@Config.Comment("Amount of HUBRIS added when dealing damage")
		@Config.Name("Damage Increasing Value")
		public static double DamageIncrease = .75f;

		@Config.Comment("Amount of HUBRIS removed when a misfortune is inflicted")
		@Config.Name("Misfortune Diminishing Value")
		@Config.RangeDouble(min = 0)
		public static double MisfortuneDiminish = 5f;

		@Config.Comment("Amount of HUBRIS removed when taking damage")
		@Config.Name("Damage Diminishing Value")
		public static double DamageDiminish = .5f;

		@Config.Comment("Amount of HUBRIS removed when dying")
		@Config.Name("Death Diminishing Value")
		public static double DeathDiminish = 5f;

		@Config.Comment("Amount of HUBRIS removed each tick")
		@Config.Name("Death Diminishing Value")
		public static double TickDiminish = .05f;

		@Config.Comment("Example Early Mixin Toggle Config")
		@Config.Name("Enable Vanilla Player Mixin (Vanilla)")
		@MixinConfig.MixinToggle(earlyMixin = "mixins.hubris.vanilla.json", defaultValue = false)
		public boolean enableVanillaMixin = false;

		@Config.Comment("Example Late Mixin Toggle Config")
		@Config.Name("Enable JEI Init Mixin (JEI)")
		@MixinConfig.MixinToggle(lateMixin = "mixins.hubris.jei.json", defaultValue = false)
		@MixinConfig.CompatHandling(
				modid = "jei",
				desired = true,
				reason = "Mod needed for this Mixin to properly work",
				warnIngame = false //use this if the mixin is for an optional mod dependency that can be skipped with no issue if the mod is not present
		)
		public boolean enableJeiMixin = false;
	}

	public static class ClientConfig {
		@Config.Name("Hud Style")
		public static EnumHudStyle HudStyle = EnumHudStyle.LABEL;

		@Config.Name("Hud X Position")
		public static int HudX = 6;

		@Config.Name("Hud Y Position")
		public static int HudY = 6;

		@Config.Comment("If enabled, hides the HUD when HUBRIS is 0")
		@Config.Name("Dynamic HUD")
		public static boolean DynHUD = true;
	}

	@Mod.EventBusSubscriber(modid = Hubris.MODID)
	private static class EventHandler{

		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(Hubris.MODID)) {
				ConfigManager.sync(Hubris.MODID, Config.Type.INSTANCE);
			}
		}
	}
}