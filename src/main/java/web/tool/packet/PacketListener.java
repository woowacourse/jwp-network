package web.tool.packet;

@FunctionalInterface
public interface PacketListener {
    void gotPacket(byte[] packet);
}
