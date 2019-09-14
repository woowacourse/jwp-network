package web.protocol.helper;

import lombok.ToString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import web.protocol.Packet;
import web.protocol.ethernet.EthernetPacket;
import web.protocol.ethernet.EthernetPacketTest;
import web.tool.NetInfo;
import web.tool.packet.*;
import web.tool.packet.dump.TcpDump;

import java.util.ArrayList;
import java.util.List;

import static web.protocol.ethernet.EthernetPacketTest.buildEthernetPacket;

public class PacketTestHelper {
    private static final String PCAP_FILE_KEY = EthernetPacketTest.class.getName() + ".pcapFile";
    public static final String PCAP_FILE = System.getProperty(PCAP_FILE_KEY, "Dump.pcap");

    public static PacketHandler handler;
    public static String nicName;
    public static String macAddress;
    public static String localIp;
    public PacketStorage packetStorage = new PacketStorage();
    public PacketListener listener;

    @BeforeEach
    void setUp() throws Exception {
        NetInfo netInfo = new NetInfo();
        nicName = netInfo.getNic();
        macAddress = netInfo.getMacAddress();
        localIp = netInfo.getIp();
        listener = packet -> gotPacket(packet);
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

    private void gotPacket(byte[] raw) {
        Packet packet = buildEthernetPacket(raw);
        packetStorage.add(packet);
    }

    @ToString
    public static final class PacketStorage {
        private List<Packet> packets = new ArrayList<>();

        public void add(Packet packet) {
            packets.add(packet);
        }

        public boolean exist(Packet packet) {
            return packets.stream().anyMatch(v -> v.equals(packet));
        }
    }
}
