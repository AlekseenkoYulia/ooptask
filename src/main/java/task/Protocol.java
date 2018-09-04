package task;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public enum Protocol {
    HTTP(SocketFactory.getDefault(), (short) 80), HTTPS(SSLSocketFactory.getDefault(), (short) 443);

    private final SocketFactory socketFactory;
    private final short defaultPort;

    Protocol(SocketFactory socketFactory, short defaultPort) {
        this.socketFactory = socketFactory;
        this.defaultPort = defaultPort;
    }

    public SocketFactory getSocketFactory() {
        return socketFactory;
    }

    public short getDefaultPort() {
        return defaultPort;
    }
}
