package com.close.close.socialnetwork;

import com.close.close.security.AuthenticationService;
import com.close.close.user.User;
import com.close.close.user.UserModelAssembler;
import com.close.close.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socialnetwork")
public class SocialNetworkController {
    final private SocialNetworkService SOCIAL_NETWORK_SERVICE;
    final private UserModelAssembler USER_MODEL_ASSEMBLER;
    final private AuthenticationService AUTH_SERVICE;
    @Autowired
    public SocialNetworkController(SocialNetworkService SOCIAL_NETWORK_SERVICE, UserModelAssembler USER_MODEL_ASSEMBLER, AuthenticationService AUTH_SERVICE) {
        this.SOCIAL_NETWORK_SERVICE = SOCIAL_NETWORK_SERVICE;
        this.USER_MODEL_ASSEMBLER = USER_MODEL_ASSEMBLER;
        this.AUTH_SERVICE = AUTH_SERVICE;
    }

    /**
     * Adds a new social network to the authenticated user.
     * If the user already has authenticated that Social Network, it overwrites it.
     * @param socialNetwork social network to upload
     * @param username username of the user in the given social network
     * @return Response entity with the modified user
     */
    @PostMapping("/add")
    public ResponseEntity addSocialNetwork(@RequestParam SocialNetwork socialNetwork, @RequestParam String username){
        User user = AUTH_SERVICE.getAuthenticated();
        SOCIAL_NETWORK_SERVICE.addSocialNetwork(user, socialNetwork, username);
        return ResponseEntity.ok(USER_MODEL_ASSEMBLER.toModel(new UserDTO(user)));
    }

    /**
     * Removes a social network from the authenticated user.
     * @param socialNetwork social network to remove
     * @return Response entity with the modified user
     */
    @PostMapping("/remove")
    public ResponseEntity removeSocialNetwork(@RequestParam SocialNetwork socialNetwork){
        User user = AUTH_SERVICE.getAuthenticated();
        SOCIAL_NETWORK_SERVICE.removeSocialNetwork(user, socialNetwork);
        return ResponseEntity.ok(USER_MODEL_ASSEMBLER.toModel(new UserDTO(user)));
    }
}
