package com.demo.journalapp.controller;

import com.demo.journalapp.entity.JournalEntry;
import com.demo.journalapp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private JournalEntryService journalEntryService;

    public JournalEntryController(JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;
    }

    @GetMapping("list-journal")
    public ResponseEntity<List<JournalEntry>> getJournalEntries() {
       List<JournalEntry> journalEntries = journalEntryService.getAllJournalEntries();
       return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addEntry(@RequestBody JournalEntry entry) {
        journalEntryService.saveEntry(entry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable ObjectId id) {
        JournalEntry journalEntry =  journalEntryService.getJournalEntryById(id);
        return new ResponseEntity<>(journalEntry, HttpStatus.OK);
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
