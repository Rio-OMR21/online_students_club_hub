package com.orinaryaga.online_students_club_hub.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orinaryaga.online_students_club_hub.models.Challenge;
import com.orinaryaga.online_students_club_hub.models.Event;
import com.orinaryaga.online_students_club_hub.repositories.ChallengeRepository;
import com.orinaryaga.online_students_club_hub.repositories.ClubRepository;
import com.orinaryaga.online_students_club_hub.repositories.EventRepository;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private EventRepository eventRepository;

    // Create a new challenge and associate it with a club and event
    public Challenge createChallenge(Challenge challenge, Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        challenge.setEvent(event);

        return challengeRepository.save(challenge);
    }

    // Other methods remain unchanged...
}



