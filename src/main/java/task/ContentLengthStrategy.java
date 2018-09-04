package task;

import java.io.IOException;

public class ContentLengthStrategy extends Strategy {
    int size;

    public ContentLengthStrategy(MyReader reader, int size){
        super(reader);
        this.size = size;
    }

    public String getContent()throws IOException {
        StringBuilder content = new StringBuilder();
        content.append(reader.getContent(size));
        return content.toString();
    }
}
