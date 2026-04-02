import model.Playlist;
import model.Song;
import model.User;
import observer.Artist;
import singleton.MusicPlayer;
import strategy.DataSaverStrategy;
import strategy.HighResLosslessStrategy;
import factory.RecommendationFactory;

/**
 * Experiment 4: Music Streaming Service LLD
 *
 * Design Patterns Demonstrated:
 *   1. Singleton       - MusicPlayer (one instance manages playback state globally)
 *   2. Observer        - Artist (Subject) notifies User (Follower) upon new song release
 *   3. Strategy        - Audio streaming quality (DataSaverStrategy, HighResLosslessStrategy)
 *   4. Factory Method  - RecommendationFactory creates personalized playlists
 * 
 * SOLID Principles Applied:
 *   S - Song, Playlist, Artist, User each have single focused responsibilities
 *   O - StreamingStrategy allows adding new qualities without touching MusicPlayer
 *   L - Different strategies (DataSaver) seamlessly substitute StreamingStrategy
 *   I - Follower interface is focused precisely on notifications
 *   D - MusicPlayer depends on StreamingStrategy (abstraction), not concrete classes
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║      EXPERIMENT 4: MUSIC STREAMING SERVICE LLD                      ║");
        System.out.println("║      Design Patterns: Singleton, Factory, Observer, Strategy         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");

        // Set up base users and artists
        User alice = new User("U001", "Alice");
        User bob = new User("U002", "Bob");

        Artist theWeeknd = new Artist("The Weeknd");
        Artist eminem = new Artist("Eminem");

        // ─────────────────────────────────────────────
        // 1. OBSERVER PATTERN - Artist Following
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 1: OBSERVER PATTERN — Artist Follows & Notifications");
        System.out.println("══════════════════════════════════════════════════════════════");
        
        theWeeknd.addFollower(alice);
        eminem.addFollower(alice);
        eminem.addFollower(bob);

        // Artist releases a new song - notify followers
        Song starboy = new Song("S001", "Starboy", "The Weeknd", "Starboy", 230);
        Song mockingbird = new Song("S002", "Mockingbird", "Eminem", "Encore", 251);

        theWeeknd.releaseNewSong(starboy);
        eminem.releaseNewSong(mockingbird);

        // ─────────────────────────────────────────────
        // 2. MAIN OOPS - Playlist Creation
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 2: OOP & PLAYLIST MANAGEMENT");
        System.out.println("══════════════════════════════════════════════════════════════");
        
        alice.createPlaylist("Favorites");
        Playlist aliceFavs = alice.getPlaylist("Favorites");
        aliceFavs.addSong(starboy);
        aliceFavs.addSong(mockingbird);
        aliceFavs.displayPlaylist();

        // ─────────────────────────────────────────────
        // 3. FACTORY METHOD PATTERN - Recommendations
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 3: FACTORY METHOD PATTERN — Smart Recommendations");
        System.out.println("══════════════════════════════════════════════════════════════");
        
        Playlist dailyMix = RecommendationFactory.generateDailyMix(alice);
        dailyMix.displayPlaylist();

        Playlist workoutMix = RecommendationFactory.generateWorkoutMix(bob);
        workoutMix.displayPlaylist();

        // ─────────────────────────────────────────────
        // 4. SINGLETON & STRATEGY PATTERNS - Playback
        // ─────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  STEP 4: SINGLETON & STRATEGY PATTERNS — Music Playback");
        System.out.println("══════════════════════════════════════════════════════════════");
        
        // Single central player instance
        MusicPlayer player = MusicPlayer.getInstance();
        
        // Play song on Wifi (High-Res Lossless)
        System.out.println("\n  --- Scene: User on Home WiFi ---");
        player.setStreamingStrategy(new HighResLosslessStrategy());
        player.playSong(starboy);
        
        player.pause();

        // Play song on Mobile Data (Data Saver)
        System.out.println("\n  --- Scene: User commuting on Mobile Data ---");
        player.setStreamingStrategy(new DataSaverStrategy());
        player.playSong(mockingbird);

        // Play an entire playlist!
        System.out.println("\n  --- Scene: User playing entire Daily Mix ---");
        player.playPlaylist(dailyMix);

        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  EXPERIMENT 4 COMPLETED SUCCESSFULLY");
        System.out.println("══════════════════════════════════════════════════════════════\n");
    }
}
