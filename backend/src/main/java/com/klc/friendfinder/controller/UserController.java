package com.klc.friendfinder.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.klc.friendfinder.common.BaseResponse;
import com.klc.friendfinder.common.ErrorCode;
import com.klc.friendfinder.common.ResultUtils;
import com.klc.friendfinder.exception.BusinessException;
import com.klc.friendfinder.model.domain.User;
import com.klc.friendfinder.model.request.UserLoginRequest;
import com.klc.friendfinder.model.request.UserRegisterRequest;
import com.klc.friendfinder.model.vo.UserVO;
import com.klc.friendfinder.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.klc.friendfinder.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:3000","http://$FRIENDFINDER_FRONTEND_IP:$FRIENDFINDER_FRONTEND_PORT"},allowedHeaders = "*",methods = {},allowCredentials = "true")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest ==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String userAccount=userRegisterRequest.getUserAccount();
        String userPassword=userRegisterRequest.getUserPassword();
        String checkPassword=userRegisterRequest.getCheckPassword();
        String planetCode=userRegisterRequest.getPlanetCode();

        if(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);

        return ResultUtils.success(id);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest ==null){
            return null;
        }

        String userAccount=userLoginRequest.getUserAccount();
        String userPassword=userLoginRequest.getUserPassword();

        if(StringUtils.isAnyBlank(userAccount, userPassword)){
            return null;
        }

        User user= userService.userLogin(userAccount, userPassword,request);
        return ResultUtils.success(user);

    }
    @PostMapping("/logout")
    public BaseResponse<Integer> userLoginout(HttpServletRequest request){
        if(request ==null){
            return null;
        }

        Integer result =  userService.userLogout(request);
        return ResultUtils.success(result);

    }
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username,HttpServletRequest request){
        if(!userService.isAdmin(request)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<User> queryWrapper =new QueryWrapper<>();

        if(StringUtils.isNoneBlank(username)){
            queryWrapper.like("username",username);
        }

        List<User> userList = userService.list();
        List<User> retUserList =userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());

        return ResultUtils.success(retUserList);
    }

    @GetMapping("/search/tags")
    public BaseResponse<List<User>> searchUsersBytags(@RequestParam(required = false) List<String> tagNameList){
        if(CollectionUtils.isEmpty(tagNameList)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        List<User> userList =userService.searchUsersByTags(tagNameList);
        return ResultUtils.success(userList);
    }

    @PostMapping("/update")
    public BaseResponse<Integer> updateUser(@RequestBody User user,HttpServletRequest request){
        //1.校验参数是否为空
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);

        //2.用户是否有权限
        //3.更新用户
        int  result= userService.updateUser(user,loginUser);
        return ResultUtils.success(result);
    }

    //支持分页
    @GetMapping("/recommend")
    public BaseResponse<Page<User>> recommendUsers(long pageSize,long pageNum,HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        //如果存在缓存数据，直接返回
        //不能直接根据用户查询缓存，因为pageSize和pageNum可能不一样，即每次查询相同范围时，才应该缓存
        String redisKey = String.format("yupao:user:recommand:%s:%s:%s",loginUser.getId(),pageNum,pageSize);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Page<User> userPage = (Page<User>) valueOperations.get(redisKey);

        if(userPage!=null){
            return ResultUtils.success(userPage);
        }

        //无缓存从数据库查询
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        userPage = userService.page(new Page<>(pageNum,pageSize),queryWrapper);

        //同时写入缓存
        try {
            valueOperations.set(redisKey, userPage, 30000, TimeUnit.MILLISECONDS);
        } catch(Exception e){
            log.error("redis error:",e);
        }

        return ResultUtils.success(userPage);
    }

    //原始推荐方法
    /*@GetMapping("/recommend")
    public BaseResponse<List<User>> recommendUsers(HttpServletRequest request){
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        List<User> userList = userService.list(queryWrapper);
        List<User> retUserList =userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());

        return ResultUtils.susscess(retUserList);
    }*/

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User curentUser =(User) userObj;
        //仅管理员可以查询
        if(curentUser == null ){
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }

        long userId =curentUser.getId();
        User user = userService.getById(userId);
        User safeUser =userService.getSafetyUser(user);
        return ResultUtils.success(safeUser);
    }

    @PostMapping ("/delete")
    public BaseResponse<Boolean> deleteUsers(@RequestBody long id,HttpServletRequest request){
        if(!userService.isAdmin(request)){
            return  null;
        }

        if(id <=0) {
            return null;
        }

        Boolean ret = userService.removeById(id);

        return ResultUtils.success(ret);
    }

    /**
     * 获取最匹配的用户
     * @param num
     * @param request
     * @return
     */
    @GetMapping("/match")
    public BaseResponse<List<User>> matchUsers(long num, HttpServletRequest request) {
        if (num < 0 || num > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        return  ResultUtils.success(userService.matchUsers(num,loginUser));
    }

}
