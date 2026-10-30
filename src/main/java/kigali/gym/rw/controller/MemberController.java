package kigali.gym.rw.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import kigali.gym.rw.domain.Member;
import kigali.gym.rw.service.MemberService;
import kigali.gym.rw.service.TrainerService;

@RestController
@RequestMapping(value = "/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TrainerService trainerService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveMember(@RequestBody Member member) {
        String message = memberService.saveMember(member);
        if (message.equals("Member saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllMembers() {
        return new ResponseEntity<>(memberService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable UUID id) {
        Optional<Member> member = memberService.findById(id);
        if (member.isEmpty()) {
            return new ResponseEntity<>("The member with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(member.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateMember(@PathVariable UUID id,@RequestBody Member member) {
        String message = memberService.updateMember(id,member);
        if (message.equals("Member updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable UUID id) {
        String message = memberService.deleteMember(id);
        if (message.equals("Member deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/by-last-name")
    public ResponseEntity<?> findByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(memberService.findByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping(value = "/of-trainer/{trainerId}")
    public ResponseEntity<?> findMembersOfTrainer(@PathVariable UUID trainerId) {
        if (trainerService.findById(trainerId).isEmpty()) {
            return new ResponseEntity<>("The trainer with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(memberService.findMembersOfTrainer(trainerId), HttpStatus.OK);
    }
}