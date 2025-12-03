package hubris.handlers;

import hubris.Hubris;
import hubris.gui.GuiHubrisHud;
import hubris.packets.PacketHubris;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler implements IMessageHandler<PacketHubris, IMessage> {
    public static final SimpleNetworkWrapper networkChannel = NetworkRegistry.INSTANCE.newSimpleChannel(Hubris.MODID);

    @Override
    public IMessage onMessage(PacketHubris msg, MessageContext ctx) {
        if (ctx.side == Side.CLIENT){
            GuiHubrisHud.hubris = msg.hubris;
        }
        return null;
    }
}
