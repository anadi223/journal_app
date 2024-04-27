package com.demo.journalapp.controller;

import com.demo.journalapp.entity.JournalEntry;
import com.demo.journalapp.entity.User;
import com.demo.journalapp.service.JournalEntryService;
import com.demo.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;
    private final UserService userService;

    //Helper method to getUsername to avoid writing in every request
    public String getUserNameFromSecurityContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public JournalEntryController(JournalEntryService journalEntryService, UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }

    @GetMapping("/list-journal")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        String userName = getUserNameFromSecurityContext();
        User user = userService.getUserByUserName(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries();
       return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    @PostMapping("/create-journal")
    public ResponseEntity<HttpStatus> addEntry(@RequestBody JournalEntry entry) {
        try{
            String userName = getUserNameFromSecurityContext();
            journalEntryService.saveEntryForUser(entry,userName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable ObjectId id) {
        String userName = getUserNameFromSecurityContext();
        User user = userService.getUserByUserName(userName);
        List<JournalEntry> journalsForASpecificUser = user.getJournalEntries().stream().
                filter(x -> x.getId().equals(id)).toList();
        if(!journalsForASpecificUser.isEmpty()){
            JournalEntry journalEntry =  journalEntryService.getJournalEntryById(id);
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEntry(@PathVariable ObjectId id) {
        String userName = getUserNameFromSecurityContext();

        boolean res =  journalEntryService.deleteJournalEntryById(id,userName);
        if(res){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry entry) {
        String userName = getUserNameFromSecurityContext();
        return journalEntryService.updateJournalEntry(id, entry,userName);
    }
}
