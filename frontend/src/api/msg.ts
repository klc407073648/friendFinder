import myAxios from "../plugins/myAxios";
import {Toast} from 'vant';
import qs from 'qs';

const success = ' succeed'
const error = ' error'
const rquest_fail = '请求失败'

/**
 * 根据标签搜索
 * @param searchContent 搜索内容
 * @param status 状态 0:'公开',1:'私有',2:'加密',
 */
export async function addMsg(targetId:number,content = '') {
    const url = '/msg/add';
    return await myAxios.post(url, {
        content: content,
        targetId:targetId,
    });
}

export async function deleteMsg(sendId:number,content = '') {
    const url = '/msg/delete';
    return await myAxios.post(url, {
        content: content,
        sendId:sendId,
    });
}

/**
 * 创建队伍
 * @param postData 队伍信息
 */
export async function getCurrentUserMsg() {
    const url = "/msg/get/current/msg";
    return await myAxios.get(url)
        .then(function (response) {
            console.log(url + success, response);
            return response?.data;
        })
        .catch(function (error) {
            console.error(url + error, error);
            Toast.fail(rquest_fail)
        })
}
