package web.protocol.tcp.option;

import web.protocol.tcp.TcpPacket.TcpOption;

public class TcpNoOperationOption implements TcpOption {

    private static final TcpNoOperationOption INSTANCE = new TcpNoOperationOption();

    private static final TcpOptionKind kind = TcpOptionKind.NO_OPERATION;

    @Override
    public TcpOptionKind getKind() {
        return kind;
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public byte[] getRawData() {
        return new byte[]{(byte) 1};
    }

    public static TcpNoOperationOption getInstance() {
        return INSTANCE;
    }
}
