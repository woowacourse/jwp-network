package web.tool;

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.SigarCommandBase;

public class NetInfo extends SigarCommandBase {

    public String getMacAddress() throws SigarException {
        return this.sigar.getNetInterfaceConfig(null).getHwaddr();
    }

    public String getIp() throws SigarException {
        return this.sigar.getNetInterfaceConfig(null).getAddress();
    }

    public String getNic() throws SigarException {
        return this.sigar.getNetInterfaceConfig(null).getName();
    }

    @Override
    public void output(String[] strings) throws SigarException {
        NetInterfaceConfig config = this.sigar.getNetInterfaceConfig(null);
        println("primary interface....." + config.getName());

        println("primary ip address...." + config.getAddress());

        println("primary mac address..." + config.getHwaddr());

        println("primary netmask......." + config.getNetmask());

        org.hyperic.sigar.NetInfo info = this.sigar.getNetInfo();

        println("host name............." + info.getHostName());

        println("domain name..........." + info.getDomainName());

        println("default gateway......." + info.getDefaultGateway());

        println("primary dns..........." + info.getPrimaryDns());

        println("secondary dns........." + info.getSecondaryDns());
    }

    public static void main(String[] args) throws Exception {
        new org.hyperic.sigar.cmd.NetInfo().processCommand(args);
    }
}
