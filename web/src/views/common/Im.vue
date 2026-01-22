<template>
  <div>
    <el-dialog
      title="客服消息"
      append-to-body
      top="15vh"
      width="60%"
      :visible.sync="dialogVisible"
      center
      @close="handleCancel"
    >
      <div class="el-dialog-div" style="height:60vh;">
        <lemon-imui
          width="100%"
          height="60vh"
          position="center"
          :user='this.user'
          ref="IMUI"
          @pull-messages='handlePullMessages'
          @send="handleSend"
          @change-contact="handleContactClick"
        />
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {getAuthorChatList,getAuthorChat,readAuthorMessage} from "@/api/business/chat/chat";
  import {isNotEmpty,error} from "@/utils";
  import webSocketManager from "@/api/websocket.js";

  export default {
    name: "Im",
    props: {
      sellList: Array,
    },
    data() {
      return {
        dialogVisible: false,
        temp:{},
        page:1,
        user:{id:8,displayName:'官方客服',avatar:'http://8.146.211.120:8080/upload/avatar/kefu.jpg'},
        contact:{}
      }
    },
    mounted(){
      webSocketManager.connect( process.env.VUE_APP_WEBSOCKET_API + '?authorId=8')

      //链接成功后获取总数
      webSocketManager.onConnect(res=>{
        this.getUnReadCount()
      })
    },
    methods: {
      open() {
        this.dialogVisible = true
        this.getAuthorList()
        if(isNotEmpty(this.contact.id)){
          this.$refs.IMUI.updateContact({
            id: this.contact.id,
            unread: 0,
          });
          this.readAuthorMessage(this.contact.id)
          const {IMUI} = this.$refs;
          IMUI.messageViewToBottom()
          this.getUnReadCount()
        }
      },
      getUnReadCount(){
        webSocketManager.sendMessage(JSON.stringify({handlerType:'8',authorId:'8'}))
      },
      readAuthorMessage(toContactId){
        webSocketManager.sendMessage(JSON.stringify({handlerType:'7',authorId:toContactId,loginId:'8'}))
      },
      handleContactClick(contact, instance){
        console.log('点击了消息')
        //将此人的消息设置为已读
        instance.updateContact({
          id: contact.id,
          unread: 0,
        });
        this.contact = contact
        contact.page = 1
        //读取当前用户信息
        this.readAuthorMessage(contact.id)
        //获取总未读消息
        this.getUnReadCount()
        const {IMUI} = this.$refs;
        IMUI.messageViewToBottom()
      },
      getAuthorList(){
        getAuthorChatList({authorId:8}).then(response=> {
          this.$nextTick(() => {
              const {IMUI} = this.$refs;
              const authorList = response.data;
              const contacts = [];
              for (let item of authorList) {
                let data = {
                  id: item.id,
                  displayName: item.displayName,
                  avatar: item.avatar,
                  index: item.index,
                  unread: item.unread,
                  //最近一条消息的内容，如果值为空，不会出现在“聊天”列表里面。
                  //lastContentRender 函数会将 file 消息转换为 '[文件]', image 消息转换为 '[图片]'，对 text 会将文字里的表情标识替换为img标签,
                  //最近一条消息的发送时间
                  lastSendTime: item.lastSendTime,
                }
                if(isNotEmpty(item.lastContent)){
                  data.lastContent = IMUI.lastContentRender({type: 'text', content: item.lastContent})
                }
                contacts.push(data)
              }
              IMUI.initContacts(contacts);
          })
        })
      },
      handleCancel(){
        this.dialogVisible = false
      },
      submit(){

      },
      handleSend(message, next, file) {
        console.log('发送了信息')
        message.handlerType = '6'
        console.log(JSON.stringify(message))
        webSocketManager.sendMessage(JSON.stringify(message))
        //执行到next消息会停止转圈，如果接口调用失败，可以修改消息的状态 next({status:'failed'});
        next();
      },
      handlePullMessages(contact, next) {
        if(this.contact.page>this.contact.totalPage){
          error('没有记录了')
          return
        }
        getAuthorChat({authorId:8,fromId:contact.id,limit:8,page:this.contact.page}).then(res=> {
          //倒序查询但是正序排列 时间早的在前面
          this.contact.totalPage = res.data.pages
          const messages = res.data.records
          //当前页数小于等于总页数
          if(this.contact.page<=res.data.pages){
            //等于1时执行页面滚动操作
            if(this.contact.page==res.data.pages){
              console.log('相等了')
              next(messages.reverse(),true);
            }else{
              next(messages.reverse(),false);
            }
            this.contact.page++
          }
        })
        //将第二个参数设为true，表示已到末尾，聊天窗口顶部会显示“暂无更多消息”，不然会一直转圈。
      },
    },
  }
</script>
<style scoped>
  /deep/ .el-dialog__headerbtn{
    top:10px;
  }
  /deep/  .el-dialog__header{
    padding: 5px 20px 5px;background: #F7F7F7;
  }
  /deep/  .el-dialog--center  .el-dialog__body{
    padding:0;
  }
</style>
