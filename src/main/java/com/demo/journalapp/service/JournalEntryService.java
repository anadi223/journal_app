package com.demo.journalapp.service;

import com.demo.journalapp.entity.JournalEntry;
import com.demo.journalapp.entity.User;
import com.demo.journalapp.repository.JournalEntryRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class JournalEntryService {

    private final JournalEntryRepo journalEntryRepo;
    private final UserService userService;

    public JournalEntryService(JournalEntryRepo journalEntryRepo, UserService userService) {
        this.journalEntryRepo = journalEntryRepo;
        this.userService = userService;
    }

    @Transactional
    public void saveEntryForUser(JournalEntry journalEntry, String userName) {
        try{
            log.info("Saving user entry");
            User user = userService.getUserByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }catch (Exception e){
            log.error("Error occurred while saving entry");
            throw new RuntimeException("An error occurred while saving journal entry" +e.getMessage());
        }


    }

    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepo.findAll();
    }
    public JournalEntry getJournalEntryById(ObjectId id) {
        return journalEntryRepo.findById(id).orElse(null);
    }

    @Transactional
    public boolean deleteJournalEntryById(ObjectId id, String userName) {
        try{
            User user = userService.getUserByUserName(userName);
            boolean removed = user.getJournalEntries().removeIf(x->x.getId().equals(id)); //remove a journal entry if the id of the journal entry matches with any id present in the user journal entries list
            if(removed){
                userService.saveUser(user);
                journalEntryRepo.deleteById(id);
            }
        }catch (Exception e){
            throw new RuntimeException("An error occurred while deleting journal entry" +e.getMessage());
        }
        return true;
    }

    public ResponseEntity<JournalEntry> updateJournalEntry(ObjectId id, JournalEntry journalEntry, String userName) {
        JournalEntry oldJournalEntry = journalEntryRepo.findById(id).orElse(null);
        if(oldJournalEntry != null) {
            oldJournalEntry.setTitle(!journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : oldJournalEntry.getTitle());
            oldJournalEntry.setContent(!journalEntry.getContent().isEmpty() ? journalEntry.getContent() : oldJournalEntry.getContent());
            return new ResponseEntity<>(journalEntryRepo.save(oldJournalEntry), HttpStatus.ACCEPTED);
        }
        return ResponseEntity.notFound().build();
    }

}
