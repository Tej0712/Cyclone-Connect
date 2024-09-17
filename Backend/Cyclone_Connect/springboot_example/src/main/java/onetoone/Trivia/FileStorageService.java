package onetoone.Trivia;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path rootLocation = Paths.get("src/main/resources/static/images/history");

    public String store(MultipartFile file) throws IOException {
        // Assuming you store the file in a directory and want to keep the original file name
        String filename = file.getOriginalFilename();
        Path storageLocation = Paths.get("path/to/your/storage/directory");

        if (file.isEmpty()) {
            throw new IOException("Cannot store empty file " + filename);
        }

        if (filename.contains("..")) {
            // Security check to avoid path traversal attack
            throw new IOException("Cannot store file with relative path outside current directory " + filename);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path targetLocation = storageLocation.resolve(filename);
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename; // Return the filename after storing the file
        }
        // Catch blocks if necessary...
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    // You may want to add more methods for file operations as needed
}
