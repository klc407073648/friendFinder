<template>
    <van-skeleton title avatar :row="3" :loading="props.loading" v-for="user in props.userList">
        <van-card
                :desc="user.profile"
                :title="`${user.username} (${user.planetCode})`"
                :thumb="user.avatarUrl"
        >
            <template #tags>
                <van-tag plain type="danger" v-for="tag in user.tags" style="margin-right: 8px;margin-top: 8px">
                    {{tag}}
                </van-tag>
            </template>
            <template #footer>
                <van-button size="mini" @click="ContactMe(user.id)">联系我</van-button>
            </template>
        </van-card>
    </van-skeleton>
</template>

<script setup lang="ts">
    import {UserType} from "../model/user";
    import {useRouter} from "vue-router";

    interface UserCardListProps {
        loading:boolean;
        userList:UserType[];
    }

    const router =useRouter();

    const props = withDefaults(defineProps<UserCardListProps>(),{
        // @ts-ignore
        userList: [] as UserType[],
    });

    const ContactMe = ( id:number) => {
        console.log("ContactMe id:" + id);
        router.push({
            path: '/message',
            query: {
                id,
            }
        })
    }
</script>

<style scoped>

</style>
