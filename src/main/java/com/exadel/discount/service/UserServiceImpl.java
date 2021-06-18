package com.exadel.discount.service;

import com.exadel.discount.dto.user.UserDto;
import com.exadel.discount.entity.User;
import com.exadel.discount.exception.UserNotFoundException;
import com.exadel.discount.exception.UserSuchNameNotFoundException;
import com.exadel.discount.mapper.UserMapper;
import com.exadel.discount.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDto addUser(UserDto UserDto) {
        User user = userMapper.toUser(UserDto);
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto findUserById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> userList = userRepository.findAll();
        return userMapper.toUserDtoList(userList);
    }

    @Override
    public List<UserDto> findUsersByName(String lastName, String firstName) {
        List<User> suchNameUserList = userRepository.findDistinctByLastNameAndFirstName(lastName, firstName);
        if (suchNameUserList.size() == 0) throw new UserSuchNameNotFoundException(lastName, firstName);
        return userMapper.toUserDtoList(suchNameUserList);
    }

    @Transactional
    @Override
    public UserDto editUser(UUID id, UserDto userDto) {
        UserDto editedUser = findUserById(id);
        editedUser.setFirstName(userDto.getFirstName());
        editedUser.setLastName(userDto.getLastName());
        editedUser.setEmail(userDto.getEmail());
        editedUser.setPhone(userDto.getPhone());
        editedUser.setRole(userDto.getRole());
        editedUser.setLogin(userDto.getLogin());
        editedUser.setPassword(userDto.getPassword());
        return editedUser;
    }

    @Transactional
    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
