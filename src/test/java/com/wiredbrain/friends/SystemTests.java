package com.wiredbrain.friends;

import com.wiredbrain.friends.model.Friend;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SystemTests {


    @Test
    public void testCreateReadDelete() {
        //Testing Using Rest Template
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/friend";

        //Create new Friend
        Friend friend = new Friend("Gordon", "Moore");

        //Added using post Method
        ResponseEntity<Friend> entity = restTemplate.postForEntity(url, friend, Friend.class);

        //Get for Object
        Friend[] friends = restTemplate.getForObject(url, Friend[].class);
        //We assert that the first name is Gordon
        Assertions.assertThat(friends).extracting(Friend::getFirstName).containsOnly("Gordon");

        restTemplate.delete(url + "/" + entity.getBody().getId());
        //Assert that the list is empty
        Assertions.assertThat(restTemplate.getForObject(url, Friend[].class)).isEmpty();
    }
}
