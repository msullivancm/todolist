package net.tibrasil.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        System.out.println(userModel.getName());
        var user = userRepository.findByUsername(userModel.getUsername());
        if(user != null && user.size() > 0) {
            System.out.println("User already exists");
            return ResponseEntity.badRequest().body("User already exists");
        } else {
            
            var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

            userModel.setPassword(passwordHashred);

            var userCreated = this.userRepository.save(userModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        }
        
    }
}
