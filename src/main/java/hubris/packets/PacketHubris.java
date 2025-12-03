package hubris.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketHubris implements IMessage {
    public double hubris;

    public PacketHubris(){}

    public PacketHubris(double hubris){
        this.hubris = hubris;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        hubris = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(hubris);
    }
}
