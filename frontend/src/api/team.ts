import myAxios from "../plugins/myAxios";
import {Toast} from 'vant';
import qs from 'qs';

/**
 * 根据标签搜索
 * @param searchContent 搜索内容
 * @param status 状态 0:'公开',1:'私有',2:'加密',
 */
export async function getTeamList(searchContent = '',status = 0) {
    const url = '/team/list';
    return await myAxios.get(url,{
        params:{
            searchText:searchContent,
            status
        }
    });
}

/**
 * 创建队伍
 * @param postData 队伍信息
 */
export async function createTeam(postData:any) {
    return await myAxios.post("/team/add",postData);
}

/**
 * 更新队伍
 * @param postData 队伍信息
 */
export async function updateTeam(postData:any) {
    return await myAxios.post("/team/update",postData);
}

/**
 * 获取登录用户创建的队伍
 * @param searchContent 过滤条件
 */
export async function getMyCreateTeam(searchContent = '') {
    return await myAxios.get('/team/list/my/create',{
        params:{
            searchText:searchContent
        }
    });
}

/**
 * 获取登录用户加入的队伍
 * @param searchContent 过滤条件
 */
export async function getMyJoinTeam(searchContent = '') {
    return myAxios.get('/team/list/my/join',{
        params:{
            searchText:searchContent
        }
    });
}
