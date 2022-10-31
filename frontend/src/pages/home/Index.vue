<template>
    <van-cell center :title="isMatchMode===true?'心动模式':'普通模式'">
        <template #right-icon>
            <van-switch v-model="isMatchMode" size="24"/>
        </template>
    </van-cell>
    <!-- v-if="isMatchMode === true" 没必要区别普通和心动模式匹配人数不同的情况 -->
    <van-field name="stepper" label="匹配人数">
        <template #input>
            <van-stepper v-model="matchNumber" min="5" max="30"/>
        </template>
    </van-field>
    <div>
    <user-card-list :user-list="currentPageUserList" :loading="loading"/>
    <van-empty v-if="!currentPageUserList || currentPageUserList.length < 1" description="数据为空"/>
    <van-pagination
            id="pagination"
            v-model="currentPage"
            :total-items="total_items"
            :items-per-page="items_per_page"
            @change="pageChange"
    />
    </div>
</template>

<script setup lang="ts">
    import {useRoute} from "vue-router";
    import {onMounted, ref, watchEffect} from 'vue';
    import myAxios from "../../plugins/myAxios";
    import {Toast} from 'vant';
    import UserCardList from "../../components/UserCardList.vue"
    import {UserType} from "../../model/user";
    import {getMatchUser,getRecommend} from "../../api/user"
    import {getMyCreateTeam} from "../../api/team";

    const route = useRoute()
    console.log(route)

    const {tags} = route.query

    const userList = ref();
    const loading = ref(true);
    const isMatchMode = ref<boolean>(false);
    const matchNumber = ref(1);
    /**
     *  当前页数，总共用户数、每页显示人数
     */
    const currentPage =ref(1);
    const total_items =ref(1);
    const items_per_page =ref(5);

    /**
     *  当前显示用户内容，当前页号
     */
    const currentPageUserList = ref();
    const currentPageNum = ref(1);

    /**
     * 更新当前页的数据
     */
    const pageChange = async () => {
        //计算对应页的数据
        currentPageUserList.value = userList.value.slice((currentPage.value-1)*(items_per_page.value), currentPage.value*(items_per_page.value))
        console.log("currentPage.value:" + currentPage.value)
        console.log("currentPageUserList.value:" +currentPageUserList.value)
    }

    /**
     * 加载数据
     */
    const loadData = async () => {
        let userListData;
        loading.value = true;
        //心动模式，根据标签匹配
        console.log('matchNumber.value:'+matchNumber.value)
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
            console.log("total_items.value:" +total_items.value)
        }
        pageChange()

        loading.value = false;
    }

    watchEffect(() => {
        currentPageUserList.value = '';
        currentPage.value = 1;
        console.log("current matchMode: " + isMatchMode.value);
        loadData();
    })

</script>

<style scoped>
    #pagination {
        position: fixed;
        width: 100%;
        bottom: 60px;
    }
</style>
