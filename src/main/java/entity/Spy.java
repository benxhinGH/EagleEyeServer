package entity;

public class Spy extends Terminal{


    public Spy(String ip, int port, String hostname) {
        super(ip, port, hostname);
    }

    @Override
    public void setType() {
        type = Terminal.TYPE_SPY;
    }
}
