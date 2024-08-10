package com.orinaryaga.online_students_club_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orinaryaga.online_students_club_hub.models.Chat;
import com.orinaryaga.online_students_club_hub.services.ChatService;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/clubs/{clubId}/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // Post a new message in the club chat
    @PostMapping("/postMessage/{userId}")
    public ResponseEntity<Chat> postMessage(
            @PathVariable Long clubId,
            @PathVariable Long userId,
            @RequestBody String message) {
        try {
            Chat chat = chatService.postMessage(clubId, userId, message);
            return new ResponseEntity<>(chat, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get all messages for a club, sorted by sentTime
    @GetMapping
    public ResponseEntity<List<Chat>> getMessagesForClub(@PathVariable Long clubId) {
        try {
            List<Chat> messages = chatService.getMessagesForClub(clubId);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all messages sent by a user in a club
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Chat>> getMessagesByUserInClub(
            @PathVariable Long clubId,
            @PathVariable Long userId) {
        try {
            List<Chat> messages = chatService.getMessagesByUserInClub(clubId, userId);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a message by ID
    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long chatId) {
        try {
            chatService.deleteMessage(chatId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all messages for a club within a time range
    @GetMapping("/timeRange")
    public ResponseEntity<List<Chat>> getMessagesForClubWithinTimeRange(
            @PathVariable Long clubId,
            @RequestParam Timestamp startTime,
            @RequestParam Timestamp endTime) {
        try {
            List<Chat> messages = chatService.getMessagesForClubWithinTimeRange(clubId, startTime, endTime);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

