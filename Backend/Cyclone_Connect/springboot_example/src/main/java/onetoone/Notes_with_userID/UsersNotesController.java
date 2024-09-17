package onetoone.Notes_with_userID;

import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users/{userId}/notes")
public class UsersNotesController {

    @Autowired
    private UserNotesRepository notesRepository;

    @Autowired
    private UserRepository userRepository;


    private Notes_Entity convertToEntity(NotesDTO notesDTO) {
        Notes_Entity noteEntity = new Notes_Entity();
        noteEntity.setTitle(notesDTO.getTitle());
        noteEntity.setContent(notesDTO.getContent());
        // Set other properties if there are any
        return noteEntity;
    }

    @GetMapping
    public ResponseEntity<List<Notes_Entity>> getAllNotes(@PathVariable("userId") Long userId) {
        List<Notes_Entity> notes = notesRepository.findAll(); // Adjust to filter by userId if necessary
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notes_Entity> getNoteById(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
        Optional<Notes_Entity> note = notesRepository.findById(id);
        if (note.isPresent()) {
            return new ResponseEntity<>(note.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createNote(@PathVariable("userId") Long userId, @RequestBody NotesDTO notesDTO) {
        return userRepository.findById(userId).map(user -> {
            Notes_Entity note = convertToEntity(notesDTO);
            note.setUser(user);
            Notes_Entity savedNote = notesRepository.save(note);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Note created successfully");
            response.put("noteId", savedNote.getId());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }).orElseGet(() -> {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "User not found");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notes_Entity> updateNote(@PathVariable("userId") Long userId, @PathVariable("id") Long id, @RequestBody NotesDTO notesDTO) {
        return notesRepository.findById(id).map(note -> {
            note.setTitle(notesDTO.getTitle());
            note.setContent(notesDTO.getContent());
            Notes_Entity savedNote = notesRepository.save(note);
            return new ResponseEntity<>(savedNote, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteNote(@PathVariable("userId") Long userId, @PathVariable("id") Long id) {
        Optional<Notes_Entity> note = notesRepository.findById(id);
        if (note.isPresent()) {
            notesRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Note deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Note not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }

}
