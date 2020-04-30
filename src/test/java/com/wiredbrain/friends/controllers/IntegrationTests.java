package com.wiredbrain.friends.controllers;

import com.wiredbrain.friends.controller.FriendController;
import com.wiredbrain.friends.model.Friend;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {

    @Autowired
    FriendController friendController;

    @Test
    public void testCreateReadDelete() {
        //Create new Friend
        Friend friend = new Friend("Gordon", "Moore");
        //Create new friend using friendController
        Friend friendResult = friendController.create(friend);
        //Read each friend
        Iterable<Friend> friends = friendController.read();
        Assertions.assertThat(friends).first().hasFieldOrPropertyWithValue("firstName", "Gordon");

        //Delete friend
        friendController.delete(friendResult.getId());
        //check if the list is empty
        Assertions.assertThat(friendController.read()).isEmpty();

    }
}
