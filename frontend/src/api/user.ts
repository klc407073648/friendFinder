import myAxios from "../plugins/myAxios";
import {Toast} from "vant";
import qs from 'qs';

/**
 * 获取心动模式下的匹配用户，按照相似度从高到低返回
 * @param num 匹配人数
 */
export async function getMatchUser(matchNumber: number) {
    const url = '/user/match';
    return await myAxios.get(url, {
        params: {
            num: matchNumber
        },
    })
        .then(function (response) {
            console.log(url +' succeed', response);
            return response?.data;
        })
        .catch(function (error) {
            console.error(url +' error', error);
            Toast.fail('请求失败')
        })
}

/**
 * 获取普通模式下的匹配用户
 * @param num 匹配人数
 */
export async function getRecommend(pageNum:number,matchNumber: number) {
    const url = '/user/recommend';
    return await myAxios.get(url, {
        params: {
            pageSize: matchNumber,
            pageNum: pageNum,
        },
    })
        .then(function (response) {
            console.log(url +' succeed', response);
            return response?.data?.records;
        })
        .catch(function (error) {
            console.error(url +' error', error);
            Toast.fail('请求失败')
        })
}

/**
 * 根据标签搜索
 * @param tags 标签
 */
export async function searchTags(tags:any) {
    const url = '/user/search/tags';
    return await myAxios.get(url, {
        params: {
            tagNameList: tags
        },
        paramsSerializer: params => {
            if(params)
                return qs.stringify(params, { indices: false })
        }
    })
        .then(function (response) {
            console.log(url +' succeed',response);
            return response?.data;
        })
        .catch(function (error) {
            console.error(url +' error',error);
            Toast.fail('请求失败')
        })
}

/**
 * 用户登录
 * @param account 用户账号
 * @param password 用户密码
 */
export async function userLogin(account:string,password:string) {
    return await myAxios.post('/user/login',{
        userAccount:account,
        userPassword:password,
    })
}

/**
 * 用户退出
 */
export async function userLogout() {
    return await myAxios.post('/user/logout', {});
}
