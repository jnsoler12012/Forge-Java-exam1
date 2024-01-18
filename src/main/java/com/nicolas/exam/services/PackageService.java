package com.nicolas.exam.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nicolas.exam.models.Package;
import com.nicolas.exam.models.User;
import com.nicolas.exam.repositories.PackageRepository;
import com.nicolas.exam.repositories.UserRepository;

@Service
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private UserRepository userRepository;

    public Package editPackage(Long id, Package packageEdit){
        Optional<Package> packageToEdit = packageRepository.findById(id);

        if(packageToEdit.isPresent()){
            Package packageEdited = packageToEdit.get();

            packageEdited.setCost(packageEdit.getCost());

            return packageRepository.save(packageEdited);
        } else {
            return null;
        }
    }

    public Package switchPackage(Long id, String newTypePackage, User user) {
        Optional<Package> packageFinded = packageRepository.findById(id);

        Optional<User> userFined = userRepository.findById(user.getId());
        List<Package> packageTypeFinded = packageRepository.findAllByName(newTypePackage);

        if (packageFinded.isPresent() && userFined.isPresent()) {
            Package packageChanged = packageFinded.get();
            Package packageOlderCopy = new Package(packageChanged.getName(), packageChanged.getCost(), null);
            packageOlderCopy.setAvailable(false);

            Package packageTypeChanged = packageTypeFinded.get(0);
            User userChanged = userFined.get();

            System.out.println(packageTypeFinded);

            packageChanged.setName(packageTypeChanged.getName());
            packageChanged.setCost(packageTypeChanged.getCost());
            packageChanged.setUser(userChanged);
            userChanged.getPackages().add(packageChanged);

            userRepository.save(userChanged);
            packageRepository.save(packageOlderCopy);
            return packageRepository.save(packageChanged);
        }
        return null;
    }

    public Package registerPackage(Package packageCreated) {
        System.out.println("132812387989876q35497834q5678");
        List<Package> listPackage = packageRepository.findAll();

        System.out.println(packageRepository.findAll().contains(packageCreated));

        for (int x = 0; x < listPackage.size(); x++) {
            if (listPackage.get(x).getName().toLowerCase().equals(packageCreated.getName().toLowerCase())) {
                return null;
            }
        }

        packageCreated.setAvailable(true);
        return packageRepository.save(packageCreated);

    }

    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
    }

    public Package findPackageById(Long id) {

        return packageRepository.findById(id).orElse(null);
    }

    public List<Package> getAll() {
        return packageRepository.findAll();
    }

    public Package findByName(String Name){
        return packageRepository.findAllByName(Name).get(0);
    }
}
