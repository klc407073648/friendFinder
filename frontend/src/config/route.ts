import Index from "../pages/home/Index.vue";

import Team from "../pages/team/Team.vue";
import TeamAdd from "../pages/team/TeamAdd.vue";
import TeamUpdate from "../pages/team/TeamUpdate.vue";

import Search from "../pages/search/Search.vue";
import SearchResult from "../pages/search/SearchResult.vue";

import User from "../pages/user/User.vue";
import UserEdit from "../pages/user/UserEdit.vue";
import UserSet from "../pages/user/UserSet.vue";
import UserUpdate from "../pages/user/UserUpdate.vue";
import UserTeamJoin from "../pages/user/UserTeamJoin.vue";
import UserTeamCreate from "../pages/user/UserTeamCreate.vue";

import UserLogin from "../pages/UserLogin.vue";
import System from "../pages/System.vue";
import MyMessage from "../pages/message/MyMessage.vue";

const routes = [
    { path: '/', component: Index },
    { path: '/team', title: '找队伍', component: Team },
    { path: '/team/add', title: '创建队伍', component: TeamAdd },
    { path: '/team/update', title: '更新队伍', component: TeamUpdate },
    { path: '/user', title: '个人信息', component: User },
    { path: '/user/set', title: '设置', component: UserSet },
    { path: '/search', title: '找伙伴', component: Search },
    { path: '/user/list',title: '用户列表',  component: SearchResult },
    { path: '/user/edit/',title: '编辑信息',  component: UserEdit },
    { path: '/user/login/',title: '登录', component: UserLogin },
    { path: '/user/update', title: '更新信息', component: UserUpdate },
    { path: '/user/team/join', title: '加入队伍', component: UserTeamJoin },
    { path: '/user/team/create', title: '创建队伍', component: UserTeamCreate },
    { path: '/system', title: '关于系统', component: System },
    { path: '/message', title: '消息', component: MyMessage },
]

export  default  routes;
