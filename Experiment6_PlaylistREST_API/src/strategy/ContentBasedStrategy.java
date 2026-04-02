package strategy;

public class ContentBasedStrategy implements RecommendationStrategy {

    @Override
    public String generateRecommendations(String userId) {
        System.out.println("    [ALGORITHM] Running Content-Based Filtering for User " + userId + " (Medium Compute)...");
        try { Thread.sleep(400); } catch (InterruptedException e) {} // Simulate compute block
        return "[\"Song X\", \"Song Y\", \"Song Z\"]";
    }
}
