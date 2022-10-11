package com.bridgelabz.lmsadmin.controller;

import com.bridgelabz.lmsadmin.dto.AdminDTO;
import com.bridgelabz.lmsadmin.model.AdminModel;
import com.bridgelabz.lmsadmin.service.IAdminService;
import com.bridgelabz.lmsadmin.util.Response;
import com.bridgelabz.lmsadmin.util.ResponseClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IAdminService adminService;


    @PostMapping("/addAdmin")
    public ResponseEntity<Response> addAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        Response response = adminService.addAdmin(adminDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/getAllAdmins")
    public ResponseEntity<List<?>> getAllAdmins(@RequestHeader String token) {
        List<AdminModel> response =adminService.getAllAdmins(token);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @PutMapping("/updateAdminDetails/{id}")
    public ResponseEntity<Response> updateDetails(@RequestHeader String token,
                                                  @Valid @RequestBody AdminDTO adminDTO,
                                                  @PathVariable Long id) {
        Response response = adminService.updateAdminDetails(id, token, adminDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/deleteAdminDetails/{id}")
    public ResponseEntity<Response> deleteAdminDetails(@PathVariable Long id,
                                                       @RequestHeader String token) {
        Response response = adminService.deleteAdminDetails(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/loginAdmin")
    public ResponseEntity<ResponseClass> loginAdmin(@RequestParam String emailId,
                                                    @RequestParam String password) {
        ResponseClass responseClass = adminService.loginAdmin(emailId, password);
        return new ResponseEntity<>(responseClass, HttpStatus.OK);
    }


    @PutMapping("/updatePassword")
    public ResponseEntity<Response> updatePassword(@RequestHeader String token,
                                                   @RequestParam String password) {
        Response response = adminService.updatePassword(token, password);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @PutMapping("/resetPassword")
    public ResponseEntity<Response> resetPassword(@RequestParam String emailId) {
        Response response = adminService.resetPassword(emailId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/addProfilePath")
    public ResponseEntity<Response> addProfilePath(@RequestParam Long id,
                                                   @RequestHeader String token,
                                                   @RequestParam String profilePath) {
        Response response= adminService.addProfile(id, profilePath, token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/validate/{token}")
    public Boolean validate(@PathVariable String token) {
        return adminService.validate(token);
    }


}
