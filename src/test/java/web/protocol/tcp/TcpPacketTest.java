package web.protocol.tcp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.helper.PacketTestHelper;
import web.tool.packet.PacketHandler;

class TcpPacketTest extends PacketTestHelper {

    PacketHandler handler;
    String nicName;

    @BeforeEach
    void setUp() throws Exception {
        handler = getHandler(nicName);
    }

    @Test
    @DisplayName("TCP Packet을 생성한다.")
    void constructor() {

    }

    @Test
    @DisplayName("TCP 3 way handshake를 연결한다.")
    void handshake() {

    }

    @AfterEach
    void tearDown() {
        handler.close();
    }
}