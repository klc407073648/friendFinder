<template>
    <van-cell center title="心动模式">
        <template #right-icon>
            <van-switch v-model="isMatchMode" size="24"/>
        </template>
    </van-cell>
    <van-field v-if="isMatchMode === true" name="stepper" label="匹配人数">
        <template #input>
            <van-stepper v-model="matchNumber" min="3" max="10"/>
        </template>
    </van-field>
    <van-field v-if="isMatchMode === false" name="stepper" label="匹配人数">
        <template #input>
            <van-stepper v-model="matchNumber" min="5" max="20"/>
        </template>
    </van-field>
    <user-card-list :user-list="currentPageUserList" :loading="loading"/>
    <van-empty v-if="!currentPageUserList || currentPageUserList.length < 1" description="数据为空"/>
    <van-pagination
            v-model="currentPage"
            :total-items="total_items"
            :items-per-page="items_per_page"
            @change="pageChange"
    />
    <!--TODO 分页-->
    <!--van-pagination v-model="currentPage" :page-count="12" mode="simple" /-->
</template>

<script setup lang="ts">
    import {useRoute} from "vue-router";
    import {onMounted, ref, watchEffect} from 'vue';
    import myAxios from "../plugins/myAxios";
    import {Toast} from 'vant';
    import UserCardList from "../components/UserCardList.vue"
    import {UserType} from "../model/user";
    import {getMatchUser,getRecommend} from "../api/user"
    import {getMyCreateTeam} from "../api/team";

    const isMatchMode = ref<boolean>(false);

    const route = useRoute()
    console.log(route)

    const {tags} = route.query

    const userList = ref();
    const loading = ref(true);
    //匹配人数
    const matchNumber = ref(0);
    /**
     *  当前页数，总共用户数、每页显示人数
     */
    const currentPage =ref(1);
    const total_items =ref(0);
    const items_per_page =ref(5);

    //当前显示用户内容，当前页号
    const currentPageUserList = ref();
    const currentPageNum = ref(1);

    /**
     * 更新当前页的数据
     */
    const pageChange = async () => {
        //计算对应页的数据
        currentPageUserList.value = userList.value.slice((currentPage.value-1)*(items_per_page.value), currentPage.value*(items_per_page.value))
        console.log(currentPage.value)
        console.log(currentPageUserList.value)
    }

    /**
     * 加载数据
     */
    const loadData = async () => {
        let userListData;
        loading.value = true;
        //心动模式，根据标签匹配
        if (isMatchMode.value) {
            userListData = await getMatchUser(matchNumber.value);
        } else {
            //普通模式，直接分页查询
            userListData = await getRecommend(currentPageNum.value,matchNumber.value);
        }

        if (userListData) {
            userListData.forEach( (user: UserType) => {
                if (user.tags) {
                    user.tags = JSON.parse(user.tags);
                }
            });
            userList.value = userListData;
            total_items.value = userList.value.length;
        }

        loading.value = false;
    }

    watchEffect(() => {
        console.log("current matchMode: " + isMatchMode.value);
        loadData();
    })

</script>

<style scoped>

</style>
