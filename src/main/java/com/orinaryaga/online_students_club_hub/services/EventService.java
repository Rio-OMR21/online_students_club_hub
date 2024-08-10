package com.orinaryaga.online_students_club_hub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orinaryaga.online_students_club_hub.models.Challenge;
import com.orinaryaga.online_students_club_hub.models.Club;
import com.orinaryaga.online_students_club_hub.models.Event;
import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.repositories.ClubRepository;
import com.orinaryaga.online_students_club_hub.repositories.EventRepository;
import com.orinaryaga.online_students_club_hub.repositories.UserRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private UserService userService;

    // Create a new event
    public Event createEvent(Event event) {
        Club club = clubService.getClubById(event.getClub().getClubId())
                .orElseThrow(() -> new RuntimeException("Club not found"));

        User user = userService.getUserById(event.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        event.setClub(club);
        event.setUser(user);

        return eventRepository.save(event);
    }

    // Get event by ID (return Optional<Event>)
    public Optional<Event> getEventById(Long eventId) {
        return eventRepository.findById(eventId);
    }

    // Create a new event with an associated challenge
    public Event createEventWithChallenge(Event event, Long userId, Long clubId, Challenge challenge) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        event.setUser(user);
        event.setClub(club);

        Event savedEvent = eventRepository.save(event);

        // Create and associate the challenge with the event and club
        challengeService.createChallenge(challenge, savedEvent.getEventId());

        return savedEvent;
    }

    // Update an existing event
    public Event updateEvent(Long eventId, Event updatedEvent) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setType(updatedEvent.getType());
        event.setTitle(updatedEvent.getTitle());
        event.setDescription(updatedEvent.getDescription());
        event.setStartTime(updatedEvent.getStartTime());
        event.setEndTime(updatedEvent.getEndTime());

        return eventRepository.save(event);
    }

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Delete an event by ID
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        eventRepository.delete(event);
    }

    // Add a challenge to an event
    public Event addChallengeToEvent(Long eventId, Challenge challenge) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        challengeService.createChallenge(challenge, eventId);

        event.setChallenge(challenge);
        return eventRepository.save(event);
    }
}



