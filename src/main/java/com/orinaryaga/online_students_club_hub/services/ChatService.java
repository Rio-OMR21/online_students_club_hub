package com.orinaryaga.online_students_club_hub.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orinaryaga.online_students_club_hub.models.Chat;
import com.orinaryaga.online_students_club_hub.models.Club;
import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.repositories.ChatRepository;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ClubService clubService;

    @Autowired
    private UserService userService;

    @Autowired
    private MembershipService membershipService;

    // Post a new message in the club chat
    // Post a new message in the club chat
    public Chat postMessage(Long clubId, Long userId, String message) {
        Club club = clubService.getClubById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        User user = userService.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        membershipService.findByUserAndClub(userId, clubId)
                .orElseThrow(() -> new RuntimeException("User is not a member of the club"));

        Chat chat = new Chat();
        chat.setClub(club);
        chat.setUser(user);
        chat.setMessage(message);
        chat.setSentTime(new Timestamp(System.currentTimeMillis()));

        return chatRepository.save(chat);
    }

    // Get all messages for a club, sorted by sentTime
    public List<Chat> getMessagesForClub(Long clubId) {
        Club club = clubService.getClubById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        return chatRepository.findByClubOrderBySentTimeAsc(club);
    }

    // Get all messages sent by a user in a club
    public List<Chat> getMessagesByUserInClub(Long clubId, Long userId) {
        Club club = clubService.getClubById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        User user = userService.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return chatRepository.findByClubAndUserOrderBySentTimeAsc(club, user);
    }

    // Delete a message by ID
    public void deleteMessage(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    // Get all messages for a club within a time range
    public List<Chat> getMessagesForClubWithinTimeRange(Long clubId, Timestamp startTime, Timestamp endTime) {
        Club club = clubService.getClubById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        return chatRepository.findByClubAndSentTimeBetweenOrderBySentTimeAsc(club, startTime, endTime);
    }
}
