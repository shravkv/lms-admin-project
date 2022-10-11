package com.bridgelabz.lmsadmin.service;

import com.bridgelabz.lmsadmin.dto.AdminDTO;
import com.bridgelabz.lmsadmin.model.AdminModel;
import com.bridgelabz.lmsadmin.util.Response;
import com.bridgelabz.lmsadmin.util.ResponseClass;

import java.util.List;

public interface IAdminService {

    Response addAdmin(AdminDTO adminDTO);

    List<AdminModel> getAllAdmins(String token);

    Response updateAdminDetails(Long id, String token, AdminDTO adminDTO);

    Response deleteAdminDetails(Long id, String token);

    ResponseClass loginAdmin(String emailId, String password);

    Response updatePassword(String token, String password);

    Response resetPassword(String emailId);

    Response addProfile(Long id, String profilePath, String token);

    Boolean validate(String token);
}
