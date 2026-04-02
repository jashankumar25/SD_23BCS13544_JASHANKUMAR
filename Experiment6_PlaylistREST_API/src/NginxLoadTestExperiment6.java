import java.net.http.*;
import java.net.URI;
import java.time.Duration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class NginxLoadTestExperiment6 {
    // 8080 is the port configured in YOUR nginx.conf
    private static final String NGINX_URL = "http://localhost:8080/api/v1/playlists/PL100"; 
    private static final int THREAD_COUNT = 10;
    private static final int REQUESTS_PER_THREAD = 100;
    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Nginx Load Test on Experiment 6 Playlist API...");
        System.out.println("Targeting NGINX URL: " + NGINX_URL);
        
        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger failure = new AtomicInteger(0);
        
        // Track which backend server responded so we can prove load balancing works!
        AtomicInteger port3000Count = new AtomicInteger(0);
        AtomicInteger port3001Count = new AtomicInteger(0);
        
        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int t = 0; t < THREAD_COUNT; t++) {
            executor.submit(() -> {
                for (int i = 0; i < REQUESTS_PER_THREAD; i++) {
                    try {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(NGINX_URL))
                                .timeout(Duration.ofSeconds(3))
                                .GET()
                                .build();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        
                        if (response.statusCode() == 200) {
                            success.incrementAndGet();
                            // Analyze the body to see who handled it
                            if (response.body().contains("PORT 3000")) {
                                port3000Count.incrementAndGet();
                            } else if (response.body().contains("PORT 3001")) {
                                port3001Count.incrementAndGet();
                            }
                        } else {
                            failure.incrementAndGet();
                        }
                    } catch (Exception e) {
                        failure.incrementAndGet();
                    }
                }
                latch.countDown();
            });
        }

        latch.await();
        executor.shutdown();
        long duration = System.currentTimeMillis() - startTime;

        System.out.println("=================================================");
        System.out.println("      NGINX LOAD BALANCER EFFICIENCY REPORT      ");
        System.out.println("=================================================");
        System.out.println("Total requests hit:    " + (THREAD_COUNT * REQUESTS_PER_THREAD));
        System.out.println("Successful responses:  " + success.get());
        System.out.println("Failed responses:      " + failure.get());
        System.out.println("Total Time (ms):       " + duration);
        System.out.printf("Throughput:            %.2f req/sec%n", (THREAD_COUNT * REQUESTS_PER_THREAD) / (duration / 1000.0));
        
        System.out.println("\n--- NGINX LOAD DISTRIBUTION ---");
        System.out.println("Requests routed to Port 3000: " + port3000Count.get());
        System.out.println("Requests routed to Port 3001: " + port3001Count.get());
        
        if (Math.abs(port3000Count.get() - port3001Count.get()) < (0.1 * (THREAD_COUNT * REQUESTS_PER_THREAD))) {
            System.out.println("CONCLUSION: Nginx efficiently and fairly distributed the load! Round-Robin acts perfectly!");
        }
    }
}
