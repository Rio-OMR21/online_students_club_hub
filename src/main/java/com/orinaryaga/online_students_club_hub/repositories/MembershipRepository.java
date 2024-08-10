package com.orinaryaga.online_students_club_hub.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orinaryaga.online_students_club_hub.models.Club;
import com.orinaryaga.online_students_club_hub.models.Membership;
import com.orinaryaga.online_students_club_hub.models.User;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    boolean existsByUserAndClub(User user, Club club);
    // Add custom query methods if needed

    Optional<Membership> findByUserAndClub(User user, Club club);

    List<Membership> findByUser(User user);

    List<Membership> findByClub(Club club);
    
}