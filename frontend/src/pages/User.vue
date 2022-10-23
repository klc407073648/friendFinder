<template>
    <template v-if="user">
        <van-cell title="当前用户" :value="user?.username" />
        <van-cell title="修改信息" is-link to="/user/update" />
        <van-cell title="我创建的队伍" is-link to="/user/team/create" />
        <van-cell title="我加入的队伍" is-link to="/user/team/join" />
    </template>
</template>

<script setup lang="ts">
 import {useRouter} from "vue-router";
 import {onMounted} from "vue";
 import {Toast} from "vant";
 import myAxios from "../plugins/myAxios";
 import {ref} from "vue";
 import {getCurrentUser} from "../services/user";

 // const user = {
 //        id:1,
 //        username: "kkk",
 //        userAccount : "kkk",
 //        avatarUrl: "https://fastly.jsdelivr.net/npm/@vant/assets/logo.png",
 //        gender: '男',
 //        phone: '123456',
 //        email: '123456@qq.com',
 //        createTime: new Date(),
 //        isDelete: '0',
 //        userRole: '1',
 //        planetCode: '1234',
 // };

 const router =useRouter()
 const toEdit = ( editKey:string ,editName:string,currentValue:string) => {
     router.push({
         path: '/user/edit',
         query: {
             editKey,
             editName,
             currentValue,
         }
     })
 }
const user = ref()
 onMounted(async() => {
     user.value = await  getCurrentUser();
 })

</script>

<style scoped>

</style>
