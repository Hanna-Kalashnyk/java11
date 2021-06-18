package com.exadel.discount.controller;

import com.exadel.discount.dto.user.UserDto;
import com.exadel.discount.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto addUser(@RequestBody final UserDto userDto) {
        return userService.addUser(userDto);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable final UUID id) {
        return userService.findUserById(id);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable final UUID id) {
        userService.deleteUser(id);
    }

    @PutMapping("{id}")
    public UserDto editUser(@PathVariable final UUID id,
                            @RequestBody final UserDto userDto) {
        return userService.editUser(id, userDto);
    }

    @GetMapping("/{lastname}/{firstname}")
    public List<UserDto> getUsersByName(@PathVariable String lastname,
                                        @PathVariable String firstname) {
        return userService.findUsersByName(lastname, firstname);
    }
}
