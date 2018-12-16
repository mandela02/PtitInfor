package ptit.wayne.com.ptitinfor.training;

import java.io.IOException;

public interface OnTaskCompleted<T> {
    void handle(T value) throws IOException;
}
