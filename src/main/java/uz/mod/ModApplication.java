package uz.mod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import uz.mod.utils.FileStorageProperties;

@EnableConfigurationProperties({
        FileStorageProperties.class
})
@SpringBootApplication
public class ModApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModApplication.class, args);
    }

}
