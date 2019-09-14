package web.protocol.ethernet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.ByteUtils;
import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.ethernet.EthernetPacket.EthernetHeader;
import web.protocol.helper.PacketTestHelper;
import web.tool.packet.PacketNativeException;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.ethernet.EthernetPacket.EthernetHeader.*;
import static web.protocol.ethernet.Type.ARP;

public class EthernetPacketTest extends PacketTestHelper {

    @Test
    @DisplayName("Ethernet Packet을 전송한다.")
    void send() throws Exception {
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(ARP), new SimplePacket());
        handler.sendPacket(expected);
        handler.loop(5, listener);

        assertThat(packetStorage.exist(expected)).isTrue();
    }

    @Test
    @DisplayName("Ethernet Packet을 pcap 파일에 저장한다.")
    void save() throws PacketNativeException {
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(ARP), new SimplePacket());
        PacketTestHelper.save(PCAP_FILE, handler, expected);
        Packet actual = buildEthernetPacket(read(PCAP_FILE));

        assertThat(actual.getHeader()).isEqualTo(expected.getHeader());
    }

    public static EthernetHeader createEthernetHeader(Type protocolType) {
        MacAddress src = MacAddress.getByName(macAddress);
        MacAddress dst = MacAddress.ETHER_BROADCAST_ADDRESS;

        return new EthernetHeader(dst, src, protocolType);
    }

    public static Packet buildEthernetPacket(byte[] rawData) {
        MacAddress dstAddr = ByteUtils.getMacAddress(rawData, DST_ADDR_OFFSET);
        MacAddress srcAddr = ByteUtils.getMacAddress(rawData, SRC_ADDR_OFFSET);
        Type type = Type.getInstance(ByteUtils.getShort(rawData, TYPE_OFFSET));

        EthernetHeader header = new EthernetHeader(dstAddr, srcAddr, type);
        return new EthernetPacket(header, new SimplePacket());
    }
}