import java.util.concurrent.atomic.AtomicInteger;

public class BackendServer {
    private String name;
    private AtomicInteger activeConnections;

    public BackendServer(String name) {
        this.name = name;
        this.activeConnections = new AtomicInteger(0);
    }

    public String getName() {
        return name;
    }

    public int getActiveConnections() {
        return activeConnections.get();
    }

    public void handleRequest() {
        System.out.println(name + " is handling a request...");
        activeConnections.incrementAndGet();
        try {
            Thread.sleep(2000); // Simulate time to process request
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activeConnections.decrementAndGet();
    }
}
 