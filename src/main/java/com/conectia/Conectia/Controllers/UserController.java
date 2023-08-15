package com.conectia.Conectia.Controllers;


import com.conectia.Conectia.Dtos.AddUser;
import com.conectia.Conectia.Dtos.ErrorData;
import com.conectia.Conectia.Dtos.UserDetail;
import com.conectia.Conectia.Models.User;
import com.conectia.Conectia.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity getAllUsers(){
        return  ResponseEntity.ok().body(userRepository.findAll().stream().map(UserDetail::new).toList());
    }

    @PostMapping
    @Transactional
    public ResponseEntity createUser(@RequestBody AddUser data){

        if (data.email() == null || data.password() == null ||
//                data.repassword() == null ||
                data.name() == null || data.born() == null){
            return ResponseEntity.badRequest().body(new ErrorData("user","Os campos devem ser preenchidos"));
        }

        if (userRepository.existsByEmail(data.email())){
            return ResponseEntity.badRequest().body(new ErrorData("user","Email já cadastrado"));

        }

//        if(data.password().equals(data.repassword())){
//            return ResponseEntity.badRequest().body(new ErrorData("senha", "As senhas devem ser iguais"));
//        }

        var user = new User(
                data.email(),
                passwordEncoder.encode(data.password()),
                data.name(),
                data.born()
        );

        userRepository.save(user);
        return ResponseEntity.ok().body("Conta criada com sucesso!");
    }


    @GetMapping("/{email}")
    public  ResponseEntity getUser(@PathVariable String email){
        var userValid = userRepository.existsByEmail(email);

        if(userValid == false){
            return ResponseEntity.badRequest().body(new ErrorData("user","User não localizado"));
        }

        var user = userRepository.getByEmail(email);


        return  ResponseEntity.ok().body(user);
    }

    @PutMapping("/follow/{userEmail}/{followEmail}")
    public ResponseEntity follow(@PathVariable String userEmail, @PathVariable String followEmail) {
        var me = userRepository.getByEmail(userEmail);
        var follow = userRepository.getByEmail(followEmail);

        if (follow == null) {
            return ResponseEntity.badRequest().body(new ErrorData("user","User não localizado"));
        }

        if(follow.getFollowers().contains(me.getEmail())){
            return ResponseEntity.badRequest().body(new ErrorData("follow","Você já segue essa pessoa"));
        } else {
            follow.getFollowers().add(me.getEmail());
            userRepository.save(me);
            return ResponseEntity.ok().body(me.getName() + " começou a seguir " + follow.getName());
        }
    }

    @PutMapping("/unfollow/{userEmail}/{followEmail}")
    public ResponseEntity unfollow(@PathVariable String userEmail, @PathVariable String followEmail) {
        var me = userRepository.getByEmail(userEmail);
        var follow = userRepository.getByEmail(followEmail);

        if (follow == null) {
            return ResponseEntity.badRequest().body(new ErrorData("user","User não localizado"));
        }

        if(follow.getFollowers().contains(me.getEmail())){
            follow.getFollowers().remove(me.getEmail());
            userRepository.save(me);
            return ResponseEntity.ok().body(me.getName() + " parou de seguir " + follow.getName());
        } else {
            return ResponseEntity.badRequest().body(new ErrorData("follow","Você não segue essa pessoa"));
        }
    }

}
