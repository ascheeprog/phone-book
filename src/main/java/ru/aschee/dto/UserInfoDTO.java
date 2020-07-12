package ru.aschee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {

    private UserDTO user;

    private CompanyDTO company;

    private List<ContactDTO> contacts;

}
