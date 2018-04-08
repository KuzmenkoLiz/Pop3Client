import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

/**
 * Created by Лиза on 01.03.2018.
 */
public class Pop3Client {
    private SSLSocket socket;
    public BufferedReader reader;
    public PrintWriter writer;

    private static final int DEFAULT_PORT = 995;

    public void connect(String host, int port) throws Exception {

        SSLSocketFactory sslSocketFactory = getFactorySimple();
        socket = (SSLSocket) sslSocketFactory.createSocket(host, port);
        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        reader.readLine();
    }
    private static SSLSocketFactory getFactorySimple() throws Exception {
        SSLContext context = SSLContext.getInstance("SSL");
        context.init(null, null, null);
        return context.getSocketFactory();
    }

    public void connect(String host) throws Exception {
        connect(host, DEFAULT_PORT);
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public void disconnect() throws IOException {
        if (!isConnected())
            throw new IllegalStateException("Not connected to the host");
        socket.close();
        reader = null;
        writer = null;
    }

    public String getResponseLine() throws IOException {
        return reader.readLine();
    }

    String sendCommand(String command) throws IOException {
        writer.write(command + "\n");
        writer.flush();
        return reader.readLine();
    }

    public String login(String username, String password) throws IOException {
        String answer = "";
        answer += sendCommand("USER " + username);
        answer += sendCommand("PASS " + password);
        return answer;
    }

    public String quit() throws IOException {
        return sendCommand("QUIT");
    }

    public String getNumberOfNewMessages() throws IOException {
        String response = sendCommand("STAT");
        String[] values = response.split(" ");
        return String.valueOf(values[1]);
    }

    public String getTextOfMessage(int num) throws IOException {
        String answer = "";
        int mes = Integer.parseInt(getNumberOfNewMessages());
        if (num > mes) {
            answer += "This message doesn't exist" + "\n";
            return answer;
        }
        sendCommand("RETR " + num);
        String line = "";
        while (!(line = reader.readLine()).equals(".")) {
            answer += line + "\n";
        }
        return answer;
    }

    public String deleteMessage(String del) throws IOException {
        return sendCommand("DELE " + del);
    }

    public String getTop(String first, String second) throws IOException {
        return sendCommand("TOP " + first + " " + second);
    }
}
