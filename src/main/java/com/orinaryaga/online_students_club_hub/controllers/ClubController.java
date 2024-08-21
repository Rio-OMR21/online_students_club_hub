package com.orinaryaga.online_students_club_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orinaryaga.online_students_club_hub.models.Chat;
import com.orinaryaga.online_students_club_hub.models.Club;
import com.orinaryaga.online_students_club_hub.services.ClubService;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/club")
public class ClubController {

    @Autowired
    private ClubService clubService;

    // Register a new club
    @PostMapping("/create")
    public ResponseEntity<Club> registerClub(@RequestBody Club club) {
        try {
            Club createdClub = clubService.registerClub(club);
            return new ResponseEntity<>(createdClub, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update existing club
    @PutMapping("/update/{clubId}")
    public ResponseEntity<Club> updateClub(@PathVariable Long clubId, @RequestBody Club club) {
        club.setClubId(clubId); // Ensure correct club ID is set
        try {
            Club updatedClub = clubService.updateClub(club);
            return new ResponseEntity<>(updatedClub, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get club by ID
    @GetMapping("/{clubId}")
    public ResponseEntity<Club> getClubById(@PathVariable Long clubId) {
        Optional<Club> club = clubService.getClubById(clubId);
        return club.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all clubs
    @GetMapping("/all")
    public ResponseEntity<List<Club>> getAllClubs() {
        List<Club> clubs = clubService.getAllClubs();
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }

    // Delete club by ID
    @DeleteMapping("/delete/{clubId}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long clubId) {
        try {
            clubService.deleteClub(clubId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Add a chat to a club
    @PostMapping("/{clubId}/addChat")
    public ResponseEntity<Chat> addChatToClub(@PathVariable Long clubId, @RequestBody Chat chat) {
        try {
            Chat createdChat = clubService.addChatToClub(clubId, chat);
            return new ResponseEntity<>(createdChat, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   
}
