<template>
    <div id="notify">
        <h1>通知消息</h1>
        <div v-for="msg in msgList" >
            <van-notice-bar
                    wrapable
                    :scrollable="true"
                    mode="closeable"
                    :text="`${msg.sendId}: (${msg.content})`"
                    @close="onDeleteMsg(msg.sendId,msg.content)"
            />
        </div>
        <van-empty v-if="msgList?.length <1" description="没有新的消息"/>
    </div>
    <div id="message">
        <h1>发送消息</h1>
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
    </div>
</template>

<script setup lang="ts">

    import {useRoute, useRouter} from "vue-router";
    import {getMyJoinTeam} from "../../api/team";
    import {Toast} from "vant";
    import {addMsg, deleteMsg, getCurrentUserMsg} from "../../api/msg";
    import {onMounted, ref} from 'vue';
    import {checkResult} from "../../api/common";
    import {getCurrentUser} from "../../services/user";

    const router = useRouter()
    const route = useRoute()

    const id = route.query.id;
    const sendMsg = ref('')
    const msgList = ref()

    console.log(id)

    /**
     * 发送消息给其他用户
     */
    const onDeleteMsg = async (sendId:number,content:string) =>{
        console.log("deleteMsg:" + sendId + ' ' + content)
        const res = await deleteMsg(sendId,content);

        checkResult(res,'删除消息');
    }

    const onSend = async () =>{
        console.log("sendMsg:" + sendMsg.value)
        const res = await addMsg(id,sendMsg.value);

        checkResult(res,'发送消息');
    }

    onMounted(async () => {
        msgList.value = await getCurrentUserMsg();
        console.log(msgList)
    })
    
</script>

<style scoped>
    #notify {
        padding: 20px;
    }
    #message {
        padding: 20px;
    }
</style>
