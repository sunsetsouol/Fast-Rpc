package shop.sunsetsouol;

import org.springframework.stereotype.Service;
import shop.sunsetsouol.annotiation.RpcService;
import shop.sunsetsouol.service.UserService;

/**
 * @author YinJunBiao
 * @date 2025/2/13 15:09
 * @Description
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public String getUserInfo() {
        return "user info";
    }
}
