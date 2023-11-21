package top.jsoft;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static org.mockito.Mockito.*;

/**
 * Created by psygrammator
 * group jsoft.top
 */
public class SocketClientTest {
    @Test
    public void testSendClientMsg() throws IOException {
        OutputStream outputStream = mock(OutputStream.class);
        PrintWriter printWriter = mock(PrintWriter.class);

        SocketClient client = new SocketClient();

        String scannerMessage = "file file.txt";
        Scanner scanner = new Scanner(scannerMessage);
        client.sendClientMsg(scanner, printWriter, outputStream);

        verify(printWriter, atLeastOnce()).println(scannerMessage);

        verify(outputStream, atLeastOnce()).write(any(byte[].class), anyInt(), anyInt());
        verify(outputStream, atLeastOnce()).flush();
    }

    @Test
    public void testSendNotifyAllClients()
    {
        BufferedReader bufferedReader = mock(BufferedReader.class);

        SocketClient socketClient = new SocketClient();
        socketClient.sendNotifyAllClients(bufferedReader);
    }

    @Test
    public void testSendFileNameIsEmpty() throws IOException {
        OutputStream outputStream = mock(OutputStream.class);

        SocketClient client = new SocketClient();

        String errorSendFileMessage = client.sendFile("file ", outputStream);

        Assert.assertEquals("File name is empty!", errorSendFileMessage);

    }

    @Test
    public void testSendFile() throws IOException {
        OutputStream outputStream = mock(OutputStream.class);
        String msg = "file file.txt";

        SocketClient socketClient = new SocketClient();

        String errorSendFileMessage = socketClient.sendFile(msg, outputStream);

        Assert.assertNull(errorSendFileMessage);

        verify(outputStream, atLeastOnce()).write(any(byte[].class), anyInt(), anyInt());
        verify(outputStream, atLeastOnce()).flush();
    }
}
