package controller;

import entity.Movie;
import service.CinemaService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MovieController {
    private final CinemaService cinemaService;
    private final Scanner scanner;

    public MovieController(CinemaService cinemaService, Scanner scanner) {
        this.cinemaService = cinemaService;
        this.scanner = scanner;
    }

    public void viewAllMovies() throws SQLException {
        System.out.println("\n=== ALL MOVIES ===");
        List<Movie> movies = cinemaService.getAllMovies();

        if (movies.isEmpty()) {
            System.out.println("No movies found.");
            return;
        }

        movies.forEach(movie -> {
            System.out.printf("\nID: %d\n", movie.getId());
            System.out.printf("Title: %s\n", movie.getTitle());
            System.out.printf("Genre: %s\n", movie.getGenre());
            System.out.printf("Category: %s\n", movie.getCategory());
            System.out.printf("Duration: %d minutes\n", movie.getDuration());
            System.out.printf("Rating: %.1f/10\n", movie.getRating());
            System.out.printf("Release Date: %s\n", movie.getReleaseDate());
            System.out.println("-------------");
        });
    }

    public void addNewMovie() throws SQLException {
        System.out.println("\n=== ADD NEW MOVIE ===");

        System.out.print("Enter movie title: ");
        String title = scanner.nextLine();

        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        System.out.print("Enter duration (minutes): ");
        int duration = getIntInput();

        System.out.print("Enter rating (0-10): ");
        double rating = getDoubleInput();

        System.out.print("Enter release date (YYYY-MM-DD): ");
        LocalDate releaseDate = LocalDate.parse(scanner.nextLine());

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setCategory(category);
        movie.setDuration(duration);
        movie.setRating(rating);
        movie.setReleaseDate(releaseDate);

        movie = cinemaService.addMovie(movie);

        System.out.println("\nMovie added successfully! ID: " + movie.getId());
    }

    public void viewMoviesByCategory() throws SQLException {
        System.out.println("\n=== MOVIES BY CATEGORY ===");
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        List<Movie> movies = cinemaService.getMoviesByCategory(category);

        if (movies.isEmpty()) {
            System.out.println("No movies found in category: " + category);
            return;
        }

        System.out.println("\nMovies in category '" + category + "':");
        movies.forEach(movie -> {
            System.out.printf("\n  ID: %d - %s\n", movie.getId(), movie.getTitle());
            System.out.printf("  Genre: %s | Duration: %d min | Rating: %.1f/10\n",
                    movie.getGenre(), movie.getDuration(), movie.getRating());
        });
    }

    public void updateMovie() throws SQLException {
        System.out.println("\n=== UPDATE MOVIE ===");

        System.out.print("Enter movie ID to update: ");
        int movieId = getIntInput();

        Movie movie = cinemaService.movieRepository.findById(movieId);
        if (movie == null) {
            System.out.println("Movie not found.");
            return;
        }

        System.out.println("\nCurrent movie details:");
        System.out.printf("Title: %s\n", movie.getTitle());
        System.out.printf("Genre: %s\n", movie.getGenre());
        System.out.printf("Category: %s\n", movie.getCategory());

        System.out.print("\nEnter new title (or press Enter to keep current): ");
        String title = scanner.nextLine();
        if (!title.isEmpty()) {
            movie.setTitle(title);
        }

        System.out.print("Enter new genre (or press Enter to keep current): ");
        String genre = scanner.nextLine();
        if (!genre.isEmpty()) {
            movie.setGenre(genre);
        }

        System.out.print("Enter new category (or press Enter to keep current): ");
        String category = scanner.nextLine();
        if (!category.isEmpty()) {
            movie.setCategory(category);
        }

        cinemaService.movieRepository.update(movie);
        System.out.println("\nMovie updated successfully!");
    }

    public void deleteMovie() throws SQLException {
        System.out.println("\n=== DELETE MOVIE ===");

        System.out.print("Enter movie ID to delete: ");
        int movieId = getIntInput();

        cinemaService.movieRepository.delete(movieId);
        System.out.println("\nMovie deleted successfully!");
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
