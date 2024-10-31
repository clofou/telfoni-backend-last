package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateFcmTokenDTO {
    private Long userId;
    private String token;
}
