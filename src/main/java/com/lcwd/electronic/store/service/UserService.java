package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entites.User;

import java.util.List;

public interface UserService {
    //creatw
    UserDto createUser(UserDto userDto);
    //update
  UserDto updateUser(UserDto userDto,String userId);

    //delete
    void deleteUser(String userId );


    //get all users
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir );

    //get single user byId
    UserDto getUserById(String userId);

    //get single user by email
    UserDto getUserByEmail(String email);


    //search user
    List<UserDto> searchUser(String keyWord);

}
