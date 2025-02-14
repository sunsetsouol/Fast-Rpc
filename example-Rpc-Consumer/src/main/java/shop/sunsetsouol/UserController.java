package shop.sunsetsouol;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.sunsetsouol.annotiation.RpcReference;
import shop.sunsetsouol.service.UserService;

/**
 * @author YinJunBiao
 * @date 2025/2/13 16:37
 * @Description
 */
@RestController
public class UserController {
    @RpcReference
    private UserService userService;
    @GetMapping("/a")
    public String getUser(){
        String userInfo = userService.getUserInfo();
        System.out.println(userInfo);
        return userInfo;
    }

}
