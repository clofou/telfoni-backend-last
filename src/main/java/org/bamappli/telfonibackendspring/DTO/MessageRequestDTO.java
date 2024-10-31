package org.bamappli.telfonibackendspring.DTO;

import lombok.Data;
import org.bamappli.telfonibackendspring.Entity.Discussion;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MessageRequestDTO {
    private MultipartFile file;
    private String text;
    private Discussion discussion;
}
