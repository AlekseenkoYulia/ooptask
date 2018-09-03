package task;

import java.io.*;
import java.net.*;
//import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private String host;
    private int port;

    public Client(String host) {
        this.host = host;
        this.port = 80;
    }

    public Client(String host, int port) {
        this(host);
        this.port = port;
    }

    public Socket openSocket() {
        try {
            Socket s = new Socket(host, port);
            return s;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void makeRequest(int n) {
        String request = "GET /story/" + n + " HTTP/1.1\nHost:" + host + "\n\n";
        try {
            Socket s = openSocket();
            OutputStream out = s.getOutputStream();
            InputStream is = s.getInputStream();
            //InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            //out.write(request.getBytes(StandardCharsets.UTF_8));
            out.write(request.getBytes("utf-8"));

//            byte[] buf = new byte[20];
//            is.read(buf);
//            String s1 = new String(buf, StandardCharsets.US_ASCII);


            List<String> headers = getHeaders(is);
            if (!(headers.get(0).equals("HTTP/1.1 200 OK"))){
                System.out.println("Ошибка");
                return;
            }
            System.out.println(headers);

            StringBuilder content = new StringBuilder();
            int size = getSize(is);
            System.out.println("size " + size);
            content.append(getContent(is, size));
            //System.out.println(content);
            //size = getSize(is);
            //System.out.println("size " + size);


            //while(size > 0){
            //content.append(getContent(is, size));
            //size = getSize(reader);
            //}

//            findStory(content.toString());

            s.close();
            out.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getHeaders(InputStream reader) throws IOException{
        List<String> headers = new ArrayList<String>();
        int c;

        out:
        while(true){
            StringBuilder line = new StringBuilder();
            while(true){
                c = reader.read();
                if(c == 13){
                    if(line.length()== 0){
                        reader.read();
                        break out;
                    }
                    reader.read();
                    headers.add(line.toString());
                    break;
                }
                line.append((char)c);
            }
        }
        return headers;
    }

    public int getSize(InputStream reader) throws IOException{
        int size;
        StringBuilder line = new StringBuilder();
        int c;


        while(true){
            c = reader.read();
            if(c == 13){
                reader.read();
                break;
            }
            line.append((char)c);
        }
        size = Integer.parseInt(line.toString(), 16);
        return size;
    }

    public String getContent(InputStream is, int size) throws IOException{
        byte[] buffer = new byte[size];
        is.read(buffer);
        String content = new String(buffer, "utf-8");
        return content;
    }

    public void findStory(String s) {
        String start = "<meta property=\"og:description\" content=";
        int startIndex = s.indexOf(start, 0) + start.length();
        int endIndex = s.indexOf(">", startIndex);
        String story = s.substring(startIndex, endIndex);
        System.out.println(story);
    }
}

