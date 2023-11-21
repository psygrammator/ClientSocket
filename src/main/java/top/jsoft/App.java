package top.jsoft;

import java.io.IOException;

/**
 * Created by psygrammator
 * group jsoft.top
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        SocketClient socketClient = new SocketClient();
        socketClient.startClient();
    }
}
