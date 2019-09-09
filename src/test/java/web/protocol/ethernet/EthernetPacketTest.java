package web.protocol.ethernet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.ByteUtils;
import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.helper.PacketTestHelper;
import web.tool.packet.PacketHandler;
import web.tool.packet.PacketNativeException;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.ethernet.EthernetPacket.EthernetHeader.*;
import static web.protocol.ethernet.Type.ARP;

public class EthernetPacketTest extends PacketTestHelper {
    private static final String PCAP_FILE_KEY = EthernetPacketTest.class.getName() + ".pcapFile";
    private static final String PCAP_FILE = System.getProperty(PCAP_FILE_KEY, "Dump.pcap");

    PacketHandler handler;
    String nicName;

    @BeforeEach
    void setUp() throws Exception {
        handler = getHandler(nicName);
    }

    @Test
    @DisplayName("Ethernet Packet을 전송한다.")
    void send() throws Exception {
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(ARP), new SimplePacket());
        Packet actual = handler.sendPacket(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Ethernet Packet을 pcap 파일에 저장한다.")
    void save() throws PacketNativeException {
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(ARP), new SimplePacket());
        PacketTestHelper.save(PCAP_FILE, handler, expected);
        Packet actual = createEthernetPacket(read(PCAP_FILE));

        assertThat(actual.getHeader()).isEqualTo(expected.getHeader());
    }

    @AfterEach
    void tearDown() {
        handler.close();
    }

    public static EthernetPacket.EthernetHeader createEthernetHeader(Type protocolType) {
        MacAddress src = MacAddress.getByName("00:00:00:00:00:01");
        MacAddress dst = MacAddress.ETHER_BROADCAST_ADDRESS;

        return new EthernetPacket.EthernetHeader(dst, src, protocolType);
    }

    public static Packet createEthernetPacket(byte[] rawData) {
        MacAddress dstAddr = ByteUtils.getMacAddress(rawData, DST_ADDR_OFFSET);
        MacAddress srcAddr = ByteUtils.getMacAddress(rawData, SRC_ADDR_OFFSET);
        Type type = Type.getInstance(ByteUtils.getShort(rawData, TYPE_OFFSET));

        EthernetPacket.EthernetHeader header = new EthernetPacket.EthernetHeader(dstAddr, srcAddr, type);
        return new EthernetPacket(header, new SimplePacket());
    }
}