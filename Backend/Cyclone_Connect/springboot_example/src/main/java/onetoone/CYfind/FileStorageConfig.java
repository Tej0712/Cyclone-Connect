package onetoone.CYfind;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileStorageConfig {
    @Bean
    public Path fileStorageLocation() {
        // Using user's home directory with a subdirectory for images
        String directory = System.getProperty("user.home") + "/images";
        return Paths.get(directory).toAbsolutePath().normalize();
    }
}
