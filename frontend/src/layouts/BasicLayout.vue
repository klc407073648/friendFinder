<template>
    <van-nav-bar
            :title="title"
            left-arrow
            @click-left="onClickLeft"
    >
    </van-nav-bar>

    <div id="content">
        <router-view/>
    </div>

    <!--van-tabbar v-model="active" @change="onChange"-->
    <van-tabbar router @change="onChange" v-model="active" active-color="#ee0a24" inactive-color="#000">
        <van-tabbar-item to="/" :icon="curActiveName==='index'?'home-o':'home-o'" name="index">主页</van-tabbar-item>
        <van-tabbar-item to="/search" :icon="curActiveName==='search'?'search':'search'" name="search">发现</van-tabbar-item>
        <van-tabbar-item to="/message" :icon="curActiveName==='message'?'chat':'chat-o'" name="message">消息</van-tabbar-item>
        <van-tabbar-item to="/team" :icon="curActiveName==='team'?'friends':'friends-o'" name="team">队伍</van-tabbar-item>
        <van-tabbar-item to="/user" :icon="curActiveName==='user'?'manager':'manager-o'"  name="user">个人</van-tabbar-item>
    </van-tabbar>
</template>

<script setup lang="ts">
    import {ref} from "@vue/reactivity";
    import {useRoute,useRouter} from "vue-router";
    import routes from "../config/route";
    import {Toast} from "vant";

    const router= useRouter();
    const route= useRoute();
    const curActiveName = ref('index');
    const indexIcon = ref("home-o")


    const DEFAULT_TITLE = '伙伴匹配';
    const title = ref(DEFAULT_TITLE);

    //TODO,目前仅是点击高亮，不能呈现实底的图标转换
    const onChange = (index) => {
        //Toast(`标签 ${index}`);
        curActiveName.value =  index;
    };

    /**
     * 根据路由，切换标题
     */
    router.beforeEach( (to,from) =>{
        const toPath = to.path;
        const route = routes.find( (route) =>{
            return toPath == route.path;
        })

        title.value = route?.title??DEFAULT_TITLE;
    })

    const onClickLeft = () => {
        router.back()
    };


</script>

<style scoped>
    #content {
        padding-bottom: 50px;
    }
</style>

