package net.tibrasil.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/create")
    public UserModel create(@RequestBody UserModel userModel) {
        System.out.println(userModel.getName());
        var user = userRepository.findByUsername(userModel.getUsername());
        if(user != null && user.size() > 0) {
            System.out.println("User already exists");
            return null;
        } else {
            var userCreated = this.userRepository.save(userModel);
            return userCreated;
        }
    }
}
