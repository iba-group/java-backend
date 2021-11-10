package com.ibagroup.petstore.controller.user;

import com.ibagroup.petstore.config.demo.DemoException;
import com.ibagroup.petstore.dto.OperationMessageDto;
import com.ibagroup.petstore.dto.user.UserDto;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v2/")
public class UserController {

  private Map<String, UserDto> users = new HashMap<>();

  @PostMapping(value = "user/",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createUser(@RequestBody UserDto user) {
    saveUser(user);
    return ResponseEntity.ok(new OperationMessageDto("Successful operation"));
  }

  @PostMapping(value = "users/",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createUsers(@RequestBody List<UserDto> users) {
    users.forEach(this::saveUser);
    return ResponseEntity.ok(new OperationMessageDto("Successful operation"));
  }

  @GetMapping(value = "user/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity loginUser(@RequestParam String username, @RequestParam String password) {

    Boolean isUserExistsAndPasswordMatches = Optional.ofNullable(users.get(username))
        .map(user -> user.getPassword().equals(password)).orElse(false);

    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Rate-Limit", "1800");
    headers.add(" X-Expires-After", LocalDateTime.now().plusHours(1).toString());

    if (!isUserExistsAndPasswordMatches) {
      HashMap<String, String> failures = new HashMap<>();
      failures.put("username", "Nonexistent username");
      failures.put("password", "Invalid password");
      throw new DemoException(failures);
    }

//    return isUserExistsAndPasswordMatches
//        ? ResponseEntity.ok().headers(headers).body(new OperationMessageDto("Successful operation"))
//        : ResponseEntity.badRequest().body(new OperationMessageDto("Not registered or invalid password is specified"));

    return ResponseEntity.ok().headers(headers).body(new OperationMessageDto("Successful operation"));
  }

  @GetMapping(value = "user/logout", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity logoutUser(@RequestParam String username) {
    return ResponseEntity.ok(new OperationMessageDto(username +" successfully logged out"));
  }

  @GetMapping(value = "user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity getUserByName(@PathVariable String username) {
    Optional<UserDto> foundUser = Optional.ofNullable(users.get(username));
    return foundUser.isPresent() ? ResponseEntity.ok(foundUser.get())
        : ResponseEntity.noContent().build();
  }

  @PutMapping(value = "user/{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateUser(@PathVariable String username, @RequestBody UserDto user) {
    users.put(username, user);
    return ResponseEntity.ok(new OperationMessageDto("Successful operation"));
  }

  @DeleteMapping(value = "user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity deleteUser(@PathVariable String username) {
    UserDto user = users.remove(username);
    return ResponseEntity.ok(user);
  }

  private void saveUser(@RequestBody UserDto user) {
    long size = users.size();
    users.put(user.getUsername(), user);
    user.setId(size);
  }

}
