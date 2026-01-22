<template>
  <div>
    <el-dialog

      top="8vh"
      width="60%"
      :visible.sync="dialogVisible"
      center
      @close="handleCancel"
    >
      <div class="el-dialog-div">
        <el-form
          :rules="rules"
          ref="dataForm"
          :model="temp"
          label-position="right"
          label-width="120px"
          style="height: 90%;"
        >
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="笔记标题" prop="noteTitle" class="is-required">
                <el-input v-model="temp.noteTitle" placeholder="笔记标题" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="笔记分类" prop="noteCategory" class="is-required">
                <el-select v-model="temp.noteCategory" placeholder="请选择笔记分类">
                  <el-option
                    v-for="item in categoryOptions"
                    :key="item.categoryId"
                    :label="item.categoryName"
                    :value="item.categoryId">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="笔记类型" prop="noteType" class="is-required">
                <el-select v-model="temp.noteType" placeholder="请选择笔记类型">
                  <el-option
                    v-for="item in noteTypeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="作者名字" prop="authorName" class="is-required">
                <el-input v-model="temp.authorName" readonly placeholder="作者名字" />
              </el-form-item>
            </el-col>
          </el-row>

          <!-- 图文笔记上传 -->
          <template v-if="temp.noteType === 1 || temp.noteType === 3">
            <el-form-item label="图片" prop="imgs" class="is-required">
              <multi-upload v-model="temp.imgs" :limit="9" :max-size="fileMaxSize" :file-type="coverFileType" save-path="note" />
            </el-form-item>
            <el-form-item v-if="temp.imgs && temp.imgs.length > 0" label="选择封面图">
                <div class="image-selection-container">
                    <div
                        v-for="imgUrl in temp.imgs"
                        :key="imgUrl"
                        class="image-item"
                        :class="{ 'selected-cover': temp.firstPicture === imgUrl }"
                        @click="temp.firstPicture = imgUrl"
                    >
                        <img :src="imgUrl" class="thumbnail-image">
                        <div v-if="temp.firstPicture === imgUrl" class="cover-indicator">封面</div>
                    </div>
                </div>
            </el-form-item>
          </template>

          <!-- 视频笔记上传 -->
          <template v-if="temp.noteType === 2">
            <el-form-item label="视频文件" prop="videoUrl" class="is-required">
              <single-upload v-model="temp.videoUrl" :max-size="fileMaxSize" :file-type="videoFileType" save-path="video" />
            </el-form-item>
            <el-form-item label="视频封面" prop="firstPicture" class="is-required">
              <single-upload v-model="temp.firstPicture" :max-size="fileMaxSize" :file-type="coverFileType" save-path="note" />
            </el-form-item>
          </template>

          <!-- 音乐笔记上传 -->
          <template v-if="temp.noteType === 3">
            <el-form-item label="音乐文件" prop="musicUrl" class="is-required">
              <single-upload v-model="temp.musicUrl" :max-size="fileMaxSize" :file-type="musicFileType" save-path="music" />
            </el-form-item>
          </template>

          <el-form-item label="笔记内容" prop="noteContent" class="is-required">
            <el-input
              v-model="temp.noteContent"
              :rows="8"
              type="textarea"
              placeholder="请输入笔记内容"
            />
          </el-form-item>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="创建时间" prop="createTime" class="is-required">
                <el-date-picker
                  v-model="temp.createTime"
                  type="datetime"
                  placeholder="创建时间"
                  value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="修改时间" prop="updateTime" class="is-required">
                <el-date-picker
                  v-model="temp.updateTime"
                  type="datetime"
                  placeholder="修改时间"
                  value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="ip地址" prop="ipAddress" class="is-required">
                <el-input v-model="temp.ipAddress" placeholder="ip地址" :readonly="!!temp.noteId" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="真实ip地址" prop="ipRealAddress" class="is-required">
                <el-input v-model="temp.ipRealAddress" placeholder="真实ip地址" :readonly="!!temp.noteId" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="初始点赞数" prop="initUpCount" class="is-required">
                <el-input v-model="temp.initUpCount" placeholder="初始点赞数" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="初始收藏数" prop="initStarCount" class="is-required">
                <el-input v-model="temp.initStarCount" placeholder="初始收藏数" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item label="冻结状态" prop="noteStatus" class="is-required">
            <el-radio-group v-model="temp.noteStatus" @change="handleNoteStatusChange">
              <el-radio :label="0">正常</el-radio>
              <el-radio :label="1">冻结</el-radio>
            </el-radio-group>
          </el-form-item>

        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addNote, editNote } from "@/api/business/note/note";
