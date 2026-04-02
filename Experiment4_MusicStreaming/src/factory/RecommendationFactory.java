package factory;

import model.Playlist;
import model.Song;
import model.User;

// Factory Method for generating different types of dynamic recommendations
public class RecommendationFactory {

    public static Playlist generateDailyMix(User user) {
        System.out.println("    [FACTORY] AI analyzing listening history for " + user.getUsername() + "...");
        Playlist mix = new Playlist("RND-001", "Daily Mix for " + user.getUsername());
        
        // Simulating recommendation logic
        mix.addSong(new Song("S10", "Blinding Lights", "The Weeknd", "After Hours", 200));
        mix.addSong(new Song("S11", "Save Your Tears", "The Weeknd", "After Hours", 215));
        mix.addSong(new Song("S12", "Levitating", "Dua Lipa", "Future Nostalgia", 203));
        
        return mix;
    }

    public static Playlist generateWorkoutMix(User user) {
        System.out.println("    [FACTORY] Extracting high-BPM tracks for " + user.getUsername() + "...");
        Playlist mix = new Playlist("RND-002", "High Energy Workout Mix");
        
        mix.addSong(new Song("S20", "Stronger", "Kanye West", "Graduation", 311));
        mix.addSong(new Song("S21", "Can't Hold Us", "Macklemore", "The Heist", 258));
        
        return mix;
    }
}
