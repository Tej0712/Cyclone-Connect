package onetoone;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import onetoone.Notes_with_userID.NotesDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.IOException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SystemTest {

    @Autowired
    private MockMvc mockMvc;

    // Utility method to convert DTO to JSON
    // Utility method to convert DTO to JSON
    private String convertToJson(Object object) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    // Utility method to extract note ID from response
    private Long extractNoteIdFromResponse(String response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response);
        return root.path("noteId").asLong(); // Update the "noteId" path according to your JSON response structure
    }

    //Test1
    @Test
    public void testCreateNoteWithInvalidUser() throws Exception {
        // Assuming that the DTO and the User entity are correctly set up.
        NotesDTO noteDto = new NotesDTO();
        noteDto.setTitle("Sample Title");
        noteDto.setContent("Sample content");

        // Convert DTO to JSON
        String noteDtoJson = convertToJson(noteDto);

        mockMvc.perform(post("/api/users/{userId}/notes", 9999) // 9999 is a non-existent user ID
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteDtoJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("User not found"));
    }

    //Test2
    @Test
    public void testUpdateNonExistentNote() throws Exception {
        NotesDTO notesDTO = new NotesDTO();
        notesDTO.setTitle("Updated Title");
        notesDTO.setContent("Updated content");

        mockMvc.perform(put("/api/users/{userId}/notes/{id}", 1L, 9999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(notesDTO)))
                .andExpect(status().isNotFound());
    }


    //Test3
    @Test
    public void testDeleteNoteAndConfirmDeletion() throws Exception {
        // Mock the note creation and then delete it
        NotesDTO newNote = new NotesDTO();
        newNote.setTitle("Note to be deleted");
        newNote.setContent("Content of note to be deleted");

        MvcResult result = mockMvc.perform(post("/api/users/{userId}/notes", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(newNote)))
                .andExpect(status().isCreated())
                .andReturn();

        // Extract the ID from the response
        String responseString = result.getResponse().getContentAsString();
        Long noteId = extractNoteIdFromResponse(responseString);

        // Now delete the note
        mockMvc.perform(delete("/api/users/{userId}/notes/{id}", 1L, noteId))
                .andExpect(status().isOk());

        // Attempt to fetch the deleted note
        mockMvc.perform(get("/api/users/{userId}/notes/{id}", 1L, noteId))
                .andExpect(status().isNotFound());
    }

    //Test4

    @Test
    public void testPersistAndRetrieveNotesInOrder() throws Exception {
        Long userId = 1L;  // User ID for the test

        // Fetch initial count of notes for the user
        MvcResult initialResult = mockMvc.perform(get("/api/users/{userId}/notes", userId))
                .andExpect(status().isOk())
                .andReturn();
        String initialResponseContent = initialResult.getResponse().getContentAsString();
        JsonNode initialNotes = new ObjectMapper().readTree(initialResponseContent);
        int initialCount = initialNotes.size();

        // Data for new notes
        String[] titles = {"First Note", "Second Note", "Third Note"};
        String[] contents = {"Content of first note", "Content of second note", "Content of third note"};

        // Post each note and assert creation
        for (int i = 0; i < titles.length; i++) {
            NotesDTO note = new NotesDTO();
            note.setTitle(titles[i]);
            note.setContent(contents[i]);

            mockMvc.perform(post("/api/users/{userId}/notes", userId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertToJson(note)))
                    .andExpect(status().isCreated());
        }

        // Fetch all notes after adding new ones
        MvcResult finalResult = mockMvc.perform(get("/api/users/{userId}/notes", userId))
                .andExpect(status().isOk())
                .andReturn();
        String finalResponseContent = finalResult.getResponse().getContentAsString();
        JsonNode finalNotes = new ObjectMapper().readTree(finalResponseContent);

        // Verify the count of notes increased by the number of new notes
        assertThat(finalNotes.size(), is(initialCount + titles.length));

        // Check that the new notes have been appended to the end in the correct order
        for (int i = 0; i < titles.length; i++) {
            JsonNode note = finalNotes.get(initialCount + i);
            assertThat(note.get("title").asText(), is(titles[i]));
            assertThat(note.get("content").asText(), is(contents[i]));
        }
    }






    //Test5
    @Test
    public void testUpdateNoteForSpecificUser() throws Exception {
        // Step 1: Create a new note
        NotesDTO newNote = new NotesDTO();
        newNote.setTitle("Original Title");
        newNote.setContent("Original Content");

        String newNoteJson = convertToJson(newNote);

        MvcResult createResult = mockMvc.perform(post("/api/users/{userId}/notes", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newNoteJson))
                .andExpect(status().isCreated())
                .andReturn();

        // Step 2: Extract the ID of the newly created note
        String createResponseString = createResult.getResponse().getContentAsString();
        Long noteId = extractNoteIdFromResponse(createResponseString);

        // Step 3: Update the note
        NotesDTO updatedNote = new NotesDTO();
        updatedNote.setTitle("Updated Title");
        updatedNote.setContent("Updated Content");

        String updatedNoteJson = convertToJson(updatedNote);

        mockMvc.perform(put("/api/users/{userId}/notes/{id}", 1L, noteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedNoteJson))
                .andExpect(status().isOk());

        // Step 4: Fetch the updated note and verify the changes
        mockMvc.perform(get("/api/users/{userId}/notes/{id}", 1L, noteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.content", is("Updated Content")));
    }


    //Test6
    @Test
    public void testConcurrentNoteUpdates() throws Exception {
        // Create a note first
        NotesDTO originalNote = new NotesDTO();
        originalNote.setTitle("Concurrent Title");
        originalNote.setContent("Concurrent Content");

        String noteJson = convertToJson(originalNote);
        MvcResult result = mockMvc.perform(post("/api/users/{userId}/notes", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteJson))
                .andExpect(status().isCreated())
                .andReturn();

        Long noteId = extractNoteIdFromResponse(result.getResponse().getContentAsString());

        // Create threads to update the same note concurrently
        Runnable updateTask = () -> {
            NotesDTO updatedNote = new NotesDTO();
            updatedNote.setTitle("Updated Concurrently");
            updatedNote.setContent("Updated Content Concurrently");
            try {
                mockMvc.perform(put("/api/users/{userId}/notes/{id}", 1L, noteId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(updatedNote)))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread thread1 = new Thread(updateTask);
        Thread thread2 = new Thread(updateTask);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Verify final state of the note
        mockMvc.perform(get("/api/users/{userId}/notes/{id}", 1L, noteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Concurrently")))
                .andExpect(jsonPath("$.content", is("Updated Content Concurrently")));
    }


    //Test7
    @Test
    public void testCreateNoteWithInvalidData() throws Exception {
        // Prepare invalid data where userId is null
        NotesDTO noteDto = new NotesDTO();
        noteDto.setId(null);
        noteDto.setUserId(null);

        // Convert DTO to JSON
        String noteDtoJson = convertToJson(noteDto);

        mockMvc.perform(post("/api/users/{userId}/notes", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteDtoJson))
                .andExpect(status().isCreated()); // Expecting a successful creation response
    }



    //Test8
    @Test
    public void testRetrieveNoteAfterUpdate() throws Exception {
        // First, create a new note
        NotesDTO newNote = new NotesDTO();
        newNote.setTitle("Initial Title");
        newNote.setContent("Initial Content");

        MvcResult createResult = mockMvc.perform(post("/api/users/{userId}/notes", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(newNote)))
                .andExpect(status().isCreated())
                .andReturn();

        Long noteId = extractNoteIdFromResponse(createResult.getResponse().getContentAsString());

        // Update the note
        NotesDTO updatedNote = new NotesDTO();
        updatedNote.setTitle("Updated Title");
        updatedNote.setContent("Updated Content");

        mockMvc.perform(put("/api/users/{userId}/notes/{id}", 1L, noteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(updatedNote)))
                .andExpect(status().isOk());

        // Retrieve the updated note and verify the contents
        mockMvc.perform(get("/api/users/{userId}/notes/{id}", 1L, noteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.content", is("Updated Content")));
    }

    //Test9
    @Test
    public void testRapidSequenceOperations() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper(); // Local instantiation

        // Step 1: Create a note
        NotesDTO newNote = new NotesDTO();
        newNote.setTitle("Test Note");
        newNote.setContent("Initial content of the test note");

        String noteJson = convertToJson(newNote);
        MvcResult createResult = mockMvc.perform(post("/api/users/{userId}/notes", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteJson))
                .andExpect(status().isCreated())
                .andReturn();

        Long noteId = objectMapper.readTree(createResult.getResponse().getContentAsString()).path("noteId").asLong();

        // Step 2: Update the note
        newNote.setTitle("Updated Test Note");
        newNote.setContent("Updated content of the test note");
        mockMvc.perform(put("/api/users/{userId}/notes/{id}", 1L, noteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(newNote)))
                .andExpect(status().isOk());

        // Step 3: Delete the note
        mockMvc.perform(delete("/api/users/{userId}/notes/{id}", 1L, noteId))
                .andExpect(status().isOk());

        // Step 4: Attempt to fetch the deleted note
        mockMvc.perform(get("/api/users/{userId}/notes/{id}", 1L, noteId))
                .andExpect(status().isNotFound());
    }


    //Test10


    //Test11


    //Test12





}
