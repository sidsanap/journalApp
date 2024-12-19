package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {


    private  static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private  UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry,String userName){
        try {
            User user =userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
           //user.setUserName(null);
            userService.saveEntry(user);
        }catch(Exception e){
            log.error("Exception ",e);
        }
    }

    public void saveEntry (JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public Optional<JournalEntry> findById(ObjectId myId){
        return journalEntryRepository.findById(myId);

    }

    @Transactional
    public boolean deleteById(ObjectId myId, String userName){
        boolean removed=false;
       try{
           User user =userService.findByUserName(userName);
           removed =user.getJournalEntries().removeIf(x->x.getId().equals(myId));
           if(removed){
               userService.saveEntry(user);
               journalEntryRepository.deleteById(myId);
           }
       }catch (Exception e){
           throw new RuntimeException("Error occured while delteing journal entry",e);
       }
       return removed;
    }


    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }
}
