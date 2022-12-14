import myAxios from "../plugins/myAxios";
import {Toast} from 'vant';
import qs from 'qs';

/**
 * 根据标签搜索
 * @param searchContent 搜索内容
 * @param status 状态 0:'公开',1:'私有',2:'加密',
 */
export async function getTeamList(searchContent = '', status = 0) {
    const url = '/team/list';
    return await myAxios.get(url, {
        params: {
            searchText: searchContent,
            status
        }
    });
}

/**
 * 创建队伍
 * @param postData 队伍信息
 */
export async function createTeam(postData: any) {
    return await myAxios.post("/team/add", postData);
}

/**
 * 获取队伍
 * @param postData 队伍信息
 */
export async function getTeam(id: any) {
    return await myAxios.get('/team/get', {
        params: {
            id,
        }
    });
}

/**
 * 更新队伍
 * @param postData 队伍信息
 */
export async function updateTeam(postData: any) {
    return await myAxios.post("/team/update", postData);
}


/**
 * 加入队伍
 * @param postData 队伍信息
 */
export async function joinTeam(teamId: number,password:string) {
    return await myAxios.post('/team/join',{
        teamId:teamId,
        password:password,
    });
}

/**
 * 退出队伍
 * @param id 队伍id
 */
export async function quitTeam(id: number) {
    return await myAxios.post('/team/quit',{
        teamId:id
    });
}

/**
 * 解散队伍
 * @param id 队伍id
 */
export async function deleteTeam(id: number) {
    return await myAxios.post('/team/delete',{
        id
    });
}

/**
 * 获取登录用户创建的队伍
 * @param searchContent 过滤条件
 */
export async function getMyCreateTeam(searchContent = '') {
    return await myAxios.get('/team/list/my/create', {
        params: {
            searchText: searchContent
        }
    });
}

/**
 * 获取登录用户加入的队伍
 * @param searchContent 过滤条件
 */
export async function getMyJoinTeam(searchContent = '') {
    return myAxios.get('/team/list/my/join', {
        params: {
            searchText: searchContent
        }
    });
}
