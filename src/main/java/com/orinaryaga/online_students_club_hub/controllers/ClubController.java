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

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    @Autowired
    private ClubService clubService;

    // Register a new club
    @PostMapping("/register")
    public ResponseEntity<Club> registerClub(@RequestBody Club club) {
        try {
            Club createdClub = clubService.registerClub(club);
            return new ResponseEntity<>(createdClub, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update existing club
    @PutMapping("/{clubId}")
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
    @GetMapping
    public ResponseEntity<List<Club>> getAllClubs() {
        List<Club> clubs = clubService.getAllClubs();
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }

    // Delete club by ID
    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long clubId) {
        try {
            clubService.deleteClub(clubId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Add a member to a club
    @PostMapping("/{clubId}/addMember/{userId}")
    public ResponseEntity<Club> addMemberToClub(@PathVariable Long clubId, @PathVariable Long userId) {
        try {
            Club updatedClub = clubService.addMemberToClub(clubId, userId);
            return new ResponseEntity<>(updatedClub, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Remove a member from a club
    @DeleteMapping("/{clubId}/removeMember/{userId}")
    public ResponseEntity<Club> removeMemberFromClub(@PathVariable Long clubId, @PathVariable Long userId) {
        try {
            Club updatedClub = clubService.removeMemberFromClub(clubId, userId);
            return new ResponseEntity<>(updatedClub, HttpStatus.OK);
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

    // // Get all chats for a club
    // @GetMapping("/{clubId}/chats")
    // public ResponseEntity<List<Chat>> getChatsForClub(@PathVariable Long clubId) {
    //     try {
    //         List<Chat> chats = clubService.getChatsForClub(clubId);
    //         return new ResponseEntity<>(chats, HttpStatus.OK);
    //     } catch (RuntimeException e) {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }
}
