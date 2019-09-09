package web.protocol;

import web.protocol.tcp.TcpPort;

public interface TransportPacket extends Packet {

    @Override
    TransportHeader getHeader();

    interface TransportHeader extends Header {

        TcpPort getSrcPort();

        TcpPort getDstPort();
    }
}
