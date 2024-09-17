package onetoone.GPACalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gpa")
public class GPARecordController {

    private final GPARecordService gpaRecordService;

    @Autowired
    public GPARecordController(GPARecordService gpaRecordService) {
        this.gpaRecordService = gpaRecordService;
    }

    @GetMapping("/calculate/{userId}")
    public ResponseEntity<?> calculateGPAForUser(@PathVariable Long userId) {
        try {
            double gpa = gpaRecordService.calculateGPA(userId);
            String gradeLetter = gpaRecordService.getGradeLetter(gpa);
            return ResponseEntity.ok(new GPAAndGrade(gpa, gradeLetter));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error calculating GPA: " + e.getMessage());
        }
    }

    @GetMapping("/grades/{userId}")
    public ResponseEntity<?> getCourseGradesForUser(@PathVariable Long userId) {
        try {
            List<GPARecordDTO> courseGrades = gpaRecordService.getCourseGradesForUser(userId);
            return ResponseEntity.ok(courseGrades);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving course grades: " + e.getMessage());
        }
    }



    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addGPARecordByUserId(@PathVariable Long userId, @RequestBody GPARecord gpaRecord) {
        try {
            GPARecordDTO newRecordDTO = gpaRecordService.addGPARecordByUserId(userId, gpaRecord);
            return ResponseEntity.ok(newRecordDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding GPA record: " + e.getMessage());
        }
    }



    @PutMapping("/update/{recordId}")
    public ResponseEntity<?> updateGPARecordById(@PathVariable Long recordId, @RequestBody GPARecord updatedGpaRecord) {
        try {
            GPARecord updatedRecord = gpaRecordService.updateGPARecordById(recordId, updatedGpaRecord);
            return ResponseEntity.ok(updatedRecord);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating GPA record: " + e.getMessage());
        }
    }



    @DeleteMapping("/delete/{recordId}")
    public ResponseEntity<?> deleteGPARecord(@PathVariable Long recordId) {
        try {
            gpaRecordService.deleteGPARecord(recordId);
            return ResponseEntity.ok().body("Record deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting GPA record: " + e.getMessage());
        }
    }

    private static class GPAAndGrade {
        private final double gpa;
        private final String gradeLetter;

        public GPAAndGrade(double gpa, String gradeLetter) {
            this.gpa = gpa;
            this.gradeLetter = gradeLetter;
        }

        // Getters for the fields, needed for serialization
        public double getGpa() {
            return gpa;
        }

        public String getGradeLetter() {
            return gradeLetter;
        }
    }
}
