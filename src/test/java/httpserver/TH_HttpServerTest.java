package httpserver;
import com.sun.net.httpserver.HttpServer;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.*;
import java.util.concurrent.Executors;

//import static org.junit.Assert.*;

public class TH_HttpServerTest {
    HttpURLConnection connection = null;
    static String urlParameters;
    static {
        try {
            urlParameters =
                    URLEncoder.encode("Testing", "UTF-8") +
                            URLEncoder.encode(" Body", "UTF-8");
        } catch (Exception IOException) {
            System.out.println("IOException");
        }
    }
    @Test
    public void ProductGET() throws IOException {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
            server.createContext("/product", new TH_HttpServer.ProductHandler());
            server.setExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
            server.start();

            System.out.println("Server started");

            URL url = new URL("http://localhost:8080/product");
            connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream()
            );
            wr.writeBytes(urlParameters);
            wr.close();
            System.out.println("Connection successful");

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            server.stop(1);
            System.out.println("Server stopped");
            return;
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
            return;
        } finally {
            System.out.println("Finally");
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    @Test
    public void CustomerPUT() throws IOException {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
            server.createContext("/customer", new TH_HttpServer.CustomerHandler());
            server.setExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
            server.start();

            System.out.println("Server started");

            URL url = new URL("http://localhost:8080/customer");
            connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream()
            );
            wr.writeBytes(urlParameters);
            wr.close();
            System.out.println("Connection successful");

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            server.stop(1);
            System.out.println("Server stopped");
            return;
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
            return;
        } finally {
            System.out.println("Finally");
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}