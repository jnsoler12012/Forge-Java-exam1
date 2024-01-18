package com.nicolas.exam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.nicolas.exam.models.LoginUser;
import com.nicolas.exam.models.Package;
import com.nicolas.exam.models.Role;
import com.nicolas.exam.models.User;
import com.nicolas.exam.repositories.PackageRepository;
import com.nicolas.exam.repositories.RoleRepository;
import com.nicolas.exam.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PackageRepository packageRepository;

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);

        System.out.println(userRepo.findAll());
        if (userRepo.findAll().isEmpty()) {
            System.out.println("ADMIASNDAISDINASDINADSINDASIN");
            if (!roleRepo.findAll().contains("ROLE_ADMIN")) {
                Role roleAdmin = new Role("ROLE_ADMIN", user);
                roleRepo.save(roleAdmin);
            }
            user.setRoles(roleRepo.findByName("ROLE_ADMIN"));
        } else {
             System.out.println("ADMIASNDAISDINASDINADSINDASIN1233333333333333333333333333333333333333333");
            if (!roleRepo.findAll().contains("ROLE_USER")) {
                Role roleNormal = new Role("ROLE_USER", user);
                roleRepo.save(roleNormal);
            }

            user.setRoles(roleRepo.findByName("ROLE_USER"));
        }
        
        Package basicPackage = new Package("Basic", 10, user);
        basicPackage.setAvailable(true);
        
        user.getPackages().add(basicPackage);

        packageRepository.save(basicPackage);
        return userRepo.save(user);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User findUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public boolean authenticateUser(LoginUser loginUser) {
        User user = userRepo.findByEmail(loginUser.getEmail());
        if (user == null) {
            return false;
        } else {
            if (BCrypt.checkpw(loginUser.getPassword(), user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean isAdmin(User user) {
        if (user.getRoles().contains(roleRepo.findRoleByName("ROLE_ADMIN"))) {
            return true;
        }
        return false;
    }
}
