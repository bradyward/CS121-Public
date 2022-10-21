import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * JukeboxHero class acts as a song catalog manager
 *
 * @author Brady Ward
 * @version cs121 Fall 2022
 */
public class JukeboxHero {
    /**
     * Main method of JukeboxHero class. It creates a UI to allow someone to interact with
     * User can input:
     * - l to load a file
     * - s to seach for a term that is in song names
     * - a to get info about the current catalog
     * - p to print ou the whole catalog
     * - q to quit the program
     * Inputs are not case sensetive, no params
     */
    public static void main(String args[]) { //TODO add method calls for every case, not directly in main for EC
        // Setup song list and keybaord scanner
        ArrayList<Song> songList = new ArrayList<Song>();
        Scanner keyboard = new Scanner(System.in);
        // Print menu and take first input
        printMenu();
        while (true) {
            System.out.println("Please enter a command (press 'm' for Menu): ");
            switch (keyboard.nextLine().toLowerCase()) {
                case "m":
                    // Display the menu again
                    printMenu();
                    break;

                case "l":
                    // Collect file name and open it
                    System.out.println("Enter a file name to load");
                    File musicFile = new File(keyboard.nextLine());
                    try {
                        // Clear out any old song list
                        songList.clear();
                        // Make a file scanner
                        Scanner fileScanner = new Scanner(musicFile);
                        while (fileScanner.hasNextLine()) {
                            // Make a line scanner
                            Scanner lineScanner = new Scanner(fileScanner.nextLine());
                            lineScanner.useDelimiter(",");
                            // Get all elements of the song
                            String artist = lineScanner.next();
                            String album = lineScanner.next();
                            String title = lineScanner.next();
                            int time = Integer.parseInt(lineScanner.next());
                            // Create song object then add it to list
                            Song newSong = new Song(title, artist, album, time);
                            songList.add(newSong);
                        }
                        // Give user success message
                        System.out.println("Successfully loaded " + songList.size() + " songs!");
                        break;
                    }
                    // Give user failure message
                    catch (FileNotFoundException fnf) {
                        System.out.println("Unable to open file: invalid .csv file");
                        break;
                    }

                case "s":
                    // Create new list to store searched songs. Collect user search term
                    ArrayList<Song> searchResults = new ArrayList<Song>();
                    System.out.println("Please enter a search query: ");
                    String searchTerm = keyboard.nextLine();
                    // Check every song for a match to search term
                    for (Song song : songList) {
                        if (song.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                            searchResults.add(song);
                        }
                    }
                    // Display search results
                    System.out.println("Search found " + searchResults.size() + " matches:");
                    System.out.println("-".repeat(30));
                    for (Song result : searchResults) {
                        System.out.println(result.toString());
                    }
                    break;

                case "a":
                    // Analyze playlist data. Create 2 lists to track artists and albums and a variable for total time
                    ArrayList<String> artists = new ArrayList<String>();
                    ArrayList<String> albums = new ArrayList<String>();
                    int totalTime = 0;
                    // Check every song, add non-duplicate artists and albums to correct list. Accumulate total time
                    for (Song song : songList) {
                        if (!artists.contains(song.getArtist())) {
                            artists.add(song.getArtist());
                        }
                        if (!albums.contains(song.getAlbum())) {
                            albums.add(song.getAlbum());
                        }
                        totalTime += song.getPlayTime();
                    }
                    // Display analysis info
                    System.out.println("Catalog Analysis...");
                    System.out.println("\tNumber of Artists: " + artists.size());
                    System.out.println("\tNumber of Albums: " + albums.size());
                    System.out.println("\tNumber of Songs: " + songList.size());
                    System.out.println("\tCatalog Playtime: " + totalTime);
                    break;

                case "p":
                    // Display all songs in playlist
                    if (songList.size() == 0) {
                        System.out.println("Playlist has 0 songs");
                        break;
                    }
                    System.out.println("Playlist has " + songList.size() + " total songs ");
                    System.out.println("-".repeat(30));
                    for (Song song : songList) {
                        System.out.println(song.toString());
                    }
                    break;

                case "q":
                    // Quit program
                    System.out.println("Quitting program now");
                    return;

                default:
                    // Tell user and incorrect input was given
                    System.out.println("Invalid selection!");
            }
        }
    }

    /**
     * Simple print method for the UI menu. No inputs
     */
    public static void printMenu() {
        System.out.println("*".repeat(29));
        System.out.println("*" + " ".repeat(6) + "Program Menu" + " ".repeat(9) + "*");
        System.out.println("*".repeat(29));
        System.out.println("(L)oad catalog");
        System.out.println("(S)earch catalog");
        System.out.println("(A)nalyze catalog");
        System.out.println("(P)rint catalog");
        System.out.println("(Q)uit" + "\n");
    }
}
