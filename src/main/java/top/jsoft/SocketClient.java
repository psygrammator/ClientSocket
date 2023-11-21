package top.jsoft;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Created by psygrammator
 * group jsoft.top
 */
public class SocketClient {
    private static final String HOST = "localhost";
    private static final int PORT = 7777;

    public void startClient() throws IOException {
        try(Socket socketClient = new Socket(HOST, PORT))
        {
            try (PrintWriter printWriter = new PrintWriter(socketClient.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream())))
            {
                new Thread(() -> sendNotifyAllClients(reader)).start();

                Scanner scanner = new Scanner(System.in);
                sendClientMsg(scanner, printWriter, socketClient.getOutputStream());
            }
        }
    }

    public void sendClientMsg(Scanner scanner, PrintWriter printWriter, OutputStream outputStream) throws IOException {
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            printWriter.println(msg);
            if ("exit".equals(msg)) {
                break;
            } else if (msg.startsWith("file ")) {
                String sendFileError = sendFile(msg, outputStream);
                if (sendFileError != null) {
                    System.out.println("Error: " + sendFileError);
                }
            }
        }
    }
    public void sendNotifyAllClients(BufferedReader reader)
    {
        try {
            String msg;
            while ((msg = reader.readLine()) != null) {
                System.out.println(msg);
            }
        } catch (IOException e) {
            System.out.println("" + e);
        }
    }
    public String sendFile(String msg, OutputStream outputStream) throws IOException {
        String fileName = (msg.substring(msg.lastIndexOf(' ') + 1)).trim();
        if (fileName.isEmpty()) {
            return "File name is empty!";
        }
        File file = new File(fileName);
        if (!file.exists()) {
            return "File not exists!";
        }

        int length;
        byte[] bufferBytes = new byte[8192];
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(file.toPath()))) {
            while ((length = bufferedInputStream.read(bufferBytes)) >= 0) {
                outputStream.write(bufferBytes, 0, length);
                outputStream.flush();
            }
        }
        return null;
    }
}
