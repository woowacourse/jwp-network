package web.tool.packet;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.protocol.ethernet.MacAddress;
import web.tool.NetInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static web.tool.packet.PacketHandler.TimestampPrecision.MICRO;

@Getter
@ToString
public class NetworkInterface {
    private static final Logger log = LoggerFactory.getLogger(NetworkInterface.class);

    private static final int LOOPBACK_CONDITION = 0x00000001;
    private static final int UP_CONDITION = 0x00000002;
    private static final int RUNNING_CONDITION = 0x00000004;

    private final List<MacAddress> macAddresses = new ArrayList<>();
    private final String name;
    private final String description;
    private final boolean loopBack;
    private final boolean up;
    private final boolean running;
    private final boolean local;

    private NetworkInterface(NativeMappings.pcap_if pif, boolean local) {
        this.name = pif.getName();
        this.description = pif.getDescription();
        this.loopBack = (pif.getFlags() & LOOPBACK_CONDITION) != 0;
        this.up = (pif.getFlags() & UP_CONDITION) != 0;
        this.running = (pif.getFlags() & RUNNING_CONDITION) != 0;
        this.local = local;
        addMacAddress();
    }

    public static NetworkInterface of(NativeMappings.pcap_if pif, boolean local) {
        return new NetworkInterface(pif, local);
    }

    public PacketHandler openLive(int snaplen, PromiscuousMode mode, int timeoutMillis) throws Exception {
        Errbuf errbuf = new Errbuf();
        Pointer handle = NativeMappings.pcap_open_live(name, snaplen, mode.getValue(), timeoutMillis, errbuf);

        if (isNullOrBlank(errbuf, handle)) {
            throw new PacketNativeException("Handler or ErrBuf is empty.");
        }

        return PacketHandler.builder()
                .handle(handle)
                .timestampPrecision(MICRO)
                .build();
    }

    private void addMacAddress() {
        NetInfo netInfo = new NetInfo();
        try {
            macAddresses.add(MacAddress.getByName(netInfo.getMacAddress()));
        } catch (SigarException e) {
            log.error(e.getMessage());
        }
    }

    private boolean isNullOrBlank(Errbuf errbuf, Pointer handle) {
        return handle == null || errbuf.length() != 0;
    }

    @Getter
    @NoArgsConstructor
    public static class Errbuf extends Structure {

        public byte[] buf = new byte[DEFAULT_ERR_BUF_SIZE];

        private static final int DEFAULT_ERR_BUF_SIZE = 256;

        public int length() {
            return toString().length();
        }

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("buf");
        }

        @Override
        public String toString() {
            return Native.toString(buf);
        }
    }

    @Getter
    public enum PromiscuousMode {
        PROMISCUOUS(1),
        NON_PROMISCUOUS(0);

        private final int value;

        PromiscuousMode(int value) {
            this.value = value;
        }
    }
}
