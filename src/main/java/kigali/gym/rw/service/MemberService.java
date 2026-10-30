package kigali.gym.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.gym.rw.domain.Member;
import kigali.gym.rw.repository.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepo;

    public String saveMember(Member member) {
        memberRepo.save(member);
        return "Member saved successfully";
    }

    public List<Member> findAll() {
        return memberRepo.findAll();
    }

    public Optional<Member> findById(UUID id) {
        return memberRepo.findById(id);
    }

    public String updateMember(UUID id, Member member) {
        Optional<Member> old = memberRepo.findById(id);
        if (old.isEmpty()) {
            return "The member with that id does not exist";
        }
        old.get().setFirstName(member.getFirstName());
        old.get().setLastName(member.getLastName());
        old.get().setMembershipNumber(member.getMembershipNumber());
        old.get().setPhone(member.getPhone());
        old.get().setPlan(member.getPlan());
        memberRepo.save(old.get());
        return "Member updated successfully";
    }

    public String deleteMember(UUID id) {
        if (!memberRepo.existsById(id)) {
            return "The member with that id does not exist";
        }
        memberRepo.deleteById(id);
        return "Member deleted successfully";
    }

    public List<Member> findByLastName(String lastName) {
        return memberRepo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
}