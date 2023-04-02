package com.klc.friendfinder.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.klc.friendfinder.common.BaseResponse;
import com.klc.friendfinder.common.ErrorCode;
import com.klc.friendfinder.common.ResultUtils;
import com.klc.friendfinder.exception.BusinessException;
import com.klc.friendfinder.model.domain.Msg;
import com.klc.friendfinder.model.domain.User;
import com.klc.friendfinder.model.request.MsgDeleteRequest;
import com.klc.friendfinder.model.request.MsgUpdateRequest;
import com.klc.friendfinder.model.request.UserRegisterRequest;
import com.klc.friendfinder.model.request.MsgAddRequest;
import com.klc.friendfinder.service.MsgService;
import com.klc.friendfinder.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/msg")
@CrossOrigin(origins = {"http://localhost:3000","http://81.68.132.31:3000"},allowedHeaders = "*",methods = {},allowCredentials = "true")
@Slf4j
public class MsgController {
    @Resource
    private MsgService msgService;

    @Resource
    private UserService userService;

    @PostMapping("/add")
    public BaseResponse<Long> addMsg(@RequestBody MsgAddRequest msgAddRequest, HttpServletRequest request){
        if(msgAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //应该从登录状态里获取当前的登录用户Id，且只有登录后才能给其他人发消息
        User user = userService.getLoginUser(request);

        Msg msg = new Msg();
        msg.setSendId(user.getId());
        msg.setTargetId(Long.valueOf(msgAddRequest.getTargetId()));
        msg.setContent(msgAddRequest.getContent());

        boolean result = msgService.save(msg);
        if( !result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return ResultUtils.success(msg.getId());
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteMsg(@RequestBody MsgDeleteRequest msgDeleteRequest, HttpServletRequest request){
        if(msgDeleteRequest ==null || StringUtils.isBlank(msgDeleteRequest.getContent())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);

        QueryWrapper<Msg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sendId", msgDeleteRequest.getSendId());
        queryWrapper.eq("targetId", user.getId());
        queryWrapper.eq("content", msgDeleteRequest.getContent());
        Msg deleteMsg = msgService.getOne(queryWrapper);

        if(deleteMsg ==null ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"要删除的消息不存在");
        }

        boolean result = msgService.removeById(deleteMsg.getId());

        if( !result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success(result);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateMsg(@RequestBody MsgUpdateRequest msgUpdateRequest){
        if(msgUpdateRequest ==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(true);
    }

    @GetMapping("/get/current/msg")
    public BaseResponse<List<Msg>> getCurrentUserMsg(HttpServletRequest request){
        User user = userService.getLoginUser(request);
        QueryWrapper<Msg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("targetId", user.getId());
        List<Msg> msgList = msgService.list(queryWrapper);
        return ResultUtils.success(msgList);
    }

}
