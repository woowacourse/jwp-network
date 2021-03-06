package web.protocol.ethernet;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import util.ByteUtils;
import web.protocol.Packet;

import java.util.ArrayList;
import java.util.List;

import static util.ByteUtils.SHORT_SIZE_IN_BYTES;
import static util.PacketUtils.copyHeader;
import static util.PacketUtils.copyPayload;
import static web.protocol.ethernet.MacAddress.SIZE_IN_BYTES;

@EqualsAndHashCode(of = {"header", "payload"})
public class EthernetPacket implements Packet {

    private final EthernetHeader header;
    private final Packet payload;

    public EthernetPacket(EthernetHeader header, Packet payload) {
        this.header = header;
        this.payload = payload;
    }

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
    @EqualsAndHashCode(of = {"dstAddr", "srcAddr", "protocolType"})
    public static final class EthernetHeader implements Header {

        static final int DST_ADDR_OFFSET = 0;
        private static final int DST_ADDR_SIZE = SIZE_IN_BYTES;
        static final int SRC_ADDR_OFFSET = DST_ADDR_OFFSET + DST_ADDR_SIZE;
        private static final int SRC_ADDR_SIZE = SIZE_IN_BYTES;
        static final int TYPE_OFFSET = SRC_ADDR_OFFSET + SRC_ADDR_SIZE;
        private static final int TYPE_SIZE = SHORT_SIZE_IN_BYTES;

        private static final int ETHERNET_HEADER_SIZE = TYPE_OFFSET + TYPE_SIZE; // 14 Bytes

        private final MacAddress dstAddr;
        private final MacAddress srcAddr;
        private final Type protocolType;

        @Override
        public int length() {
            return ETHERNET_HEADER_SIZE;
        }

        @Override
        public List<byte[]> getRawFields() {
            List<byte[]> rawFields = new ArrayList<>();
            rawFields.add(ByteUtils.toByteArray(dstAddr));
            rawFields.add(ByteUtils.toByteArray(srcAddr));
            rawFields.add(ByteUtils.toByteArray(protocolType.getValue()));
            return rawFields;
        }
    }
}
