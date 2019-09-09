package web.protocol.helper;

import web.protocol.ethernet.EthernetPacket;
import web.tool.packet.NetworkInterface;
import web.tool.packet.NetworkInterfaceService;
import web.tool.packet.PacketHandler;
import web.tool.packet.PacketNativeException;
import web.tool.packet.dump.TcpDump;

public class PacketTestHelper {

    public static PacketHandler getHandler(String nicName) throws Exception {
        NetworkInterface nif = NetworkInterfaceService.findByName(nicName);
        return nif.openLive(65536, NetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
    }

    public static byte[] read(String filePath) throws PacketNativeException {
        PacketHandler handler = TcpDump.openOffline(filePath);
        byte[] rawData = handler.getNextRawPacket();
        handler.close();
        return rawData;
    }

    public static void save(String filePath, PacketHandler handler, EthernetPacket packet) throws PacketNativeException {
        TcpDump dumper = handler.dumpOpen(filePath);
        dumper.dump(packet);
        dumper.close();
    }
}
