package api.apirest.controller;

import api.apirest.model.User;
import api.apirest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping()
    public List<User> getUsers() {
       return  userRepository.listAll();
    }

    @GetMapping("{username}")
    public User getOne(@PathVariable("username") String username){
        return  userRepository.finByUsername(username);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id){
        userRepository.remove(id);
    }

    @PostMapping("")
    public void postUser(@RequestBody User user){
        userRepository.save(user);
    }

    @PutMapping("")
    public void putUser(@RequestBody User user){
        userRepository.save(user);
    }
}
