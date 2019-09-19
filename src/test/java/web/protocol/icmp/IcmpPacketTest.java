package web.protocol.icmp;

import org.junit.jupiter.api.Test;
import web.protocol.SimplePacket;
import web.protocol.ethernet.EthernetPacket;
import web.protocol.ethernet.Type;
import web.protocol.helper.PacketTestHelper;
import web.protocol.ip.IpPacket;
import web.protocol.ip.IpPacket.IpHeader;
import web.tool.packet.PacketNativeException;
import web.protocol.icmp.IcmpPacket.IcmpHeader;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.ethernet.EthernetPacketTest.createEthernetHeader;

public class IcmpPacketTest extends PacketTestHelper {

    @Test
    void constructor() {
        IcmpHeader icmpHeader = createIcmpHeader();
        IcmpPacket packet = new IcmpPacket(icmpHeader, new SimplePacket());

        assertThat(packet).isNotNull();
    }

    @Test
    void ping() throws PacketNativeException {
        IcmpPacket icmpPacket = new IcmpPacket(createIcmpHeader(), new SimplePacket());
        IpPacket ipPacket = new IpPacket(createIpHeader(), icmpPacket);
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(Type.IPV4), ipPacket);

        handler.sendPacket(expected);
    }

    private IcmpHeader createIcmpHeader() {
        return IcmpPacket.IcmpHeader.builder()
                //TODO: ICMP Header를 구성한다.
                .build();
    }

    public static IpHeader createIpHeader() {
        return IpHeader.builder().build();
    }
}