package com.klc.friendfinder.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 *  消息添加请求体
 */
@Data
public class MsgUpdateRequest implements Serializable {

    private static final long serialVersionUID = -1796340682878515389L;

    Integer targetId;
    String content;
}
