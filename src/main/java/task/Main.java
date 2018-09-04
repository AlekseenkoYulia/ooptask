package task;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //String host = "vpustotu.ru";
        String host = "bash.im";
        int port = 443;
        Client c = new Client(host, port, Protocol.HTTPS);
        c.charset = "windows-1251";
        Scanner in = new Scanner(System.in);

        c.makeRequest(452072);
        //c.makeRequest(in.nextInt());
//        while(true) {
//            c.makeRequest(in.nextInt());
//        }
    }
}
