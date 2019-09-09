package web.protocol.ip;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.ethernet.EthernetPacket;
import web.protocol.helper.PacketTestHelper;
import web.protocol.ip.IpPacket.IpHeader;
import web.tool.packet.PacketHandler;
import web.tool.packet.PacketNativeException;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.ethernet.EthernetPacketTest.createEthernetHeader;
import static web.protocol.ethernet.Type.IPV4;

class IpPacketTest extends PacketTestHelper {

    PacketHandler handler;
    String nicName;

    @BeforeEach
    void setUp() throws Exception {
        handler = getHandler(nicName);
    }

    @Test
    @DisplayName("IPv4 Packet을 생성한다.")
    void constructor() {
        IpPacket.IpHeader header = createIpHeader();
        IpPacket packet = new IpPacket(header, new SimplePacket());

        assertThat(packet).isNotNull();
    }

    @Test
    @DisplayName("IPv4 Packet을 전송한다.")
    void save() throws PacketNativeException {
        IpPacket ipPacket = new IpPacket(createIpHeader(), new SimplePacket());
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(IPV4), ipPacket);
        Packet actual = handler.sendPacket(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @AfterEach
    void tearDown() {
        handler.close();
    }

    public static IpHeader createIpHeader() {
        return IpHeader.builder().build();
    }
}