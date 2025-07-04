### Java Music Store - Learning Journey & Technical Milestones

This project served as a learning sandbox for integrating API-based data into a modern Java Swing application. It evolved from basic UI rendering to a feature-rich desktop app consuming the Deezer API and included audio playback, dynamic layout design, and modular UI practices.

---

## Project Initialization

- Maven-based project created with a basic `Main.java` entry point
- Set up `pom.xml` with dependencies including:
  - Gson (for JSON parsing)
  - Basic MP3 player libraries (later JLayer)
  - Swing (standard Java UI toolkit)

---

## Deezer API Integration

- Established basic HTTP request to Deezer's search endpoint
- Created models for mapping JSON responses:
  - `Track`, `Album`, `Artist` (with Gson annotations)
- Printed search results to the console as a first step

---

## Search UI (Swing + Layout Foundations)

- Set up the main `JFrame` with basic layout
- Created a `JTextField` for search input and button to trigger API request
- Built first result renderer using `JTextArea`
- Refactored to use a scrollable `JPanel` with `FlowLayout` to render individual search result cards
- Album cards included title and image, dynamically downloaded and scaled

---

## Improved Result Card Layout

- Added an additional info panel inside result cards for artist and album info
- Applied naming conventions across variables (e.g., `pnlResults`, `lblTitle`)
- Dynamically calculated how many rows to show based on image height and layout width

---

## Clickable Albums → Details Modal

- Made album titles clickable
- Added logic to open a custom modal (`JDialog`) when clicking an album card
- Modal fetches full album details using Deezer’s album endpoint
- Created structured modal layout using Swing components:
  - Large album art
  - Album title, artist, genre
  - Track list

---

## Refactoring Models & UI Code

- Refactored model structure:
  - Introduced `Release` interface to simplify handling of Album vs Track
  - Added `Genre` model
- Broke out UI logic from main window to separate methods and classes (separation of concerns)

---

## Tracklist Enhancements

- Added logic for multi-disk albums (showing "Disk 1", "Disk 2" headers)
- Listed track duration next to each track

---

## Audio Previews

- Added ability to play 30-second MP3 previews from Deezer
- Integrated `JLayer` MP3 player
- Preview buttons added to tracklist

---

## Modal Layout Improvements

- Modal window resized dynamically based on content height
- Extracted layout responsibilities for album/track/mp3player sections into modular methods

---

## Bug Fixes & Edge Cases

- Replaced `int` ID fields with `long` due to large Deezer IDs
- Handled null values in JSON where fields might be missing (e.g., genres)
- Ensured UI doesn't crash on incomplete or invalid API responses

---

## Java Concepts Practiced

- Maven project setup and dependency management
- Modular UI design with Swing
- JSON data binding with Gson
- HTTP requests and response parsing
- Event-driven programming and listeners
- Modal and dialog design patterns
- JLayer integration for media playback
- Dynamic layout calculations (e.g., content height estimation)

---

## Planning Forward

- This version of the app is now considered "parked" as we shift focus to building a **Spring Boot web version** of the music store.
- Planned features for the Spring Boot version include:
  - User authentication and login system
  - Shopping cart and checkout functionality
  - Simulated payment and order flow
  - Improved frontend integration with RESTful APIs
- Additional backend improvements under consideration:
  - Enhanced use of `ResponseEntity`
  - Unit and integration testing with Spring Boot Test
  - Deployment to cloud services (Render, Fly.io, etc.)
