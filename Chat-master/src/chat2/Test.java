package chat2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s=in.readLine();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(8);

            } catch (InterruptedException e) {

            }
            //System.out.flush();
            System.out.println(23);
        }).start();
        System.out.println(s);
    }
}

