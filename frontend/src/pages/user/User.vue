<template>
    <template v-if="user">
        <van-cell title="当前用户" :value="user?.username"/>
        <van-cell title="修改信息" is-link to="/user/update"/>
        <van-cell title="我创建的队伍" is-link to="/user/team/create"/>
        <van-cell title="我加入的队伍" is-link to="/user/team/join"/>
        <van-cell title="设置" is-link to="/user/set"/>
    </template>
</template>

<script setup lang="ts">
    import {useRouter} from "vue-router";
    import {onMounted, ref} from "vue";
    import {getCurrentUser} from "../../services/user";

    const router = useRouter()
    const toEdit = (editKey: string, editName: string, currentValue: string) => {
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
    onMounted(async () => {
        user.value = await getCurrentUser();
    })

</script>

<style scoped>

</style>
