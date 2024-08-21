package com.orinaryaga.online_students_club_hub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orinaryaga.online_students_club_hub.models.Club;
import com.orinaryaga.online_students_club_hub.models.Membership;
import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.repositories.ClubRepository;
import com.orinaryaga.online_students_club_hub.repositories.MembershipRepository;
import com.orinaryaga.online_students_club_hub.repositories.UserRepository;

@Service
public class MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClubRepository clubRepository;

    // Add a member (student or mentor) to a club
    public Membership addMemberToClub(Long clubId, Long userId, boolean isMentor) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user is already a member of the club
        if (membershipRepository.existsByUserAndClub(user, club)) {
            throw new RuntimeException("User is already a member of the club");
        }

        // Validate user type (student or mentor) based on isMentor flag
        if (isMentor) {
            if (user.getMentor() == null) {
                throw new RuntimeException("User is not a mentor");
            }
        } else {
            if (user.getStudent() == null) {
                throw new RuntimeException("User is not a student");
            }
        }

        // Create a new Membership
        Membership membership = new Membership();
        membership.setUser(user);
        membership.setClub(club);

        // Save the membership
        membershipRepository.save(membership);

        // Update the club's memberships
        club.getMemberships().add(membership);
        clubRepository.save(club);

        return membership;
    }

    // Get all memberships for a club
    public List<Membership> getAllMembershipsForClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        return membershipRepository.findByClub(club);
    }

    // Get all memberships for a user
    public List<Membership> getAllMembershipsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return membershipRepository.findByUser(user);
    }

    // Check if a user is a member of a club
    public boolean isUserMemberOfClub(Long userId, Long clubId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        return membershipRepository.existsByUserAndClub(user, club);
    }

    // Get membership by ID
    public Optional<Membership> getMembershipById(Long membershipId) {
        return membershipRepository.findById(membershipId);
    }

    // Find membership by user and club
    public Optional<Membership> findByUserAndClub(Long userId, Long clubId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        return membershipRepository.findByUserAndClub(user, club);
    }
}
