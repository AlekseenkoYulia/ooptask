package task;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyReader {
    DataInputStream is;
    String charset;

    MyReader(DataInputStream is, String charset) {
        this.is = is;
        this.charset = charset;
    }

    public List<String> getHeaders() throws IOException {
        List<String> headers = new ArrayList<String>();
        int c;

        out:
        while (true) {
            StringBuilder line = new StringBuilder();
            while (true) {
                c = is.read();
                if (c == 13) {
                    if (line.length() == 0) {
                        is.read();
                        break out;
                    }
                    is.read();
                    headers.add(line.toString());
                    break;
                }
                line.append((char) c);
            }
        }
        return headers;
    }

    public int getSize() throws IOException {
        int size;
        StringBuilder line = new StringBuilder();
        int c;


        while (true) {
            c = is.read();
            if (c == 13) {
                is.read();
                break;
            }
            line.append((char) c);
        }
        size = Integer.parseInt(line.toString(), 16);
        return size;
    }

    public String getContent(int size) throws IOException {
        byte[] buffer = new byte[size];
        is.readFully(buffer);
        is.read();
        is.read();

        String content = new String(buffer, charset);
        return content;
    }

}
