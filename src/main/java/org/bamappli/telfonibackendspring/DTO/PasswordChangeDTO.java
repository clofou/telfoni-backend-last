package org.bamappli.telfonibackendspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PasswordChangeDTO {
    private String oldPassword;
    private String newPassword;
}
