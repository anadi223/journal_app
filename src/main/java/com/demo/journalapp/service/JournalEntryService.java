package com.demo.journalapp.service;

import com.demo.journalapp.entity.JournalEntry;
import com.demo.journalapp.entity.User;
import com.demo.journalapp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
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
            User user = userService.getUserByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            user.setUserName(null);
            userService.saveUser(user);
        }catch (Exception e){
            throw new RuntimeException("An error occurred while saving journal entry" +e.getMessage());
        }


    }

    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepo.findAll();
    }
    public JournalEntry getJournalEntryById(ObjectId id) {
        return journalEntryRepo.findById(id).orElse(null);
    }

    public boolean deleteJournalEntryById(ObjectId id, String userName) {
        User user = userService.getUserByUserName(userName);
        user.getJournalEntries().removeIf(x->x.getId().equals(id)); //remove a journal entry if the id of the journal entry matches with any id present in the user journal entries list
        userService.saveUser(user);
        journalEntryRepo.deleteById(id);
        return true;
    }

    public JournalEntry updateJournalEntry(ObjectId id, JournalEntry journalEntry, String userName) {
        JournalEntry oldJournalEntry = journalEntryRepo.findById(id).orElse(null);
        if(oldJournalEntry != null) {
            oldJournalEntry.setTitle(!journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : oldJournalEntry.getTitle());
            oldJournalEntry.setContent(!journalEntry.getContent().isEmpty() ? journalEntry.getContent() : oldJournalEntry.getContent());
        }
        return journalEntryRepo.save(oldJournalEntry);
    }

}
