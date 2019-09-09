package web.protocol.ip;

import lombok.Getter;

@Getter
public enum IpOptionType {
    END_OF_OPTION_LIST((byte) 0, "End of Option List"),
    NO_OPERATION((byte) 1, "No Operation"),
    SECURITY((byte) 130, "Security"),
    LOOSE_SOURCE_ROUTING((byte) 131, "Loose Source Routing"),
    INTERNET_TIMESTAMP((byte) 68, "Internet Timestamp"),
    EXTENDED_SECURITY((byte) 133, "Extended Security"),
    CIPSO((byte) 134, "CIPSO"),
    NO_OPERATIRECORD_ROUTEON((byte) 7, "Record Route"),
    STREAM_ID((byte) 136, "Stream ID"),
    STRICT_SOURCE_ROUTING((byte) 137, "Strict Source Routing"),
    ZSU((byte) 10, "ZSU"),
    MTUP((byte) 11, "MTUP"),
    MTUR((byte) 12, "MTUR"),
    FINN((byte) 205, "FINN"),
    VISA((byte) 142, "VISA"),
    ENCODE((byte) 15, "ENCODE"),
    IMITD((byte) 144, "IMITD"),
    EIP((byte) 145, "EIP"),
    TRACEROUTE((byte) 82, "Traceroute"),
    ADDRESS_EXTENSION((byte) 147, "Address Extension"),
    ROUTER_ALERT((byte) 148, "Router Alert"),
    SELECTIVE_DIRECTED_BROADCAST((byte) 149, "Selective Directed Broadcast"),
    DYNAMIC_PACKET_STATE((byte) 151, "Dynamic Packet State"),
    UPSTREAM_MULTICAST_PACKET((byte) 152, "Upstream Multicast Packet"),
    QUICK_START((byte) 25, "Quick-Start");

    private byte value;
    private String name;

    IpOptionType(byte value, String name) {
        this.value = value;
        this.name = name;
    }
}
