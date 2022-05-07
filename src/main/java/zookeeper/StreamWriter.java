package zookeeper;

import java.io.InputStream;
import java.io.OutputStream;

public class StreamWriter extends Thread{
    OutputStream os;
    InputStream is;

    StreamWriter (InputStream is, OutputStream os) {
        this.is = is;
        this.os = os;
        start();
    }

    @Override
    public void run() {
        byte[] b = new byte[80];
        int rc;
        try {
            while((rc = is.read(b)) > 0) {
                os.write(b,0,rc);
            }

        }  catch (Exception e) {

        }
    }
}
