package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.ImageResponse;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.service.FileService;
import com.lcwd.electronic.store.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    private Logger logger= LoggerFactory.getLogger(UserController.class);
    //create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto userDto1=userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }
    @PutMapping("/{userId}")
    //update
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") String userId,@RequestBody UserDto userDto){
         UserDto userDto2= userService.updateUser(userDto, userId);
         return new ResponseEntity<>(userDto2,HttpStatus.OK);
    }

    //delete
      @DeleteMapping("/{userId}")
      public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        ApiResponseMessage message=ApiResponseMessage.builder().message("User is deleted successfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>( message,HttpStatus.OK);
      }

    //getAll
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(@RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
                                                        @RequestParam(value="pageSize",defaultValue="10",required = false) int pageSize,
                                                        @RequestParam(value="sortBy",defaultValue="name",required = false) String sortBy,
                                                        @RequestParam(value="sortDir",defaultValue="asc",required = false) String sortDir
    ){
        return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
    }

    //getSingle
    @GetMapping("/{userId}")
     public ResponseEntity<UserDto>getUser(@PathVariable String userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
     }

    //getByemail
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto>getUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    // Search user
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>>searchUser(@PathVariable String keyword){
        return new ResponseEntity<>(userService.searchUser(keyword),HttpStatus.OK);
    }
    //Upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException {
           String imageName= fileService.uploadFile(image,imageUploadPath);
           UserDto user=userService.getUserById(userId);
           user.setImageName(imageName);
           userService.updateUser(user,userId);
           UserDto userDto=userService.updateUser(user,userId);

          ImageResponse imageResponse= ImageResponse.builder().imageName(imageName).message("Image is uploaded Successfully").success(true).status(String.valueOf(HttpStatus.CREATED)).build();
             return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }
    //serve user Image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
            UserDto user= userService.getUserById(userId);
            logger.info("User image name:{}",user.getImageName());
        InputStream resource= fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }


}
