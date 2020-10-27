package httpserver.th_classes;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

interface RootPath extends HttpHandler{         // List of possible methods || The child classes should implement this. Is that efficient?
    public void GET(String read);
    public void POST(String read);
    public void PUT(String read);
    public void DELETE(String read);
}

class RootClass implements  HttpHandler{        // List of possible methods || I guess this one is more efficient. Some classes might not use some methods.

    static public List<String> methods;
    static public int method_num;
    //    static public List
    static{
        method_num=4;
        methods = new ArrayList<String>();
        methods.add("GET");
        methods.add("POST");
        methods.add("PUT");
        methods.add("DELETE");
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        byte[] readBytes = httpExchange.getRequestBody().readAllBytes();
        String read = new String(readBytes, StandardCharsets.UTF_8.name());
        int method_idx = 0;
        String method = httpExchange.getRequestMethod();
        for (String met : methods)
            if (method.equals(met))
                break;
            else
                method_idx += 1;
        switch (method_idx) {
            case 0:
                GET(read);
                break;
            case 1:
                POST(read);
                break;
            case 2:
                PUT(read);
                break;
            case 3:
                DELETE(read);
                break;
        }
        System.out.println("Request Body: " + read);
        httpExchange.sendResponseHeaders(200, readBytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(readBytes);
        os.flush();
    }
    public void GET(String read)
    {
        System.out.println("RootClass Handler's GET ");
    }
    public void POST(String read)
    {
        System.out.println("RootClass Handler's POST ");
    }
    public void PUT(String read)
    {
        System.out.println("RootClass Handler's PUT ");
    }
    public void DELETE(String read)
    {
        System.out.println("RootClass Handler's DELETE ");
    }
}


public class TH_HttpServer{
    static class ProductHandler extends RootClass {

//        private static LazyEmbedDB product_instance; //share one DB instance with child classes
//        public static LazyEmbedDB getInstance()
//        {
//            if(product_instance==null)
//                product_instance= new LazyEmbedDB();
//            return product_instance;
//        }

        @Override
        public void GET(String read){
            System.out.println("Product Handler's GET ");
        }
        @Override
        public void POST(String read){
            System.out.println("Product Handler's POST ");
        }
        @Override
        public void PUT(String read){
            System.out.println("Product Handler's PUT ");
        }
        @Override
        public void DELETE(String read){
            System.out.println("Product Handler's DELETE ");
        }
    }
    static class Product_Category_Handler extends ProductHandler {
        @Override
        public void GET(String read){
            System.out.println("Category Handler's GET ");
        }
        @Override
        public void POST(String read){
            System.out.println("Category Handler's POST ");
        }
        @Override
        public void PUT(String read){
            System.out.println("Category Handler's PUT ");
        }
        @Override
        public void DELETE(String read){
            System.out.println("Category Handler's DELETE ");
        }
    }
    static class Product_Item_Handler extends Product_Category_Handler {
        @Override
        public void GET(String read){
            System.out.println("Item Handler's GET ");
        }
        @Override
        public void POST(String read){
            System.out.println("Item Handler's POST ");
        }
        @Override
        public void PUT(String read){
            System.out.println("Item Handler's PUT ");
        }
        @Override
        public void DELETE(String read){
            System.out.println("Item Handler's DELETE ");
        }
    }

    static class CustomerHandler extends RootClass {
        @Override
        public void GET(String read){
            System.out.println("Customer Handler's GET ");
        }
        @Override
        public void POST(String read){
            System.out.println("Customer Handler's POST ");
        }
        @Override
        public void PUT(String read){
            System.out.println("Customer Handler's PUT ");
        }
    }
    static class Customer_ID_Handler extends CustomerHandler {
        @Override
        public void GET(String read){
            System.out.println("Customer_ID Handler's GET ");
        }
        @Override
        public void POST(String read){
            System.out.println("Customer_ID Handler's POST ");
        }
        @Override
        public void PUT(String read){
            System.out.println("Customer_ID Handler's PUT ");
        }
    }
    static class Customer_Page_Handler extends Customer_ID_Handler {
        @Override
        public void GET(String read){
            System.out.println("Customer_Page Handler's GET ");
        }
        @Override
        public void POST(String read){
            System.out.println("Customer_Page Handler's POST ");
        }
        @Override
        public void PUT(String read){
            System.out.println("Customer_Page Handler's PUT ");
        }
    }

    static class StaffHandler extends RootClass {
        @Override
        public void GET(String read){
            System.out.println("Staff Handler's GET ");
        }
        @Override
        public void POST(String read){
            System.out.println("Staff Handler's POST ");
        }
        @Override
        public void PUT(String read){
            System.out.println("Staff Handler's PUT ");
        }
    }
    static class Staff_Position_Handler extends StaffHandler {
        @Override
        public void GET(String read){
            System.out.println("Staff Position Handler's GET ");
        }
        @Override
        public void POST(String read){
            System.out.println("Staff Position Handler's POST ");
        }
        @Override
        public void PUT(String read){
            System.out.println("Staff Position Handler's PUT ");
        }
    }
    static class Staff_ID_Handler extends Staff_Position_Handler {
        @Override
        public void GET(String read){
            System.out.println("Staff ID Handler's GET ");
        }
        @Override
        public void POST(String read){
            System.out.println("Staff ID Handler's POST ");
        }
        @Override
        public void PUT(String read){
            System.out.println("Staff ID Handler's PUT ");
        }
    }
    static class Staff_Page_Handler extends Staff_ID_Handler {
        @Override
        public void GET(String read){
            System.out.println("Staff Page Handler's GET ");
        }
        @Override
        public void POST(String read){
            System.out.println("Staff Page Handler's POST ");
        }
        @Override
        public void PUT(String read){
            System.out.println("Staff Page Handler's PUT ");
        }
    }



    public static void main(String [] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        server.createContext("/product",new ProductHandler());
        server.createContext("/product/category",new Product_Category_Handler());
        server.createContext("/product/category/item",new Product_Item_Handler());

        server.createContext("/customer",new CustomerHandler());
        server.createContext("/customer/id",new Customer_ID_Handler());
        server.createContext("/customer/id/own_pages",new Customer_Page_Handler());

        server.createContext("/staff",new StaffHandler());
        server.createContext("/staff/position",new Staff_Position_Handler());
        server.createContext("/staff/position/id",new Staff_ID_Handler());
        server.createContext("/staff/position/id/own_pages",new Staff_Page_Handler());

        server.setExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        server.start();
    }

}