<template>
    <!--van-button type="primary" @click="onResetUser">切换用户</van-button-->
    <van-button type="danger" @click="onQuit">退出</van-button>
</template>

<script setup lang="ts">

    import {useRouter} from "vue-router";
    import myAxios from "../plugins/myAxios";
    import {Toast} from "vant";

    const router = useRouter()

    const onQuit = async () => {
        const res = await myAxios.post('/user/logout', {})
        console.log('退出请求：', res);

        if (res.code === 0 && res.data) {
            Toast.success('退出成功');
            router.back();
        } else {
            Toast.fail('退出失败');
        }
    };


</script>

<style scoped>

</style>
