package com.demo.journalapp.service;

import com.demo.journalapp.entity.JournalEntry;
import com.demo.journalapp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalEntryService {

    private final JournalEntryRepo journalEntryRepo;

    public JournalEntryService(JournalEntryRepo journalEntryRepo) {
        this.journalEntryRepo = journalEntryRepo;
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepo.findAll();
    }
    public JournalEntry getJournalEntryById(ObjectId id) {
        return journalEntryRepo.findById(id).orElse(null);
    }

    public boolean deleteJournalEntryById(ObjectId id) {
        journalEntryRepo.deleteById(id);
        return true;
    }

    public JournalEntry updateJournalEntry(ObjectId id,JournalEntry journalEntry) {
        JournalEntry oldJournalEntry = journalEntryRepo.findById(id).orElse(null);
        if(oldJournalEntry != null) {
            oldJournalEntry.setTitle(journalEntry.getTitle()!=null && !journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : oldJournalEntry.getTitle());
            oldJournalEntry.setContent(journalEntry.getContent()!=null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : oldJournalEntry.getContent());
        }
        return journalEntryRepo.save(oldJournalEntry);
    }

}
