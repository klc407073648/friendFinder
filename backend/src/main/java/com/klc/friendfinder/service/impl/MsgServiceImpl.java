package com.klc.friendfinder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.klc.friendfinder.model.domain.Msg;
import com.klc.friendfinder.service.MsgService;
import com.klc.friendfinder.mapper.MsgMapper;
import org.springframework.stereotype.Service;

/**
* @author Jason
* @description 针对表【msg(消息表)】的数据库操作Service实现
* @createDate 2022-10-30 21:25:52
*/
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg>
    implements MsgService{

}




