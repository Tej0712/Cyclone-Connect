package onetoone.CYfind;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Paths;

@Service
public class CyFindItemService {

    @Autowired
    private CyFindItemRepository cyFindItemRepository;

    @Autowired
    private Path fileStorageLocation;  // Injected from FileStorageConfig

    public String findImagePathById(Long id) {
        return cyFindItemRepository.findById(id).map(CyFindItem::getImagePath).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();  // Ensure you resolve the path correctly
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + filename);
            }
        } catch (Exception ex) {
            throw new RuntimeException("File not found " + filename, ex);
        }
    }


    public CyFindItem addItem(CyFindItem newItem, MultipartFile file, String emailId) {
        String fileName = saveFile(file);
        newItem.setImagePath(fileName);// Set the imagePath to the saved file name
        newItem.setEmailId(emailId);
        return cyFindItemRepository.save(newItem);
    }

    private String saveFile(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;  // Return the relative path as filename
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public List<CyFindItem> findAllItems() {
        return cyFindItemRepository.findAll();
    }

    public CyFindItem findItemById(Long id) {
        return cyFindItemRepository.findById(id).orElse(null);
    }

    public CyFindItem updateItem(Long id, CyFindItem updatedItem, String emailId) {
        return cyFindItemRepository.findById(id).map(item -> {
            item.setItemName(updatedItem.getItemName());
            item.setDescription(updatedItem.getDescription());
            item.setDateFound(updatedItem.getDateFound());
            item.setCategory(updatedItem.getCategory());
            item.setLocation(updatedItem.getLocation());
            item.setEmailId(emailId);  // Update the email ID
            return cyFindItemRepository.save(item);
        }).orElseGet(() -> {
            updatedItem.setId(id);
            updatedItem.setEmailId(emailId);  // Set the email ID for new entries
            return cyFindItemRepository.save(updatedItem);
        });
    }

    public void deleteItem(Long id) {
        cyFindItemRepository.deleteById(id);
    }
}
