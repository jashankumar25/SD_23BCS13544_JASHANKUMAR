package strategy;

public class CollaborativeFilteringStrategy implements RecommendationStrategy {

    @Override
    public String generateRecommendations(String userId) {
        System.out.println("    [ALGORITHM] Running Collaborative Filtering for User " + userId + " (Heavy Compute)...");
        try { Thread.sleep(800); } catch (InterruptedException e) {} // Simulate compute block
        return "[\"Song A\", \"Song B\", \"Song C\"]";
    }
}