import { getCategoryList } from "@/api/business/category/category";
import { setRequiredFields, success } from "@/utils";
import SingleUpload from '@/components/Upload/SingleUpload'
import MultiUpload from '@/components/Upload/MultiUpload'

const requiredFields = ['noteTitle', 'noteContent']

export default {
  name: "addNoteForm",
  components: {
    SingleUpload,
    MultiUpload
  },
  data() {
    const _this = this;
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      categoryOptions: [],
      noteTypeOptions: [
        { label: '图文', value: 1 },
        { label: '视频', value: 2 },
        { label: '音乐', value: 3 }
      ],
      videoUrl: '', // Video file URL (from SingleUpload)
      musicUrl: '', // Music file URL (from SingleUpload)
      fileId: '',
      fileMaxSize: 20, // 文件最大限制20MB
      coverFileType: ['image/png', 'image/jpeg', 'image/gif'],
      videoFileType: ['video/mp4', 'video/quicktime'],
      musicFileType: ['audio/mpeg', 'audio/wav'],
      temp: {
        noteId:'',
        noteTitle:'',
        noteContent:'',
        noteCategory:'',
        noteCategoryName:'',
        noteType: 1, // 默认为图文
        noteStatus: 0,
        authorId:'',
        authorAvatar:'',
        authorName:'',
        firstPicture:'',
        version:'',
        deleted:'',
        createTime:'',
        updateTime:'',
        ipAddress:'',
        ipRealAddress:'',
        upCount:'',
        starCount:'',
        initUpCount: 0,
        initStarCount: 0,
        imgs: [], // For graphic and music notes
        videoUrl: '',
        musicUrl: ''
      },
      toolbarConfig: {},
      editorConfig: {
        placeholder: '请输入内容...',
        MENU_CONF: {
          uploadImage: {
            server: process.env.VUE_APP_BASE_API + '/upload/wangeditor',
            fieldName: 'file',
            // 自定义上传参数
            meta: {
              fileSavePath: 'note'
            }
          }
        }
      },
      mode: 'default', // or 'simple'
    }
  },
  watch: {
    'temp.imgs': {
      handler(newVal) {
        // Check if the currently selected cover is still in the list of images.
        const isCoverStillPresent = newVal && newVal.includes(this.temp.firstPicture);

        // If the cover is not present (or was never set), and there are images in the list
        if (!isCoverStillPresent && newVal && newVal.length > 0) {
          // Set the first image of the list as the new cover.
          this.temp.firstPicture = newVal[0];
        }
        // If there are no images left, ensure the cover is cleared.
        else if (!newVal || newVal.length === 0) {
          this.temp.firstPicture = '';
        }
      },
      deep: true
    }
  },
  methods: {
    open(row) {
      // 1. 重置数据对象为其初始状态
      this.temp = this.$options.data().temp;
      // 2. 打开对话框
      this.dialogVisible = true;
      // 3. 在下一个DOM更新周期中，填充表单
      this.$nextTick(() => {
        // 清除先前使用中可能存在的验证信息
        this.$refs.dataForm.clearValidate();

        if (row && row.noteId) { // 编辑现有笔记
          this.temp.noteId = row.noteId || '';
          this.temp.noteTitle = row.noteTitle || '';
          this.temp.noteContent = row.noteContent || '';
          this.temp.noteCategory = row.noteCategory || '';
          this.temp.noteCategoryName = row.noteCategoryName || '';
          this.temp.noteType = row.noteType || 1;
          this.temp.authorId = row.authorId || '';
          this.temp.authorAvatar = row.authorAvatar || '';
          this.temp.authorName = row.authorName || '';
          this.temp.imgs = Array.isArray(row.imgList) ? row.imgList : [];
          this.temp.videoUrl = row.videoUrl || '';
          this.temp.musicUrl = row.musicUrl || '';
          this.temp.firstPicture = row.firstPicture || '';
          this.temp.version = row.version || '';
          this.temp.deleted = row.deleted || '';
          this.temp.createTime = row.createTime || '';
          this.temp.updateTime = row.updateTime || '';
          this.temp.ipAddress = row.ipAddress || '';
          this.temp.ipRealAddress = row.ipRealAddress || '';
          this.temp.upCount = row.upCount || 0;
          this.temp.isUp = row.isUp || false;
          this.temp.starCount = row.starCount || 0;
          this.temp.isStar = row.isStar || false;
          this.temp.noteStatus = row.noteStatus || 0;
          this.temp.viewCount = row.viewCount || 0;
        } else if (row) { // 新增笔记，带有作者信息
           this.temp.authorId = row.authorId || '';
           this.temp.authorName = row.authorName || '';
        }
      });
      // 4. 为表单加载辅助数据
      this.loadCategories();
    },
    loadCategories() {
      getCategoryList().then(response => {
        this.categoryOptions = response.data
      })
    },
    submit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.temp.noteCategory) {
            const category = this.categoryOptions.find(item => item.categoryId === this.temp.noteCategory);
            if (category) {
              this.temp.noteCategoryName = category.categoryName;
            }
          }

          const dataToSend = { ...this.temp };

          if (dataToSend.imgs && Array.isArray(dataToSend.imgs) && dataToSend.imgs.length > 0) {
            dataToSend.imgs = dataToSend.imgs.map(file => {
              if (file && typeof file === 'object' && file.response && file.response.code === 200) {
                return file.response.data.fileId;
              } else if (typeof file === 'string' && file.startsWith('http')) {
                const parts = file.split('/');
                return parts[parts.length - 1].split('.')[0];
              } else {
                return file;
              }
            }).filter(Boolean);
          }

          if (typeof dataToSend.firstPicture === 'object' && dataToSend.firstPicture.url) {
            dataToSend.firstPicture = dataToSend.firstPicture.url;
          } else if (dataToSend.firstPicture && dataToSend.firstPicture.response && dataToSend.firstPicture.response.code === 200) {
            dataToSend.firstPicture = dataToSend.firstPicture.response.data.filePath;
          }

          if (dataToSend.noteType === 1 || dataToSend.noteType === 3) {
              if (this.temp.imgs && this.temp.imgs.length > 0 && !dataToSend.firstPicture) {
                  const firstImage = this.temp.imgs[0];
                  if (typeof firstImage === 'string' && firstImage.startsWith('http')) {
                      dataToSend.firstPicture = firstImage;
                  } else if (firstImage.response && firstImage.response.data && firstImage.response.data.filePath) {
                      dataToSend.firstPicture = firstImage.response.data.filePath;
                  }
              }
          }

          if (dataToSend.noteType === 1) {
            if (!dataToSend.imgs || dataToSend.imgs.length === 0) {
              this.$message.error('请上传图片！');
              return;
            }
            if (!dataToSend.firstPicture) {
              this.$message.error('请选择或上传一张封面！');
              return;
            }
            if (!this.temp.imgs.includes(this.temp.firstPicture)) {
                this.$message.error('所选封面图不在已上传图片列表中！');
                return;
            }
            dataToSend.videoUrl = null;
            dataToSend.musicUrl = null;
          } else if (dataToSend.noteType === 2) {
            if (!dataToSend.videoUrl) {
              this.$message.error('请上传视频文件！');
              return;
            }
            if (!dataToSend.firstPicture) {
              this.$message.error('请上传视频封面！');
              return;
            }
            dataToSend.imgs = [];
            dataToSend.musicUrl = null;
          } else if (dataToSend.noteType === 3) {
            if (!dataToSend.musicUrl) {
              this.$message.error('请上传音乐文件！');
              return;
            }
            dataToSend.videoUrl = null;

            if (dataToSend.imgs && dataToSend.imgs.length > 0) {
              if (!dataToSend.firstPicture) {
                  this.$message.error('请选择封面图片！');
                  return;
              }
              if (!this.temp.imgs.includes(this.temp.firstPicture)) {
                this.$message.error('所选封面图不在已上传图片列表中！');
                return;
              }
            }
          }

          let request;
          if (dataToSend.noteId) {
            request = editNote(dataToSend);
          } else {
            request = addNote(dataToSend);
          }

          request.then(response => {
            this.handleCancel();
            this.$emit('ok', response.data);
            success(dataToSend.noteId ? '修改成功' : '发布成功');
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleCancel() {
      this.temp = this.$options.data().temp;
      this.videoUrl = '';
      this.musicUrl = '';
      this.fileId = '';
      this.$refs['dataForm'].resetFields()
      this.dialogVisible = false;
    },
    handleNoteStatusChange(newVal) {
      console.log('noteStatus changed via @change:', newVal);
    }
  },
  beforeDestroy() {
  },
}
</script>

<style src="@wangeditor/editor/dist/css/style.css"></style>
<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.upload-tip {
  font-size: 12px;
  color: #606266;
  margin-top: 5px;
}

.image-selection-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.image-item {
  position: relative;
  width: 100px;
  height: 100px;
  cursor: pointer;
  border: 2px solid transparent;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

.image-item:hover {
  border-color: #409EFF;
}

.selected-cover {
  border-color: red !important;
}

.thumbnail-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.cover-indicator {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  background-color: rgba(255, 0, 0, 0.7);
  color: white;
  text-align: center;
  font-size: 12px;
  padding: 2px 0;
}
</style>
