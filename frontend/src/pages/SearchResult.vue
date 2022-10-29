<template>
    <user-card-list :user-list="userList"/>
    <van-empty v-if ="!userList || userList.length < 1" description="搜索结果为空" />
</template>

<script setup lang="ts">
    import {useRoute} from "vue-router";
    import {onMounted, ref} from 'vue';
    import {searchTags} from "../api/user";
    import UserCardList from "../components/UserCardList.vue"
    import {UserType} from "../model/user";

    const route= useRoute()
    console.log(route)

    const {tags} =route.query
    
    const userList =ref();

    onMounted(async() => {
        console.log(tags)
        const userListData = await searchTags(tags);
        console.log(userListData)

        if(userListData){
            userListData.forEach((user: UserType) => {
                if (user.tags) {
                    user.tags = JSON.parse(user.tags);
                }
            })
            userList.value = userListData
        }
    })

</script>

<style scoped>

</style>
