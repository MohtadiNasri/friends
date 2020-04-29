package com.wiredbrain.friends.services;

import com.wiredbrain.friends.model.Friend;
import org.springframework.data.repository.CrudRepository;

public interface FriendService extends CrudRepository<Friend,Integer> {

}
