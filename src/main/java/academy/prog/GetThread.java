package academy.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GetThread implements Runnable {
    private final Gson gson;
    private final String myLogin;
    private int n; // /get?from=n

    public GetThread(String myLogin) {
        this.myLogin = myLogin;
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    @Override
    public void run() { // WebSockets

        try {
            while (!Thread.interrupted()) {
                String qLogin = URLEncoder.encode(myLogin, StandardCharsets.UTF_8);
                URL url = new URL(Utils.getURL() + "/get?for=" + qLogin + "&from=" + n);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                InputStream is = http.getInputStream();
                try {
                    byte[] buf = responseBodyToArray(is);
                    String strBuf = new String(buf, StandardCharsets.UTF_8);

                    JsonMessages list = gson.fromJson(strBuf, JsonMessages.class);
                    n = list.getNewIndex();
                    if (list != null) {
                        for (Message m : list.getList()) {
                            System.out.println(m);
                        }
                    }
                } finally {
                    is.close();
                }

                // C -> S -> x
                // WebSockets

                Thread.sleep(500);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private byte[] responseBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }
}
