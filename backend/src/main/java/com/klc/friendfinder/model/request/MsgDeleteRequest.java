package com.klc.friendfinder.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 *  消息删除请求体
 */
@Data
public class MsgDeleteRequest implements Serializable {

    private static final long serialVersionUID = -1796340682878515389L;

    String sendId;
    String content;
}
