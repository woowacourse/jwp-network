package web.protocol.tcp.option;

import lombok.Getter;

@Getter
public enum TcpOptionKind {

    NO_OPERATION((byte) 1, "No Operation"),
    MAXIMUM_SEGMENT_SIZE((byte) 2, "Maximum Segment Size"),
    WINDOW_SCALE((byte) 3, "Window Scale"),
    SACK_PERMITTED((byte) 4, "SACK Permitted"),
    TIMESTAMPS((byte) 8, "Timestamps");

    private byte value;
    private String name;

    TcpOptionKind(byte value, String name) {
        this.value = value;
        this.name = name;
    }
}