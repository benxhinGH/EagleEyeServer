package entity;

public class Spy extends Terminal{


    public Spy(String ip, int port, String hostname) {
        super(ip, port, hostname);
    }

    @Override
    public int getType() {
        return Terminal.TYPE_SPY;
    }
}
