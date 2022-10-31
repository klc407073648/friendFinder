import {Toast} from "vant";
import {Router} from "vue-router";
import myAxios from "../plugins/myAxios";

const success = '成功'
const fail = '失败'

/**
 * 校验请求的结果信息
 * @param res  返回的结果
 * @param msg  提示前缀信息
 */
export const checkResult =(res:any,msg:string) =>{
    if(res?.code === 0){
        Toast.success(msg + success)
    }
    else{
        Toast.fail(msg + fail + (res.description?`, ${res.description}`:''))
    }
}

/**
 * 校验请求的结果信息且需要返回上一层页面,需要直接把对应页面的router对象传递过来
 * 适用于队伍创建、更新，用户修改和设置后返回上一级
 * @param res  返回的结果
 * @param msg  提示前缀信息
 */
export const checkOperationResult =(res:any,msg:string,router: Router) =>{
    if(res?.code === 0 && res.data){
        Toast.success(msg + success)
        router.back();
    }else{
        Toast.fail(msg + fail + (res.description?`${res.description}`:''));
    }
}
