package hubris;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import hubris.capability.CapabilityHubrisHandler;
import hubris.handlers.ModRegistry;
import hubris.proxy.CommonProxy;

@Mod(modid = Hubris.MODID, version = Hubris.VERSION, name = Hubris.NAME, dependencies = "required-after:fermiumbooter")
public class Hubris {
    public static final String MODID = "hubris";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "Hubris";
    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean completedLoading = false;
	
    @SidedProxy(clientSide = "hubris.proxy.ClientProxy", serverSide = "hubris.proxy.CommonProxy")
    public static CommonProxy PROXY;
	
	@Instance(MODID)
	public static Hubris instance;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRegistry.init();
        Hubris.PROXY.preInit();

        CapabilityHubrisHandler.registerCapability();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        completedLoading = true;
    }
}