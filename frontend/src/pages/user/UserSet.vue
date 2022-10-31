<template>
    <!--van-button type="primary" @click="onResetUser">切换用户</van-button-->
    <van-cell title="关于伙伴匹配系统" is-link to="/system"/>
    <div style="padding: 12px">
        <van-button block type="primary" @click="onResetUser">切换账号</van-button>
    </div>
    <div style="padding: 12px">
        <van-button block type="danger" @click="onQuit">退出</van-button>
    </div>
</template>

<script setup lang="ts">

    import {useRouter} from "vue-router";
    import myAxios from "../../plugins/myAxios";
    import {Toast} from "vant";
    import {userLogout} from "../../api/user";
    import {checkOperationResult} from "../../api/common";

    const router = useRouter()

    const onResetUser = () => {
        window.location.href = '/user/login';
    };

    const onQuit = async () => {
        const res = await userLogout();
        console.log('退出请求：', res);

        checkOperationResult(res,'用户退出',router);
    };


</script>

<style scoped>

</style>
