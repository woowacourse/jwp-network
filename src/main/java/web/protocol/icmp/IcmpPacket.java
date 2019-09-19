package web.protocol.icmp;

import lombok.Builder;
import lombok.Getter;
import web.protocol.Packet;

import java.util.ArrayList;
import java.util.List;

import static util.PacketUtils.copyHeader;
import static util.PacketUtils.copyPayload;

@Builder
public class IcmpPacket implements Packet {

    private IcmpHeader header;
    private Packet payload;

    @Override
    public Header getHeader() {
        return header;
    }

    @Override
    public Packet getPayload() {
        return payload;
    }

    @Override
    public int length() {
        return getHeader().length() + getPayload().length();
    }

    @Override
    public byte[] getRawData() {
        byte[] result = new byte[length()];

        int nextPosition = copyHeader(this, result, 0);
        copyPayload(this, result, nextPosition);
        return result;
    }

    @Builder
    public final static class IcmpHeader implements Header {

        public static final int ICMP_HEADER_SIZE = 8;
        private IcmpType type;
        private IcmpCode code;
        private short checksum;
        private short identification;
        private short sequenceNumber;

        @Override
        public int length() {
            return ICMP_HEADER_SIZE;
        }

        @Override
        public List<byte[]> getRawFields() {
            List<byte[]> rawFields = new ArrayList<>();
            //TODO: ICMP Header의 필드를 구성한다.
            return rawFields;
        }
    }

    @Getter
    public enum IcmpType {
        ECHO_REPLY((byte) 0, "Echo Reply"),
        DESTINATION_UNREACHABLE((byte) 3, "Destination Unreachable"),
        ECHO_REQUEST((byte) 8, "Echo Request");

        private byte value;
        private String name;

        IcmpType(byte value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    @Getter
    public enum IcmpCode {
        // type 3
        NETWORK_UNREACHABLE((byte) 0, "Network Unreachable");

        private byte value;
        private String name;

        IcmpCode(byte value, String name) {
            this.value = value;
            this.name = name;
        }
    }
}
