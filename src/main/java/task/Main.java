package task;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String host = "vpustotu.ru";
        Client c1 = new Client(host);
        Scanner in = new Scanner(System.in);

        c1.makeRequest(2000);
//        c1.makeRequest(in.nextInt());
//        while(true) {
//            c1.makeRequest(in.nextInt());
//        }
    }
}
