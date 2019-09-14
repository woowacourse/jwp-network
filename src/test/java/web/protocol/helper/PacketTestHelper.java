package web.protocol.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import web.protocol.ethernet.EthernetPacket;
import web.protocol.ethernet.EthernetPacketTest;
import web.tool.NetInfo;
import web.tool.packet.NetworkInterface;
import web.tool.packet.NetworkInterfaceService;
import web.tool.packet.PacketHandler;
import web.tool.packet.PacketNativeException;
import web.tool.packet.dump.TcpDump;

public class PacketTestHelper {
    private static final String PCAP_FILE_KEY = EthernetPacketTest.class.getName() + ".pcapFile";
    public static final String PCAP_FILE = System.getProperty(PCAP_FILE_KEY, "Dump.pcap");

    public static PacketHandler handler;
    public static String nicName;
    public static String macAddress;
    public static String localIp;

    @BeforeEach
    void setUp() throws Exception {
        NetInfo netInfo = new NetInfo();
        nicName = netInfo.getNic();
        macAddress = netInfo.getMacAddress();
        localIp = netInfo.getIp();
        handler = getHandler(nicName);
    }

    @AfterEach
    void tearDown() {
        handler.close();
    }

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
