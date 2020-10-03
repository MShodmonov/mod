package uz.mod.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileResponse {
        private UUID id;
        private String fileName;
        private String fileDownloadUri;
        private String fileType;
        private long size;
}
