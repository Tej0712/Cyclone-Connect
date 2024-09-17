package onetoone.ReportBug;

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
@RequestMapping("/reports")
public class ReportController {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReportController(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }


    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody ReportDTO reportDTO) {
        return userRepository.findById(reportDTO.getUserId())
                .map(user -> {
                    Report report = new Report(reportDTO.getSubject(), reportDTO.getDescription(), user);
                    report = reportRepository.save(report);

                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Report created successfully");
                    response.put("reportId", report.getId());

                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                })
                .orElseGet(() -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("error", "User not found with ID: " + reportDTO.getUserId());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

//    @PostMapping
//    public ResponseEntity<?> createReport(@RequestBody ReportDTO reportDTO) {
//        return userRepository.findById(reportDTO.getUserId())
//                .map(user -> {
//                    Report report = new Report(reportDTO.getSubject(), reportDTO.getDescription(), user);
//                    report = reportRepository.save(report);
//                    return ResponseEntity.status(HttpStatus.CREATED).body("Report created successfully with ID: " + report.getId());
//                })
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + reportDTO.getUserId()));
//    }

    // Get all reports
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportRepository.findAll();

        if (reports.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reports);
        }
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if (report.isPresent()) {
            return ResponseEntity.ok(report.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report not found with ID: " + id);
        }
    }


//
//    // Update a report by ID
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateReport(@PathVariable Long id, @RequestBody ReportDTO reportDTO) {
//        return reportRepository.findById(id).map(report -> {
//            userRepository.findById(reportDTO.getUserId()).ifPresent(report::setUser);
//            report.setSubject(reportDTO.getSubject());
//            report.setDescription(reportDTO.getDescription());
//            Report updatedReport = reportRepository.save(report);
//            return ResponseEntity.ok("Report updated successfully with ID: " + updatedReport.getId());
//        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report not found with ID: " + id));
//    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(@PathVariable Long id, @RequestBody ReportDTO reportDTO) {
        return reportRepository.findById(id).map(report -> {
            // Removed the line that updates the user based on userId from ReportDTO

            report.setSubject(reportDTO.getSubject());
            report.setDescription(reportDTO.getDescription());
            Report updatedReport = reportRepository.save(report);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Report updated successfully");
            response.put("reportId", updatedReport.getId());

            return ResponseEntity.ok(response);
        }).orElseGet(() -> {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Report not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        });
    }

    // Delete a report by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable Long id) {
        return reportRepository.findById(id).map(report -> {
            reportRepository.deleteById(id);
            return ResponseEntity.ok().body("Report deleted successfully with ID: " + id);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report not found with ID: " + id));
    }
}
