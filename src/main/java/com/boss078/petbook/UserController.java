package com.boss078.petbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller    // This means that this class is a Controller
@RequestMapping(path="user") // This means URL's start with /user (after Application path)
public class UserController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @PostMapping(path="add") // Map ONLY GET Requests
    public @ResponseBody String addNewUser(@RequestParam String name
            , @RequestParam String surname, @RequestParam Integer age) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
            User n = new User();
            n.setName(name);
            n.setSurname(surname);
            n.setAge(age);
            userRepository.save(n);
            return "Saved.";
    }

    @GetMapping("all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping("find")
    public @ResponseBody Optional<User> getUser(@RequestParam Integer id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id);
        }
        else {
            return null;
        }
    }

    @GetMapping("pets")
    public @ResponseBody List<Pet> getPets(@RequestParam Integer id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).get().getPets();
        }
        else {
            return null;
        }
    }

    @PutMapping("update")
    public @ResponseBody String updateUser(@RequestParam Integer id, @RequestParam String newName
            , @RequestParam String newSurname, @RequestParam Integer newAge) {
        if (userRepository.existsById(id)) {
            User n = userRepository.findById(id).get();
            if (newName != null  && !newName.isEmpty())
                n.setName(newName);
            if (newSurname != null && !newSurname.isEmpty())
                n.setSurname(newSurname);
            if (newAge != null)
                n.setAge(newAge);
            userRepository.save(n);
            return "Updated.";
        }
        else {
            return "Not updated, User with this id is not exist.";
        }
    }

    @DeleteMapping("delete")
    public @ResponseBody String deleteUser(@RequestParam Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "Deleted";
        }
        else {
            return "Not deleted, User with this id is not exist.";
        }
    }

}