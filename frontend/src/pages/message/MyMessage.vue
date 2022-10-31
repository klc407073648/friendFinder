<template>
    <div id="message">
        <h1>消息内容</h1>
    </div>
    <van-cell-group inset v-if="id !=undefined">
        <van-field
                v-model="sendMsg"
                center="false"
                clearable="false"
                placeholder=""
                left-icon="chat-o"
        >
            <template #button>
                <van-button size="small" type="primary" @click="onSend()">发送</van-button>
            </template>
        </van-field>
    </van-cell-group>
    <van-empty v-if="id==undefined" description="未选择联系人"/>
</template>

<script setup lang="ts">

    import {useRoute, useRouter} from "vue-router";
    import {getMyJoinTeam} from "../../api/team";
    import {Toast} from "vant";
    import {addMsg} from "../../api/msg";
    import {ref} from 'vue';
    import {checkResult} from "../../api/common";

    const router = useRouter()
    const route = useRoute()

    const id = route.query.id;
    const sendMsg = ref('')

    console.log(id)

    /**
     * 发送消息给其他用户
     */
    const onSend = async () =>{
        console.log(sendMsg.value)
        const res = await addMsg(sendMsg.value,id);

        checkResult(res,'发送消息');
    }
    
</script>

<style scoped>
    #message {
        padding: 20px;
    }
</style>
