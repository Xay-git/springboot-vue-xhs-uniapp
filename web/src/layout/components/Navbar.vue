<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <img :src="require( '@/assets/sdz.jpg' )" class="user-avatar">
          <span style="cursor: pointer;">{{user.username}}({{user.deptName}})</span>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link to="/">
            <el-dropdown-item>
              首页
            </el-dropdown-item>
          </router-link>
<!--          <a target="_blank" href="https://panjiachen.gitee.io/vue-element-admin-site/zh/">-->
<!--            <el-dropdown-item>文档</el-dropdown-item>-->
<!--          </a>-->
<!--          <a target="_blank" href="https://github.com/iimeepo/vue-admin-template">-->
<!--            <el-dropdown-item>Github</el-dropdown-item>-->
<!--          </a>-->
          <el-dropdown-item  @click.native="updatePassword">
            <span style="display:block;">修改密码</span>
          </el-dropdown-item>
          <el-dropdown-item  @click.native="openUploadConfig">
            <span style="display:block;">存储模式配置</span>
          </el-dropdown-item>
          <el-dropdown-item  @click.native="openSmsConfig">
            <span style="display:block;">短信发送配置</span>
          </el-dropdown-item>
          <el-dropdown-item  @click.native="openSystemSetting">
            <span style="display:block;">系统设置</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">退出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <div style="position: absolute;left: 50%;line-height: 50px;font-size: 16px;">
      qq讨论群:{{qqGroup}} &nbsp;&nbsp;&nbsp;  wx:{{wechat}}
    </div>

    <div class="right-menu" style="margin-right: 20px;">
      <div class="top-icon" @click="openIm">

        <el-badge v-show="unReadCount>0" :value="unReadCount" :max="99" >
          客服消息
          <i class="el-icon-bell"></i>
        </el-badge>
        <template v-show="unReadCount==0">
          客服消息
          <i class="el-icon-bell"></i>
        </template>
      </div>
    </div>

    <UpdatePassword ref="updatePass"/>
    <Im ref="im" />
    <UploadConfig ref="uploadConfig" />
    <SmsConfig ref="smsConfig" />
    <SystemSetting ref="systemSetting" />


  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import UpdatePassword from '@/views/common/system/UpdatePassword'
import Im from "@/views/common/Im";
import UploadConfig from '@/components/UploadConfig';
import SmsConfig from '@/components/SmsConfig';
import SystemSetting from '@/views/system/setting/SystemSetting';
import {getUnReadCount} from "@/api/business/chat/chat";
import webSocketManager from "@/api/websocket";
import {isNotEmpty} from "@/utils";

export default {
  components: {
    UpdatePassword,
    Breadcrumb,
    Hamburger,
    Im,
    UploadConfig,
    SmsConfig,
    SystemSetting,
  },
  computed: {
    ...mapGetters([
      'user',
      'sidebar',
      'avatar'
    ])
  },
  data() {
    return {
      unReadCount:0,
      qqGroup: process.env.VUE_APP_QQ_GROUP,
      wechat: process.env.VUE_APP_WECHAT
    }
  },
  mounted() {
    // 设置收到消息回调函数
    webSocketManager.onMessage((data) => {
      console.log('我是Navbar的mounted')
      console.log(data.handlerType)
      if(data.handlerType == '6'){
        console.log(JSON.stringify(data.body))
        const {IMUI} = this.$refs.im.$refs;
        try {
          IMUI.appendMessage(data.body);
          IMUI.messageViewToBottom()
          const contactId = this.$refs.im.contact.id
          if(isNotEmpty(contactId)&&contactId==data.body.toContactId&&this.$refs.im.dialogVisible){
            console.log('当前在该用户聊天框 设置为已读')
            this.$refs.im.readAuthorMessage(contactId)
          }
        }catch (e) {
        }
        //请求总数
        this.$refs.im.getUnReadCount()
      }
      if(data.handlerType == '8'){
        this.unReadCount = data.body
      }
    });
  },
  methods: {
    updatePassword(){
      this.$refs.updatePass.open()
    },
    openIm(){
      this.$refs.im.open()
    },
    openUploadConfig(){
      this.$refs.uploadConfig.open()
    },
    openSmsConfig(){
      this.$refs.smsConfig.open()
    },
    openSystemSetting(){
      this.$refs.systemSetting.open()
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push('/login')

      //退出登陆后删除全部tagview 避免重新登陆低权限账号显示菜单
      await this.$store.dispatch('tagsView/delAllViews')
      sessionStorage.removeItem('tabViews')
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .top-icon{
    text-align: center;
    height: 50px;
    width: 150px;
    line-height: 50px;
    cursor: pointer;
    color: #606266;
    font-size: 18px;
    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .top-menu{
    margin-right: 25px;
    font-size: 14px;
  }

  //修改徽章位置
  .el-badge {
    ::v-deep .el-badge__content
    {
      margin-top:12px;
    }
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
