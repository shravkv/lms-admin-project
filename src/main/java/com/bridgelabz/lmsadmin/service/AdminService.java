package com.bridgelabz.lmsadmin.service;

import com.bridgelabz.lmsadmin.dto.AdminDTO;
import com.bridgelabz.lmsadmin.exception.LMSException;
import com.bridgelabz.lmsadmin.model.AdminModel;
import com.bridgelabz.lmsadmin.repository.AdminRepository;
import com.bridgelabz.lmsadmin.util.Response;
import com.bridgelabz.lmsadmin.util.ResponseClass;
import com.bridgelabz.lmsadmin.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    MailService mailService;


    @Override
    public Response addAdmin(AdminDTO adminDTO) {
        AdminModel adminModel = new AdminModel(adminDTO);
        adminModel.setCreatedTime(LocalDateTime.now());
        adminRepository.save(adminModel);
        String body = "Admin Added With Id is : " + adminModel.getId();
        String subject = "Admin Registration Successfully ...";
        mailService.send(adminModel.getEmailId(), body, subject);
        return new Response(200, "Success", adminModel);
    }


    @Override
    public List<AdminModel> getAllAdmins(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isIdPresent = adminRepository.findById(userId);
        if (isIdPresent.isPresent()) {
            List<AdminModel> isAdmins = adminRepository.findAll();
            if (isAdmins.size() > 0) {
                return isAdmins;
            } else {
                throw new LMSException(400, "admin not found");
            }
        }
        throw new LMSException(400, "Token Is Wrong");
    }


    @Override
    public com.bridgelabz.lmsadmin.util.Response updateAdminDetails(Long id, String token, AdminDTO adminDTO) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isIdPresent = adminRepository.findById(userId);
        if (isIdPresent.isPresent()) {
            Optional<AdminModel> isAdminPresent = adminRepository.findById(id);
            if (isAdminPresent.isPresent()) {
                isAdminPresent.get().setFirstName(adminDTO.getFirstName());
                isAdminPresent.get().setLastName(adminDTO.getLastName());
                isAdminPresent.get().setMobile(adminDTO.getMobile());
                isAdminPresent.get().setEmailId(adminDTO.getEmailId());
                isAdminPresent.get().setPassword(adminDTO.getPassword());
                isAdminPresent.get().setStatus(adminDTO.getStatus());
                isAdminPresent.get().setUpdatedTime(LocalDateTime.now());
                adminRepository.save(isAdminPresent.get());
                String body = "Admin Updated With Id is : " + isAdminPresent.get().getId();
                String subject = "Admin Details Updated Successfully ...";
                mailService.send(isAdminPresent.get().getEmailId(), body, subject);
                return new Response(200, "Success", isAdminPresent.get());
            } else {
                throw new LMSException(400, "No Admin found with this ID");
            }
        }
        throw new LMSException(400, "Token is Wrong");
    }



    @Override
    public Response deleteAdminDetails(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isIdPresent = adminRepository.findById(userId);
        if (isIdPresent.isPresent()) {
            Optional<AdminModel> isAdminPresent = adminRepository.findById(id);
            if (isAdminPresent.isPresent()) {
                adminRepository.delete(isAdminPresent.get());
                String body = "Admin Details Deleted With Id is : " + isAdminPresent.get().getId();
                String subject = "Admin Details Deleted Successfully ...";
                mailService.send(isAdminPresent.get().getEmailId(), body, subject);
                return new Response(200, "Success", isAdminPresent.get());
            } else {
                throw new LMSException(400, "No Admin found with this ID");
            }
        }
        throw new LMSException(400, "Token is Wrong");
    }


    @Override
    public ResponseClass loginAdmin(String emailId, String password) {
        Optional<AdminModel> isEmailPresent = adminRepository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()) {
            if (isEmailPresent.get().getPassword().equals(password)) {
                String token = tokenUtil.createToken(isEmailPresent.get().getId());
                return new ResponseClass(200, "Login successfully", token);
            } else {
                throw new LMSException(400, "Password is wrong");
            }
        }
        throw new LMSException(400, "Not found with this Email id");
    }


    @Override
    public Response updatePassword(String token, String password) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isIdPresent = adminRepository.findById(userId);
        if (isIdPresent.isPresent()) {
            isIdPresent.get().setPassword(password);
            adminRepository.save(isIdPresent.get());
            return new Response(200, "Success", isIdPresent.get());
        } else {
            throw new LMSException(400, "Token is Wrong");
        }
    }


    @Override
    public Response resetPassword(String emailId) {
        Optional<AdminModel> isEmailPresent = adminRepository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()) {
            String token = tokenUtil.createToken(isEmailPresent.get().getId());
            String url = System.getenv("url");
            String subject = "Reset Password";
            String body = " Reset password Use this link \n" + url + "\n Use this token to reset \n" + token;
            mailService.send(isEmailPresent.get().getEmailId(), body, subject);
            return new Response(200, "Success", isEmailPresent.get());
        }
        throw new LMSException(400, "Email is not found");
    }


    @Override
    public Response addProfile(Long id, String profilePath, String token) {
        Optional<AdminModel> isIdPresent = adminRepository.findById(id);
        if (isIdPresent.isPresent()) {
            isIdPresent.get().setProfilePath(profilePath);
            return new Response(200, "Success", isIdPresent.get());
        } else {
            throw new LMSException(400, "Admin Not found with this id");
        }
    }

    @Override
    public Boolean validate(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isUserPresent = adminRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            return true;
        }
        return false;
    }


}
