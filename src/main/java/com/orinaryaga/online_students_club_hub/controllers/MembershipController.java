package com.orinaryaga.online_students_club_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orinaryaga.online_students_club_hub.models.Membership;
import com.orinaryaga.online_students_club_hub.services.MembershipService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    // Add a member to a club
    @PostMapping("/add")
    public ResponseEntity<Membership> addMemberToClub(@RequestParam Long clubId, @RequestParam Long userId) {
        try {
            Membership membership = membershipService.addMemberToClub(clubId, userId);
            return new ResponseEntity<>(membership, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get all memberships for a club
    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<Membership>> getAllMembershipsForClub(@PathVariable Long clubId) {
        try {
            List<Membership> memberships = membershipService.getAllMembershipsForClub(clubId);
            return new ResponseEntity<>(memberships, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all memberships for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Membership>> getAllMembershipsForUser(@PathVariable Long userId) {
        try {
            List<Membership> memberships = membershipService.getAllMembershipsForUser(userId);
            return new ResponseEntity<>(memberships, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Check if a user is a member of a club
    @GetMapping("/check")
    public ResponseEntity<Boolean> isUserMemberOfClub(@RequestParam Long userId, @RequestParam Long clubId) {
        try {
            boolean isMember = membershipService.isUserMemberOfClub(userId, clubId);
            return new ResponseEntity<>(isMember, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get membership by ID
    @GetMapping("/{membershipId}")
    public ResponseEntity<Membership> getMembershipById(@PathVariable Long membershipId) {
        Optional<Membership> membership = membershipService.getMembershipById(membershipId);
        return membership.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Find membership by user and club
    @GetMapping("/find")
    public ResponseEntity<Membership> findByUserAndClub(@RequestParam Long userId, @RequestParam Long clubId) {
        Optional<Membership> membership = membershipService.findByUserAndClub(userId, clubId);
        return membership.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

