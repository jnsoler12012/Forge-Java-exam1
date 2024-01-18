package com.nicolas.exam.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nicolas.exam.models.LoginUser;
import com.nicolas.exam.models.User;
import com.nicolas.exam.services.PackageService;
import com.nicolas.exam.services.UserService;
import com.nicolas.exam.validators.UserValidator;
import com.nicolas.exam.models.Package;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private PackageService packageService;
    @Autowired
    private UserValidator validator;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session,
            Model model, Package package1) {
        validator.validate(user, result);
        if (userService.findByEmail(user.getEmail()) == null) {
            if (result.hasErrors()) {
                return "register";
            } else {
                User u = userService.registerUser(user);
                session.setAttribute("user", u);
                return "redirect:/dashboard";
            }
        } else {
            model.addAttribute("errorRegister", "Email has been already taken");
            return "register";
        }

    }

    @RequestMapping("/register")
    public String registerForm(@ModelAttribute("user") User user, HttpSession session, Model model, Package package1) {

        String sessionPreviousLogged = redirectIfLogged(session, model, "register");

        if (sessionPreviousLogged == "FALSE") {
            model.addAttribute("user", session.getAttribute("user"));

            return "dashboard";
        } else {
            return sessionPreviousLogged;
        }
    }

    @RequestMapping({ "/login", "/" })
    public String loginForm(@ModelAttribute("loginUser") LoginUser loginUser, HttpSession session, Model model,
            Package package1) {
        String sessionPreviousLogged = redirectIfLogged(session, model, "login");

        if (sessionPreviousLogged == "FALSE") {
            model.addAttribute("user", session.getAttribute("user"));

            return "dashboard";
        } else {
            return sessionPreviousLogged;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(
            @Valid @ModelAttribute("loginUser") LoginUser loginUser,
            BindingResult result,
            HttpSession session,
            Model model,
            Package package1) {
        if (result.hasErrors()) {
            return "login";
        }

        if (userService.authenticateUser(loginUser)) {
            User user = userService.findByEmail(loginUser.getEmail());
            System.out.println(user.getId());
            session.setAttribute("user", user);

            return "redirect:/dashboard";
        } else {

            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    @RequestMapping("/dashboard")
    public String dashboard(HttpSession session, Model model, Package package1) {

        model.addAttribute("package1", new Package());
        String sessionPreviousLogged = redirectIfLogged(session, model, "redirect:/login");

        if (sessionPreviousLogged == "FALSE") {
            User user = (User) session.getAttribute("user");

            System.out.println(user.getRoles().get(0).getName().equals("ROLE_ADMIN"));
            if (user.getRoles().get(0).getName().equals("ROLE_ADMIN")) {
                model.addAttribute("user", session.getAttribute("user"));

                model.addAttribute("packages", packagesWithUser(packageService.getAll()));

                System.out.println(counterPackage(packageService.getAll()));

                model.addAttribute("packagesCounter", counterPackage(packageService.getAll()));

                return "dashboard";
            } else {
                return "redirect:users/" + user.getId();
            }

        } else {
            return sessionPreviousLogged;
        }
    }

    @RequestMapping("/users/{id}")
    public String showProduct(@PathVariable("id") Long id, HttpSession session, Model model,
            Package packageCustomized) {

        model.addAttribute("packageCustomized", new Package());

        User userPrevious = (User) session.getAttribute("user");
        User user = userService.findUserById(id);

        if (userPrevious != null) {
            String sessionPreviousLogged = redirectIfLogged(session, model, "redirect:/login");

            System.out.println("-=d-=a-=-=-=-=-=-=");
            System.out.println(userPrevious.getId());
            System.out.println(user.getId());

            if (sessionPreviousLogged == "FALSE" && user.getId() == userPrevious.getId()) {

                model.addAttribute("packages", packagesWithUser(packageService.getAll()));

                model.addAttribute("possiblePackages", uniquePackagesTypes(packageService.getAll()));

                model.addAttribute("user", user);
                return "showUser";
            } else {
                if (sessionPreviousLogged != "FALSE") {
                    return sessionPreviousLogged;
                } else {
                    return "redirect:/dashboard";
                }

            }
        }
        return "redirect:/dashboard";

    }

    @RequestMapping(value = "/packages/{id}/edit", method = RequestMethod.POST)
    public String editProductPost(@PathVariable("id") Long id,
            @ModelAttribute("packageCustomized") Package packageCustomized, BindingResult result, HttpSession session,
            Model model,
            Package packageCustomized2) {

        model.addAttribute("packageCustomized2", new Package());

        User userPrevious = (User) session.getAttribute("user");

        String sessionPreviousLogged = redirectIfLogged(session, model, "redirect:/login");

        if (sessionPreviousLogged == "FALSE" && userPrevious.getRoles().get(0).getName() != "ROLE_ADMIN") {

            if (result.hasErrors()) {

                return "register";
            } else {
                packageService.editPackage(id, packageCustomized);
                return "redirect:/dashboard";
            }

        } else {
            if (sessionPreviousLogged != "FALSE") {
                return sessionPreviousLogged;
            } else {
                return "redirect:/dashboard";
            }

        }
    }

    @RequestMapping(value = "/packages/{id}/delete")
    public String deletePackage(@PathVariable("id") Long id, HttpSession session, Model model,
            Package packageCustomized) {
        String sessionPreviousLogged = redirectIfLogged(session, model, "redirect:/login");
        User userPrevious = (User) session.getAttribute("user");
        System.out.println(
                "-=---=--------------287187932467893452678346578943w6587934w598743w654983756437895678435784365874356678");
        if (sessionPreviousLogged == "FALSE" && userPrevious.getRoles().get(0).getName() != "ROLE_ADMIN") {
            System.out.println(
                    "-=-2222222--=--------------287187932467893452678346578943w6587934w598743w654983756437895678435784365874356678");
            packageService.deletePackage(id);

            return "redirect:/dashboard";
        } else {
            System.out.println(
                    "NNOONNONONONONO-=---=--------------287187932467893452678346578943w6587934w598743w654983756437895678435784365874356678");
            return sessionPreviousLogged;
        }
    }

    @RequestMapping("/packages/{id}/edit")
    public String editProduct(@PathVariable("id") Long id, HttpSession session, Model model,
            Package packageCustomized) {

        model.addAttribute("packageCustomized", new Package());

        User userPrevious = (User) session.getAttribute("user");

        String sessionPreviousLogged = redirectIfLogged(session, model, "redirect:/login");

        try {
            if (sessionPreviousLogged == "FALSE" && userPrevious.getRoles().get(0).getName() != "ROLE_ADMIN") {
                model.addAttribute("packageToEdit", packageService.findPackageById(id));
                session.setAttribute("user", userPrevious);
                return "editPackage";

            } else {
                if (sessionPreviousLogged != "FALSE") {
                    return sessionPreviousLogged;
                } else {
                    return "redirect:/dashboard";
                }

            }
        } catch (Exception e) {
            return "redirect:/dashboard";
        }

    }

    @RequestMapping(value = "/switchPackage/{id}", method = RequestMethod.POST)
    public String switchPackage(@PathVariable("id") Long id,
            @ModelAttribute("packageCustomized") Package packageCustomized,
            BindingResult result,
            HttpSession session, Model model) {

        String sessionPreviousLogged = redirectIfLogged(session, model, "redirect:/login");

        if (sessionPreviousLogged == "FALSE") {
            User user = (User) session.getAttribute("user");

            Package packageChanged = packageService.switchPackage(id, packageCustomized.getName(), user);

            if (packageChanged == null) {
                model.addAttribute("errorName", "Name has been already taken, please use other");
                return "/users/" + user.getId();
            } else {
                return "redirect:/users/" + user.getId();
            }
        } else {
            return sessionPreviousLogged;
        }
    }

    @RequestMapping(value = "/package/new", method = RequestMethod.POST)
    public String registerTablet(@Valid @ModelAttribute("package1") Package package1, BindingResult result,
            HttpSession session, Model model) {

        String sessionPreviousLogged = redirectIfLogged(session, model, "redirect:/login");

        if (sessionPreviousLogged == "FALSE") {
            if (result.hasErrors()) {
                return "dashboard";
            } else {
                package1.setUser(userService.findUserById(((User) session.getAttribute("user")).getId()));

                Package packageNew = packageService.registerPackage(package1);
                if (packageNew == null) {
                    model.addAttribute("errorName", "Name has been already taken, please use other");
                    return "dashboard";
                } else {
                    return "redirect:/dashboard";
                }

            }
        } else {
            return sessionPreviousLogged;
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    public String redirectIfLogged(HttpSession session, Model model, String pathRedirect) {
        if (session.getAttribute("user") != null) {
            return "FALSE";
        } else {
            return pathRedirect;
        }
    }

    public List<Package> packagesWithUser(List<Package> packages) {
        List<Package> packagesWithUser = new ArrayList<>();
        for (int x = 0; x < packages.size(); x++) {

            // System.out.println("=================================================");
            // System.out.println(packages.get(x).toString());
            if (packages.get(x).getUser() != null) {
                packagesWithUser.add(packages.get(x));
            }
        }
        return packagesWithUser;

    }

    public List<String> uniquePackagesTypes(List<Package> packages) {
        List<String> uniquePackages = new ArrayList<>();
        for (int x = 0; x < packages.size(); x++) {

            // System.out.println("=================================================");
            // System.out.println(packages.get(x).toString());
            if (!uniquePackages.contains(packages.get(x).getName())) {
                uniquePackages.add(packages.get(x).getName());
            }
        }
        return uniquePackages;

    }

    public HashMap<Package, Integer> counterPackage(List<Package> list) {

        System.out.println("dsakijadshjadshashdjkhkajsdhjkasdasdasddddddddd");
        System.out.println(list);
        HashMap<String, Integer> hashMap = new HashMap<>();

        HashMap<Package, Integer> hashMapObject = new HashMap<>();

        for (Package element : list) {
            if (hashMap.containsKey(element.getName())) {
                if (element.getUser() != null) {
                    System.out.println("-=-adsasdadsadsasdsdadsadasd");
                    hashMap.put(element.getName(), hashMap.get(element.getName()) + 1);
                }

            } else {
                if (element.getUser() != null) {
                    hashMap.put(element.getName(), 1);
                } else {
                    hashMap.put(element.getName(), 0);
                }

            }
        }

        hashMap.forEach((key, value) -> {
            hashMapObject.put(packageService.findByName(key), value);
        });

        System.out.println("dsakijadshjadshashdjkhkajsdhjkasdasdasddddddddd");
        System.out.println(hashMapObject);

        List<Map.Entry<Package, Integer>> packageList = new LinkedList<Map.Entry<Package, Integer>>(
                hashMapObject.entrySet());

        Collections.sort(packageList, new Comparator<Map.Entry<Package, Integer>>() {
            public int compare(Map.Entry<Package, Integer> o1,
                    Map.Entry<Package, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<Package, Integer> returnValue = new LinkedHashMap<Package, Integer>();

        for (Map.Entry<Package, Integer> aa : packageList) {
            returnValue.put(aa.getKey(), aa.getValue());
        }
        System.out.println(returnValue);

        return returnValue;
    }

}
