package web.protocol.arp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.arp.ArpPacket.ArpHeader;
import web.protocol.ethernet.EthernetPacket;
import web.protocol.helper.PacketTestHelper;
import web.tool.packet.PacketNativeException;

import static web.protocol.ethernet.EthernetPacketTest.createEthernetHeader;
import static web.protocol.ethernet.Type.ARP;

class ArpPacketTest extends PacketTestHelper {

    @Test
    @DisplayName("ARP를 이용하여 공유기 주소를 알아온다.")
    void retrieve() throws PacketNativeException {
        ArpHeader header = createArpHeader();
        ArpPacket arpPacket = new ArpPacket(header);
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(ARP), arpPacket);
        handler.sendPacket(expected);
    }

    private ArpPacket.ArpHeader createArpHeader() {
        return ArpHeader.builder()
                //TODO: ARP Header를 구성한다.
                .build();
    }
}