package com.exadel.discount.mapper;

import com.exadel.discount.dto.user.BaseUserDto;
import com.exadel.discount.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class BaseUserMapper {

    public interface UserMapper {
        User toUser(BaseUserDto baseUserDto);

        BaseUserDto toBaseUserDtoDto(User User);
    }
}
