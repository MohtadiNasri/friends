package com.wiredbrain.friends.controller;

import com.wiredbrain.friends.model.Friend;
import com.wiredbrain.friends.services.FriendService;
import com.wiredbrain.friends.util.ErrorMessage;
import com.wiredbrain.friends.util.FieldErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FriendController {

    @Autowired
    FriendService friendService;

    // Added the validation API Annotation to check empty field
    @PostMapping("/friend")
    public Friend create(@Valid @RequestBody Friend friend){
       return friendService.save(friend);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<FieldErrorMessage> fieldErrorMessages = fieldErrors
                .stream()
                .map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return fieldErrorMessages;
    }


    //    2- If the new friend doesn't have a name, last name or an Id an Exception will be raised
    //    @PostMapping("/friend")
    //    Friend create(@RequestBody Friend friend) throws ValidationException{
    //        if(friend.getId()==0 && friend.getFirstName()!=null && friend.getLastName()!=null)
    //            return friendService.save(friend);
    //        else
    //            throw new ValidationException("friend cannot be created");
    //    }

    //    1- Commented to avoid saving empty field
    //    @PostMapping("/friend")
    //    Friend create(@RequestBody Friend friend){
    //        return friendService.save(friend);
    //    }

    @GetMapping("/friend")
    public Iterable<Friend> read(){
        return friendService.findAll();
    }

    //    Commented to avoid adding new friend if the id doesn't exist
    //    @PutMapping("/friend")
    //    Friend update(@RequestBody Friend friend){
    //        return friendService.save(friend);
    //    }

    @PutMapping("/friend")
    ResponseEntity<Friend> update(@RequestBody Friend friend){
        //check to avoid adding new friend if the id doesn't exist
        if(friendService.findById(friend.getId()).isPresent())
            return new ResponseEntity(friendService.save(friend), HttpStatus.OK);
        else
            return new ResponseEntity(friend,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/friend/{id}")
    public void delete(@PathVariable Integer id){
        friendService.deleteById(id);
    }

    @GetMapping("/friend/{id}")
    Optional<Friend> findById(@PathVariable Integer id){
        return friendService.findById(id);
    }

    @GetMapping("/friend/search")
    Iterable<Friend> findByQuery(@RequestParam(value = "first", required = false)String firstName,
                                 @RequestParam(value = "last", required = false)String lastName){
        if(firstName!=null && lastName!=null)
            return friendService.findByFirstNameAndLastName(firstName, lastName);
        else if(firstName!=null)
            return friendService.findByFirstName(firstName);
        else if(lastName!=null)
            return friendService.findByLastName(lastName);
        else
            return friendService.findAll();
    }


}
