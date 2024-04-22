package com.demo.journalapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true) // Writing this will not create index you have to write this as well in app.prop file
    //spring.data.mongodb.auto-index-creation=true
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @DBRef //Will create a foreign key kind of in mongo db collection
    private List<JournalEntry> journalEntries = new ArrayList<>();

}
