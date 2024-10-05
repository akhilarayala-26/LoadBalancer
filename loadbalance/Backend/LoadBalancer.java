import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalancer {
    private List<BackendServer> servers;
    private AtomicInteger roundRobinIndex;

    public LoadBalancer(List<BackendServer> servers) {
        this.servers = servers;
        this.roundRobinIndex = new AtomicInteger(0);
    }

    // Round-robin algorithm
    public BackendServer getNextServerRoundRobin() {
        int index = roundRobinIndex.getAndUpdate(i -> (i + 1) % servers.size());
        return servers.get(index);
    }

    // Least-connections algorithm
    public BackendServer getNextServerLeastConnections() {
        return servers.stream()
                .min((s1, s2) -> Integer.compare(s1.getActiveConnections(), s2.getActiveConnections()))
                .orElseThrow(() -> new IllegalStateException("No servers available"));
    }

    public void distributeRequestRoundRobin() {
        BackendServer server = getNextServerRoundRobin();
        server.handleRequest();
    }

    public void distributeRequestLeastConnections() {
        BackendServer server = getNextServerLeastConnections();
        server.handleRequest();
    }

    public List<BackendServer> getServers() {
        return servers;
    }
}
