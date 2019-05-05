package entity;

public abstract class Terminal {

    public static final int TYPE_SPY = 0;
    public static final int TYPE_CLIENT = 1;

    private String ip;
    private int port;
    private String hostname;

    public Terminal(){

    }

    public Terminal(String ip, int port, String hostname) {
        this.ip = ip;
        this.port = port;
        this.hostname = hostname;
    }

    public abstract int getType();

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Terminal)){
            return false;
        }
        Terminal terminal = (Terminal) obj;
        if(terminal.getHostname().equals(hostname) &&
                terminal.getIp().equals(ip) &&
                terminal.getPort() == port){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + ip.hashCode();
        result = result * 31 + port;
        result = result * 31 + hostname.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", hostname='" + hostname + '\'' +
                '}';
    }
}
