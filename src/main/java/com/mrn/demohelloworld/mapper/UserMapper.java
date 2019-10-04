package com.mrn.demohelloworld.mapper;

import com.mrn.demohelloworld.dtos.UserMsDto;
import com.mrn.demohelloworld.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "Spring") // this is generated as a spring bean an can be retrieved via @Autowired
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // User to UserMsDTO
    @Mappings({
            @Mapping(source="email", target="email"),
            @Mapping(source="role", target="roleName")
    })
    UserMsDto userToUserMsDto(User user);

    // List<User> to List<UserMsDTO>
    List<UserMsDto> usersToUserDtos(List<User> users);
}
