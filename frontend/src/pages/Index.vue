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

    const isMatchMode = ref<boolean>(false);

    const route = useRoute()
    console.log(route)

    const {tags} = route.query

    const userList = ref();
    const loading = ref(true);
    const matchNumber = ref();
    const currentPage =ref(1);
    const total_items =ref(0);
    const items_per_page =ref(5);
    const currentPageUserList = ref();

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
    const loadDate = async () => {
        let userListData;
        loading.value = true;
        //心动模式，根据标签匹配
        if (isMatchMode.value) {
            userListData = await myAxios.get('/user/match', {
                params: {
                    num: matchNumber.value
                },
            })
                .then(function (response) {
                    console.log('/user/match succeed', response);
                    return response?.data;
                })
                .catch(function (error) {
                    console.error('/user/match error', error);
                    Toast.fail('请求失败')
                })
        } else {
            //普通模式，直接分页查询
            userListData = await myAxios.get('/user/recommend', {
                params: {
                    pageSize: matchNumber.value,
                    pageNum: 1
                },
            })
                .then(function (response) {
                    console.log('/user/recommend succeed', response);
                    return response?.data?.records;
                })
                .catch(function (error) {
                    console.error('/user/recommend error', error);
                    Toast.fail('请求失败')
                })
        }
        loading.value = false;

        if (userListData) {
            userListData.forEach((user: UserType) => {
                if (user.tags) {
                    user.tags = JSON.parse(user.tags);
                }
            })
            userList.value = userListData
            total_items.value = userList.value.length
        }
    }

    watchEffect(() => {
        console.log("current matchMode: " + isMatchMode.value);
        loadDate();
    })

</script>

<style scoped>

</style>
