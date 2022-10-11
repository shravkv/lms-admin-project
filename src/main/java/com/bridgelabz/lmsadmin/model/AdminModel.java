package com.bridgelabz.lmsadmin.model;

import com.bridgelabz.lmsadmin.dto.AdminDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "admin")
@Entity
public class AdminModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String mobile;
    private String emailId;
    private String password;
    private String profilePath;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public AdminModel() {
    }

    public AdminModel(AdminDTO adminDTO) {
        this.firstName = adminDTO.getFirstName();
        this.lastName = adminDTO.getLastName();
        this.mobile = adminDTO.getMobile();
        this.emailId = adminDTO.getEmailId();
        this.password = adminDTO.getPassword();
        this.status = adminDTO.getStatus();
    }



}
