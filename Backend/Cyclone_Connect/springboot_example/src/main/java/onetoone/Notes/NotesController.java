package onetoone.Notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    @Autowired
    private NotesRepository notesRepository;

    @GetMapping
    public ResponseEntity<List<Notes_Entity>> getAllNotes() {
        List<Notes_Entity> notes = notesRepository.findAll();
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notes_Entity> getNoteById(@PathVariable("id") Long id) {
        Optional<Notes_Entity> note = notesRepository.findById(id);
        if (note.isPresent()) {
            return new ResponseEntity<>(note.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Notes_Entity> createNote(@RequestBody Notes_Entity note) {
        Notes_Entity savedNote = notesRepository.save(note);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notes_Entity> updateNote(@PathVariable("id") Long id, @RequestBody Notes_Entity updatedNote) {
        return notesRepository.findById(id)
                .map(note -> {
                    note.setTitle(updatedNote.getTitle());
                    note.setContent(updatedNote.getContent());
                    Notes_Entity savedNote = notesRepository.save(note);
                    return new ResponseEntity<>(savedNote, HttpStatus.OK);
                }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") Long id) {
        if (notesRepository.existsById(id)) {
            notesRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
