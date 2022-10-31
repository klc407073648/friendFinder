<template>
    <van-form @submit="onSubmit">
        <van-cell-group inset>
            <van-field
                    v-model="editUser.currentValue"
                    :name="editUser.editKey"
                    :label="editUser.editName"
                    :placeholder="`请输入${editUser.editName}`"
            />
        </van-cell-group>
        <div style="margin: 16px;">
            <van-button round block type="primary" native-type="submit">
                提交
            </van-button>
        </div>
    </van-form>
</template>

<script setup lang="ts">

    import {useRoute,useRouter} from "vue-router";
    import {onMounted, ref} from "vue";
    import myAxios from "../../plugins/myAxios";
    import {Toast} from "vant";
    import {getCurrentUser} from "../../services/user";
    import {checkOperationResult} from "../../api/common";

    const router= useRouter()
    const route= useRoute()


    const editUser = ref({
        editKey:route.query.editKey,
        editName:route.query.editName,
        currentValue:route.query.currentValue,
    })
    console.log(route.query)

    const onSubmit = async () => {
        const currentUser = await getCurrentUser();

        const res = await myAxios.post('/user/update',{
            'id': currentUser.id,
            [editUser.value.editKey as string] : editUser.value.currentValue,
        })
        console.log('更新请求：', res);

        checkOperationResult(res,'用户信息修改',router);
    };
</script>

<style scoped>

</style>
