package entity;

public class Client extends Terminal{

    public Client(String ip, int port, String hostname) {
        super(ip, port, hostname);
    }

    @Override
    public void setType() {
        type = Terminal.TYPE_CLIENT;
    }
}
