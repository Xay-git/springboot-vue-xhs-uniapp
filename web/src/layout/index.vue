<template>
  <div :class="classObj" class="app-wrapper">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside" />
    <sidebar class="sidebar-container" />
    <div class="main-container">
      <div :class="{'fixed-header':fixedHeader}">
        <navbar />
        <tags-view />    <!-- 此处增加tag-->
      </div>
      <app-main />

      <div class="floating-component">
        <!-- 悬浮内容 -->
        <el-tooltip placement="left" effect="light" popper-class="too">
<!--          <div slot="content">-->
<!--            <el-button icon="el-icon-s-comment" class="butto" @click="handleQuestion">问题反馈 </el-button>-->
<!--            <br/>-->
<!--            <el-button icon="el-icon-question" class="butto" @click="handleHelp">帮助手册 </el-button>-->
<!--          </div>-->
          <el-button icon="el-icon-menu" circle class="but"></el-button>
        </el-tooltip>
      </div>
    </div>
  </div>
</template>

<script>
import { Navbar, Sidebar, AppMain,TagsView } from './components'
import ResizeMixin from './mixin/ResizeHandler'

export default {
  name: 'Layout',
  components: {
    Navbar,
    Sidebar,
    AppMain,
    TagsView
  },
  mixins: [ResizeMixin],
  computed: {
    sidebar() {
      return this.$store.state.app.sidebar
    },
    device() {
      return this.$store.state.app.device
    },
    fixedHeader() {
      return this.$store.state.settings.fixedHeader
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    }
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    }
  }
}
</script>

<style lang="scss" scoped>
  @import "~@/styles/mixin.scss";
  @import "~@/styles/variables.scss";

  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
    &.mobile.openSidebar{
      position: fixed;
      top: 0;
    }
  }
  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  .fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: calc(100% - #{$sideBarWidth});
    transition: width 0.28s;
  }

  .hideSidebar .fixed-header {
    width: calc(100% - 54px)
  }

  .mobile .fixed-header {
    width: 100%;
  }


  .floating-component {
    position: fixed; /* 固定位置 */
    bottom: 5%; /* 下边距 */
    right: 1%; /* 右边距 */
    padding: 0;
    border-radius: 50%; /* 圆角 */
    z-index: 10000; /* 设置 z-index 确保悬浮在顶层 */
  }
  .but {
    font-size: larger;
    color: rgb(0,119,216);
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2); /* 阴影 */
  }
  .butto {
    border: 0;
    padding: 10px;
    margin: 0;
  }

   .too.el-tooltip__popper.is-light {
     border: none !important;
     box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);
     padding: 5px
   }
  /* 修改箭头边框 这里方位是left，所以 x-placement^="left" 并且是设置 border-right-color 的颜色*/
  .too.el-tooltip__popper.is-light[x-placement^="left"] .popper__arrow {
    border-left-color: #eaeaea !important;
  }
  .too.el-tooltip__popper[x-placement^="left"] .popper__arrow {
    border-left-color: #eaeaea !important;
  }

</style>
