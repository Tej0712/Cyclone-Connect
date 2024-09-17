package onetoone.CYfind;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class CyFindItemController {

    @Autowired
    private CyFindItemService cyFindItemService;

    private final Path imageStorageLocation = Paths.get("/var/www/images");

    @GetMapping("/")
    public List<CyFindItem> getAllItems() {
        return cyFindItemService.findAllItems();
    }

    /**
     * GET /items/image/{id} : Get the image file for a specific item by ID.
     *
     * @param id the ID of the item whose image to retrieve.
     * @return ResponseEntity<Resource> the ResponseEntity with status 200 (OK) and with body the image file, or with status 404 (Not Found).
     */
    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getItemImage(@PathVariable Long id, HttpServletRequest request) {
        String imagePath = cyFindItemService.findImagePathById(id);
        Resource file = cyFindItemService.loadFileAsResource(imagePath);

        // Determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (Exception ex) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CyFindItem> getItemById(@PathVariable Long id) {
        CyFindItem item = cyFindItemService.findItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> addItem(@RequestParam("file") MultipartFile file,
                                     @RequestParam("itemName") String itemName,
                                     @RequestParam("description") String description,
                                     @RequestParam("dateFound") String dateFoundStr,
                                     @RequestParam("category") String category,
                                     @RequestParam("emailId") String emailId,
                                     @RequestParam("location") String location) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateFound = sdf.parse(dateFoundStr);
            CyFindItem newItem = new CyFindItem(itemName, description, dateFound, category, location, null, emailId);
            CyFindItem savedItem = cyFindItemService.addItem(newItem, file, emailId);
            return ResponseEntity.ok(savedItem);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Date parsing failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CyFindItem> updateItem(@PathVariable Long id,
                                                 @RequestBody CyFindItem updatedItem,
                                                 @RequestParam("emailId") String emailId) {
        CyFindItem item = cyFindItemService.updateItem(id, updatedItem, emailId);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        cyFindItemService.deleteItem(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = imageStorageLocation.resolve(filename).normalize();
            Resource image = new UrlResource(filePath.toUri());
            if (image.exists() && image.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(determineMediaType(filename)) // Dynamically determine the media type
                        .body(image);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private MediaType determineMediaType(String filename) {
        String lowerCaseFilename = filename.toLowerCase();
        if (lowerCaseFilename.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (lowerCaseFilename.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else {
            return MediaType.IMAGE_JPEG;  // Default to JPEG
        }
    }
}
















//package onetoone.CYfind;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/items")
//public class CyFindItemController {
//
//    @Autowired
//    private CyFindItemService cyFindItemService;
//
//    @GetMapping("/")
//    public List<CyFindItem> getAllItems() {
//        return cyFindItemService.findAllItems();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<CyFindItem> getItemById(@PathVariable Long id) {
//        CyFindItem item = cyFindItemService.findItemById(id);
//        if (item != null) {
//            return ResponseEntity.ok(item);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping("/")
//    public ResponseEntity<?> addItem(@RequestParam("file") MultipartFile file,
//                                     @RequestParam("itemName") String itemName,
//                                     @RequestParam("description") String description,
//                                     @RequestParam("dateFound") String dateFoundStr,
//                                     @RequestParam("category") String category,
//                                     @RequestParam("emailId") String emailId,
//                                     @RequestParam("location") String location) {
//
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date dateFound = sdf.parse(dateFoundStr);
//            CyFindItem newItem = new CyFindItem(itemName, description, dateFound, category, location, null, emailId);
//            CyFindItem savedItem = cyFindItemService.addItem(newItem, file, emailId);
//            return ResponseEntity.ok(savedItem);
//        } catch (ParseException e) {
//            return ResponseEntity.badRequest().body("Date parsing failed: " + e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<CyFindItem> updateItem(@PathVariable Long id,
//                                                 @RequestBody CyFindItem updatedItem,
//                                                 @RequestParam("emailId") String emailId){
//        CyFindItem item = cyFindItemService.updateItem(id, updatedItem, emailId);
//        if (item != null) {
//            return ResponseEntity.ok(item);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
//        cyFindItemService.deleteItem(id);
//        return ResponseEntity.ok().build();
//    }
//
//
//}
//
