package com.demo.journalapp.controller;

import com.demo.journalapp.entity.JournalEntry;
import com.demo.journalapp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private JournalEntryService journalEntryService;

    public JournalEntryController(JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;
    }

    @GetMapping("list-journal")
    public List<JournalEntry> getJournalEntries() {
       return journalEntryService.getAllJournalEntries();
    }

    @PostMapping
    public void addEntry(@RequestBody JournalEntry entry) {
        journalEntryService.saveEntry(entry);
    }

    @GetMapping("/id/{id}")
    public JournalEntry getJournalEntry(@PathVariable ObjectId id) {
        return journalEntryService.getJournalEntryById(id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteEntry(@PathVariable ObjectId id) {
        return journalEntryService.deleteJournalEntryById(id);
    }

    @PutMapping("/update/{id}")
    public JournalEntry updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry entry) {
        return journalEntryService.updateJournalEntry(id, entry);
    }
}
