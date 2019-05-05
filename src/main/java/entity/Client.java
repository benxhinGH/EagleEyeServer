package entity;

public class Client extends Terminal{

    public Client(String ip, int port, String hostname) {
        super(ip, port, hostname);
    }

    @Override
    public int getType() {
        return Terminal.TYPE_CLIENT;
    }
}
