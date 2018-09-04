package task;

import java.io.IOException;

public class ChunkedStrategy  extends Strategy{
    public ChunkedStrategy(MyReader reader){
        super(reader);
    }
    public String getContent() throws IOException{
        StringBuilder content = new StringBuilder();
        int size = reader.getSize();

        while (size > 0) {
            content.append(reader.getContent(size));
            size = reader.getSize();
        }

    return content.toString();
    }
}
