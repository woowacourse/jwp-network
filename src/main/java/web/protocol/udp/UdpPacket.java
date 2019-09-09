package web.protocol.udp;

import web.protocol.Packet;
import web.protocol.TransportPacket.TransportHeader;
import web.protocol.tcp.TcpPort;

import java.util.List;

public class UdpPacket implements Packet {
    @Override
    public Header getHeader() {
        return null;
    }

    @Override
    public Packet getPayload() {
        return null;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public byte[] getRawData() {
        return new byte[0];
    }

    public static final class UdpHeader implements TransportHeader {

        @Override
        public TcpPort getSrcPort() {
            return null;
        }

        @Override
        public TcpPort getDstPort() {
            return null;
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public List<byte[]> getRawFields() {
            return null;
        }
    }
}
