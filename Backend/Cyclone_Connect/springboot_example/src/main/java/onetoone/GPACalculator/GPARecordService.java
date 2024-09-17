package onetoone.GPACalculator;

import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GPARecordService {

    private final GPARecordRepository gpaRecordRepository;
    private final UserRepository userRepository;

    @Autowired
    public GPARecordService(GPARecordRepository gpaRecordRepository, UserRepository userRepository) {
        this.gpaRecordRepository = gpaRecordRepository;
        this.userRepository = userRepository;
    }

    public List<GPARecordDTO> getCourseGradesForUser(Long userId) {
        List<GPARecord> records = gpaRecordRepository.findByUser_UserId(userId);
        if (records.isEmpty()) {
            throw new IllegalArgumentException("No course grades found for user with ID: " + userId);
        }

        // Convert GPARecord entities to DTOs
        List<GPARecordDTO> courseGrades = new ArrayList<>();
        for (GPARecord record : records) {
            GPARecordDTO dto = GPARecordDTO.fromEntity(record);
            // Set the userId in the DTO
            dto.setUserId(userId);
            courseGrades.add(dto);
        }

        return courseGrades;
    }

//    public GPARecord updateGPARecordByUserId(Long userId, GPARecord updatedGpaRecord) {
//        // Check if the user exists
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
//
//        // Set the user for the updated GPA record
//        updatedGpaRecord.setUser(user);
//
//        // Update the GPA record
//        return gpaRecordRepository.save(updatedGpaRecord);
//    }




//
//    public List<GPARecordDTO> getCourseGradesForUser(Long userId) {
//        List<GPARecord> records = gpaRecordRepository.findByUser_UserId(userId);
//        if (records.isEmpty()) {
//            throw new IllegalArgumentException("No course grades found for user with ID: " + userId);
//        }
//
//        // Convert GPARecord entities to DTOs
//        List<GPARecordDTO> courseGrades = new ArrayList<>();
//        for (GPARecord record : records) {
//            GPARecordDTO dto = GPARecordDTO.fromEntity(record);
//            courseGrades.add(dto);
//        }
//
//        return courseGrades;
//    }
//
//
//    public List<GPARecordDTO> getAllGPARecords() {
//        List<GPARecord> records = gpaRecordRepository.findAll();
//        return records.stream().map(GPARecordDTO::fromEntity).collect(Collectors.toList());
//    }

    public GPARecordDTO addGPARecordByUserId(Long userId, GPARecord gpaRecord) {
        // Fetch the user by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Set the user for the GPARecord
        gpaRecord.setUser(user);

        // Save the GPARecord
        GPARecord savedRecord = gpaRecordRepository.save(gpaRecord);

        // Convert the saved entity to a DTO
        return GPARecordDTO.fromEntity(savedRecord);
    }


//    public GPARecordDTO addGPARecord(GPARecord gpaRecord) {
//        validateUserForGPARecord(gpaRecord);
//        GPARecord savedRecord = gpaRecordRepository.save(gpaRecord);
//        return GPARecordDTO.fromEntity(savedRecord);
//    }

//    public GPARecordDTO updateGPARecord(GPARecord gpaRecord) {
//        validateUserForGPARecord(gpaRecord);
//        if (!gpaRecordRepository.existsById(gpaRecord.getId())) {
//            throw new IllegalArgumentException("GPA record with ID " + gpaRecord.getId() + " not found.");
//        }
//        GPARecord updatedRecord = gpaRecordRepository.save(gpaRecord);
//        return GPARecordDTO.fromEntity(updatedRecord);
//    }

    public GPARecord updateGPARecordById(Long recordId, GPARecord updatedGpaRecord) {
        // Retrieve the existing record
        GPARecord existingRecord = gpaRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("Record not found with id: " + recordId));

        // Update the existing record with new details
        existingRecord.setSubjectName(updatedGpaRecord.getSubjectName());
        existingRecord.setCredit(updatedGpaRecord.getCredit());
        existingRecord.setGrade(updatedGpaRecord.getGrade());

        // Save the updated record
        return gpaRecordRepository.save(existingRecord);
    }


    public void deleteGPARecord(Long recordId) {
        gpaRecordRepository.deleteById(recordId);
    }

    public double calculateGPA(Long userId) {
        List<GPARecord> records = gpaRecordRepository.findByUser_UserId(userId);
        if (records.isEmpty()) {
            return 0.0; // No records found for the user
        }

        double totalPoints = 0.0;
        int totalCredits = 0;
        for (GPARecord record : records) {
            double gradeValue = getNumericalGrade(record.getGrade());
            totalPoints += gradeValue * record.getCredit();
            totalCredits += record.getCredit();
        }

        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }

    public String getGradeLetter(double gpa) {
        if (gpa >= 4.0) {
            return "A";
        } else if (gpa >= 3.7) {
            return "A-";
        } else if (gpa >= 3.3) {
            return "B+";
        } else if (gpa >= 3.0) {
            return "B";
        } else if (gpa >= 2.7) {
            return "B-";
        } else if (gpa >= 2.3) {
            return "C+";
        } else if (gpa >= 2.0) {
            return "C";
        } else if (gpa >= 1.7) {
            return "C-";
        } else if (gpa >= 1.0) {
            return "D";
        } else {
            return "F";
        }
    }

    private double getNumericalGrade(String grade) {
        switch (grade) {
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "C-": return 1.7;
            case "D": return 1.0;
            default: return 0.0; // Assuming F grade has numerical value 0.0
        }
    }

    private void validateUserForGPARecord(GPARecord gpaRecord) {
        if (gpaRecord.getUser() == null || gpaRecord.getUser().getUserId() == null) {
            throw new IllegalArgumentException("User must be provided with a valid user ID");
        }
        User user = userRepository.findById(gpaRecord.getUser().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + gpaRecord.getUser().getUserId()));
        gpaRecord.setUser(user);
    }
}
