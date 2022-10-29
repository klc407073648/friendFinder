<template>
    <div id="function">
        <h1>功能介绍</h1>
        <p>一个帮助大家找寻志同道合的伙伴的移动端网站（APP 风格），包括主页、发现、消息、队伍、个人五大模块，涵盖了用户（注册、登录、退出)、账号信息更新、按标签搜索用户、推荐相似用户、组队（创建、加入、解散、修改）、消息传递等功能。
        </p>
    </div>
    <div id="version" >
        <h1>版本信息</h1>
        {{`${currentVesion}`}}
    </div>
    <div id="advice" >
        <h1>意见反馈</h1>
        <van-cell-group inset >
            <van-field
                    v-model="message"
                    rows="5"
                    size="large"
                    type="textarea"
                    maxlength="100"
                    placeholder="请输入您对本系统的意见"
                    show-word-limit
            />
        </van-cell-group>
        <van-button class="advice-submit-button" size="small" type="primary" @click="onSubmit">提交</van-button>
    </div>
</template>

<script setup lang="ts">

    import {useRouter} from "vue-router";
    import {version} from "../constants/globalVar"

    const router = useRouter()

    const currentVesion = version;

    const onSubmit = async () => {
        //通过接口记录，获取当前userid，然后把意见反馈到后台的 意见信息表
    };


</script>

<style scoped>
    #function {
        padding: 20px;
    }
    #version {
        padding: 20px;
    }
    #advice {
        padding: 20px;
    }
</style>
