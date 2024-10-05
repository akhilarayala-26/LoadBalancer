import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadBalancerTest {
    public static void main(String[] args) {
        // Create backend servers
        BackendServer server1 = new BackendServer("Server 1");
        BackendServer server2 = new BackendServer("Server 2");
        BackendServer server3 = new BackendServer("Server 3");

        List<BackendServer> servers = Arrays.asList(server1, server2, server3);

        // Create a load balancer
        LoadBalancer loadBalancer = new LoadBalancer(servers);

        // Simulate requests with an ExecutorService
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Round-robin algorithm test
        System.out.println("Distributing requests using Round-Robin...");
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> loadBalancer.distributeRequestRoundRobin());
        }

        // Wait for round-robin simulation to finish
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Least-connections algorithm test
        System.out.println("\nDistributing requests using Least-Connections...");
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> loadBalancer.distributeRequestLeastConnections());
        }

        // Shutdown executor service
        executorService.shutdown();
    }
}

