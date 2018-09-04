package task;

import javax.net.SocketFactory;
import java.io.*;
import java.net.*;
import java.util.List;

public class Client {
    private String host;
    private int port;
    private Protocol protocol;
    String charset;
    private MyReader reader;
    private Strategy strategy;

    public Client(String host, int port, Protocol protocol) {
        this.host = host;
        this.port = port;
        this.protocol = protocol;
    }

    public Socket openSocket() throws IOException {
        SocketFactory factory = protocol.getSocketFactory();
        return factory.createSocket(host, port);
    }

    public void makeRequest(int n) {
        String request = "GET /quote/" + n + " HTTP/1.1\nHost:" + host + "\n\n";
        //String request = "GET /story/" + n + " HTTP/1.1\nHost:" + host + "\n\n";
        try {
            Socket s = openSocket();
            OutputStream out = s.getOutputStream();
            DataInputStream is = new DataInputStream(s.getInputStream());
            out.write(request.getBytes(charset));
            reader = new MyReader(is, charset);

            List<String> headers = reader.getHeaders();
            if (!(headers.get(0).equals("HTTP/1.1 200 OK"))) {
                System.out.println("Ошибка");
                return;
            }

            for (int i = 1; i < headers.size(); i++){
                if (headers.get(i).indexOf("Transfer-Encoding: chunked") >= 0){
                    strategy = new ChunkedStrategy(reader);
                    break;
                }
                if(headers.get(i).indexOf("Content-Length") >= 0) {
                    int size = Integer.parseInt(headers.get(i).substring(15));
                    strategy = new ContentLengthStrategy(reader, size);
                    break;
                }
            }

            String content = strategy.getContent();

            findStory(content);

            s.close();
            out.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void findStory(String s) {
        //String start = "<meta property=\"og:description\" content=";
        String start = "<div class=\"text\">";
        int startIndex = s.indexOf(start, 0) + start.length();
        int endIndex = s.indexOf("</div>", startIndex);
        String story = s.substring(startIndex, endIndex - 1);
        System.out.println(story);
    }
}

