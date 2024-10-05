import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executors;

public class LoadBalancerAPI {

    private static LoadBalancer loadBalancer;

    public static void main(String[] args) throws IOException {
        // Create backend servers
        BackendServer server1 = new BackendServer("Server 1");
        BackendServer server2 = new BackendServer("Server 2");
        BackendServer server3 = new BackendServer("Server 3");

        List<BackendServer> servers = List.of(server1, server2, server3);

        // Create a load balancer
        loadBalancer = new LoadBalancer(servers);

        // Set up the API server
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/status", new StatusHandler());
        server.createContext("/api/send-request", new RequestHandler());
        server.setExecutor(Executors.newFixedThreadPool(5)); // creates a default executor
        server.start();
        System.out.println("Server started on port 8080...");
    }

    // Handler for getting the status of backend servers
    static class StatusHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                JSONArray serverArray = new JSONArray();
                for (BackendServer server : loadBalancer.getServers()) {
                    JSONObject serverObject = new JSONObject();
                    serverObject.put("name", server.getName());
                    serverObject.put("activeConnections", server.getActiveConnections());
                    serverArray.add(serverObject); // Add to the array
                }

                JSONObject responseJson = new JSONObject();
                responseJson.put("servers", serverArray); // Put the array inside the JSON response

                String jsonResponse = responseJson.toJSONString(); // Convert the JSON object to string
                byte[] responseBytes = jsonResponse.getBytes(); // Convert the string to bytes

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(responseBytes);
                os.close();
            }
        }
    }

    // Handler for sending a request to the load balancer
    static class RequestHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String algorithm = query.split("=")[1]; // Extract algorithm from query

                if (algorithm.equals("round-robin")) {
                    loadBalancer.distributeRequestRoundRobin();
                } else if (algorithm.equals("least-connections")) {
                    loadBalancer.distributeRequestLeastConnections();
                }

                exchange.sendResponseHeaders(200, -1); // 200 OK with no body
            }
        }
    }
}
