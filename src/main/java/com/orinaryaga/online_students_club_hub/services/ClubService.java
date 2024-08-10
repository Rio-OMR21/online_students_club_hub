package com.orinaryaga.online_students_club_hub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orinaryaga.online_students_club_hub.models.Chat;
import com.orinaryaga.online_students_club_hub.models.Club;
import com.orinaryaga.online_students_club_hub.models.Membership;
import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.repositories.ChatRepository;
import com.orinaryaga.online_students_club_hub.repositories.ClubRepository;
import com.orinaryaga.online_students_club_hub.repositories.MembershipRepository;
import com.orinaryaga.online_students_club_hub.repositories.UserRepository;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    // Register a new club
    public Club registerClub(Club club) {
        return clubRepository.save(club);
    }

    // Update existing club
    public Club updateClub(Club club) {
        // Ensure the club exists
        Club existingClub = clubRepository.findById(club.getClubId())
                .orElseThrow(() -> new RuntimeException("Club not found"));

        // Update the club details
        existingClub.clubName(club.getClubName());
        existingClub.setDescription(club.getDescription());
        existingClub.setMemberships(club.getMemberships());
        return clubRepository.save(existingClub);
    }

    // Get club by ID
    public Optional<Club> getClubById(Long clubId) {
        return clubRepository.findById(clubId);
    }

    // Get all clubs
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    // Delete club by ID
    public void deleteClub(Long clubId) {
        clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        // Delete associated chats
        List<Chat> chats = chatRepository.findByClubClubId(clubId);
        chatRepository.deleteAll(chats);

        // Delete the club
        clubRepository.deleteById(clubId);
    }

    // Add a member to a club
   // Add a member to a club
    // Add a member to a club
    public Club addMemberToClub(Long clubId, Long userId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new Membership
        Membership membership = new Membership();
        membership.setUser(user);
        membership.setClub(club);

        // Save the membership
        membershipRepository.save(membership);

        // Add membership to the club's memberships
        club.getMemberships().add(membership);
        return clubRepository.save(club);
    }

    // Remove a member from a club
    @SuppressWarnings("unlikely-arg-type")
    public Club removeMemberFromClub(Long clubId, Long userId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        club.getMemberships().remove(user);
        return clubRepository.save(club);
    }

    // Add a chat to a club
    public Chat addChatToClub(Long clubId, Chat chat) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        chat.setClub(club);
        return chatRepository.save(chat);
    }

    // Get all chats for a club
    public List<Chat> getChatsForClub(Long clubId) {
        clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        return chatRepository.findByClubClubId(clubId);
    }
}

