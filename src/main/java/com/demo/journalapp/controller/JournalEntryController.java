package com.demo.journalapp.controller;

import com.demo.journalapp.entity.JournalEntry;
import com.demo.journalapp.entity.User;
import com.demo.journalapp.service.JournalEntryService;
import com.demo.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;
    private final UserService userService;

    public JournalEntryController(JournalEntryService journalEntryService, UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }

    @GetMapping("/list-journal/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.getUserByUserName(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries();
       return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    @PostMapping("{userName}")
    public ResponseEntity<HttpStatus> addEntry(@RequestBody JournalEntry entry, @PathVariable String userName) {
        try{
            journalEntryService.saveEntryForUser(entry,userName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable ObjectId id) {
        JournalEntry journalEntry =  journalEntryService.getJournalEntryById(id);
        return new ResponseEntity<>(journalEntry, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userName}/{id}")
    public boolean deleteEntry(@PathVariable ObjectId id,@PathVariable String userName) {
        return journalEntryService.deleteJournalEntryById(id,userName);
    }

    @PutMapping("/update/{userName}/{id}")
    public JournalEntry updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry entry, @PathVariable String userName) {
        return journalEntryService.updateJournalEntry(id, entry,userName);
    }
}
