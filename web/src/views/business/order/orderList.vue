<template>
  <div class="app-container">
    <!-- 搜索工具栏 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.orderNo"
        placeholder="请输入订单号"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-input
        v-model="listQuery.authorName"
        placeholder="请输入用户名"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-select
        v-model="listQuery.orderStatus"
        placeholder="订单状态"
        clearable
        style="width: 150px"
        class="filter-item"
      >
        <el-option label="全部状态" value="" />
        <el-option label="待付款" :value="0" />
        <el-option label="已付款" :value="1" />
        <el-option label="已发货" :value="2" />
        <el-option label="已完成" :value="3" />
        <el-option label="已取消" :value="4" />
      </el-select>
      <el-button
        v-waves
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >
        搜索
      </el-button>
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-refresh"
        @click="resetSearch"
      >
        重置
      </el-button>
    </div>

    <!-- 订单列表 -->
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="序号" type="index" width="60" align="center" />
      
      <el-table-column label="订单号" width="180" align="center">
        <template slot-scope="{row}">
          <span>{{ row.orderNo }}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="用户信息" width="150" align="center">
        <template slot-scope="{row}">
          <div>
            <div>{{ row.authorName || '未知用户' }}</div>
            <div style="color: #666; font-size: 12px;">ID: {{ row.authorId }}</div>
          </div>
        </template>
      </el-table-column>
      
      <el-table-column label="收货信息" width="200" align="center">
        <template slot-scope="{row}">
          <div v-if="row.receiverName">
            <div>{{ row.receiverName }}</div>
            <div style="color: #666; font-size: 12px;">{{ row.receiverPhone }}</div>
            <div style="color: #999; font-size: 11px;">{{ row.receiverAddress }}</div>
          </div>
          <div v-else style="color: #999;">暂无收货信息</div>
        </template>
      </el-table-column>
      
      <el-table-column label="商品信息" width="300" align="center">
        <template slot-scope="{row}">
          <div v-if="row.items && row.items.length > 0" class="order-products">
            <div v-for="item in row.items" :key="item.itemId" class="product-item">
              <div class="product-image">
                <img 
                  :src="item.productImage || '/static/img/demo1.jpg'" 
                  :alt="item.productName"
                  @error="handleImageError"
                  style="width: 100%; height: 100%; object-fit: cover; border-radius: 4px;"
                />
              </div>
              <div class="product-info">
                <div class="product-name">{{ item.productName || '商品名称' }}</div>
                <div class="product-price">¥{{ item.productPrice }} x{{ item.quantity }}</div>
              </div>
            </div>
          </div>
          <div v-else style="color: #999;">暂无商品信息</div>
        </template>
      </el-table-column>
      
      <el-table-column label="订单金额" width="120" align="center">
        <template slot-scope="{row}">
          <span style="color: #ff4757; font-weight: bold;">¥{{ row.totalAmount }}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="订单状态" width="100" align="center">
        <template slot-scope="{row}">
          <el-tag :type="getStatusType(row.orderStatus)">{{ getStatusText(row.orderStatus) }}</el-tag>
        </template>
      </el-table-column>
      
      <el-table-column label="快递单号" width="150" align="center">
        <template slot-scope="{row}">
          <div v-if="row.trackingNumber && (row.orderStatus >= 2 || row.orderStatus === 4 || row.orderStatus === 5 || row.orderStatus === 6)">
            <div>{{ row.trackingNumber }}</div>
            <el-button v-if="row.orderStatus === 2" type="text" size="mini" @click="handleEditTracking(row)">修改</el-button>
          </div>
          <div v-else-if="row.orderStatus >= 2 || row.orderStatus === 4 || row.orderStatus === 5 || row.orderStatus === 6" style="color: #999;">暂无单号</div>
          <div v-else style="color: #999;">-</div>
        </template>
      </el-table-column>
      
      <el-table-column label="下单时间" width="160" align="center">
        <template slot-scope="{row}">
          <div>
            <div>{{ formatDate(row.createTime) }}</div>
            <div style="font-size: 12px; color: #666;">{{ formatTime(row.createTime) }}</div>
          </div>
        </template>
      </el-table-column>
      
      <el-table-column label="操作" align="center" width="280" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleView(row)">
            查看
          </el-button>
          <el-button
            v-if="row.orderStatus === 1"
            type="success"
            size="mini"
            @click="handleShip(row)"
          >
            发货
          </el-button>
          <el-button
            v-if="row.orderStatus === 2"
            type="warning"
            size="mini"
            @click="handleComplete(row)"
          >
            完成
          </el-button>
          <el-button
            v-if="row.orderStatus === 0"
            type="info"
            size="mini"
            @click="handlePay(row)"
          >
            支付
          </el-button>
          <el-button
            v-if="row.orderStatus < 2"
            type="danger"
            size="mini"
            @click="handleCancel(row)"
          >
            取消
          </el-button>
          <el-button
            v-if="row.orderStatus === 5"
            type="success"
            size="mini"
            @click="handleConfirmRefund(row)"
          >
            确认退款
          </el-button>
          <el-button
            v-if="row.orderStatus === 5"
            type="danger"
            size="mini"
            @click="handleRejectRefund(row)"
          >
            拒绝退款
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="listQuery.current"
      :limit.sync="listQuery.size"
      @pagination="getList"
    />

    <!-- 订单详情弹窗 -->
    <el-dialog title="订单详情" :visible.sync="dialogVisible" width="800px">
      <div v-if="currentOrder">
        <div class="order-info">
          <div class="info-row">
            <span class="info-label">订单号：</span>
            <span class="info-value">{{ currentOrder.orderNo }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">用户名：</span>
            <span class="info-value">{{ currentOrder.authorName }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">订单状态：</span>
            <span class="info-value">
              <el-tag :type="getStatusType(currentOrder.orderStatus)">{{ getStatusText(currentOrder.orderStatus) }}</el-tag>
            </span>
          </div>
          <div class="info-row">
            <span class="info-label">订单金额：</span>
            <span class="info-value" style="color: #ff4757; font-weight: bold;">¥{{ currentOrder.totalAmount }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">下单时间：</span>
            <span class="info-value">{{ formatDateTime(currentOrder.createTime) }}</span>
          </div>
          <div v-if="currentOrder.payTime" class="info-row">
            <span class="info-label">支付时间：</span>
            <span class="info-value">{{ formatDateTime(currentOrder.payTime) }}</span>
          </div>
          <div v-if="currentOrder.shipTime" class="info-row">
            <span class="info-label">发货时间：</span>
            <span class="info-value">{{ formatDateTime(currentOrder.shipTime) }}</span>
          </div>
          <div v-if="currentOrder.trackingNumber" class="info-row">
            <span class="info-label">快递单号：</span>
            <span class="info-value">
              {{ currentOrder.trackingNumber }}
              <el-button v-if="currentOrder.orderStatus === 2" type="text" size="mini" @click="handleEditTracking(currentOrder)">修改</el-button>
            </span>
          </div>
          <div v-if="currentOrder.completeTime" class="info-row">
            <span class="info-label">完成时间：</span>
            <span class="info-value">{{ formatDateTime(currentOrder.completeTime) }}</span>
          </div>
          <div v-if="currentOrder.cancelTime" class="info-row">
            <span class="info-label">取消时间：</span>
            <span class="info-value">{{ formatDateTime(currentOrder.cancelTime) }}</span>
          </div>
          <div v-if="currentOrder.cancelReason" class="info-row">
            <span class="info-label">取消原因：</span>
            <span class="info-value">{{ currentOrder.cancelReason }}</span>
          </div>
          <!-- 退款信息 (仅在状态为5、6时显示) -->
          <div v-if="currentOrder.orderStatus === 5 || currentOrder.orderStatus === 6">
            <div v-if="currentOrder.refundApplyTime" class="info-row">
              <span class="info-label">退款申请时间：</span>
              <span class="info-value">{{ formatDateTime(currentOrder.refundApplyTime) }}</span>
            </div>
            <div v-if="currentOrder.refundProcessTime" class="info-row">
              <span class="info-label">退款处理时间：</span>
              <span class="info-value">{{ formatDateTime(currentOrder.refundProcessTime) }}</span>
            </div>
            <div v-if="currentOrder.refundAmount" class="info-row">
              <span class="info-label">退款金额：</span>
              <span class="info-value" style="color: #ff4757; font-weight: bold;">¥{{ currentOrder.refundAmount }}</span>
            </div>
            <div v-if="currentOrder.refundReason" class="info-row">
              <span class="info-label">退款原因：</span>
              <span class="info-value">{{ currentOrder.refundReason }}</span>
            </div>
            <div v-if="currentOrder.refundProcessRemark" class="info-row">
              <span class="info-label">退款处理备注：</span>
              <span class="info-value">{{ currentOrder.refundProcessRemark }}</span>
            </div>
            <div v-if="currentOrder.refundRejectReason" class="info-row">
              <span class="info-label">退款拒绝原因：</span>
              <span class="info-value">{{ currentOrder.refundRejectReason }}</span>
            </div>
          </div>
          
          <!-- 评价信息 (仅在状态为7时显示) -->
          <div v-if="currentOrder.orderStatus === 7 && currentOrder.reviews && currentOrder.reviews.length > 0">
            <h4 style="margin-top: 20px; margin-bottom: 10px;">评价信息</h4>
            <div v-for="review in currentOrder.reviews" :key="review.reviewId" class="review-item" style="border: 1px solid #e4e7ed; border-radius: 4px; padding: 15px; margin-bottom: 10px;">
              <div class="info-row">
                <span class="info-label">商品名称：</span>
                <span class="info-value">{{ review.productName }}</span>
              </div>
              <div class="info-row">
                <span class="info-label">评分：</span>
                <span class="info-value">
                  <el-rate v-model="review.rating" disabled show-score text-color="#ff9900" />
                </span>
              </div>
              <div v-if="review.content" class="info-row">
                <span class="info-label">评价内容：</span>
                <span class="info-value">{{ review.content }}</span>
              </div>
              <div v-if="review.imgUrl" class="info-row">
                <span class="info-label">评价图片：</span>
                <span class="info-value">
                  <img :src="review.imgUrl" alt="评价图片" style="max-width: 200px; max-height: 200px; border-radius: 4px;" />
                </span>
              </div>
              <div class="info-row">
                <span class="info-label">评价时间：</span>
                <span class="info-value">{{ formatDateTime(review.createTime) }}</span>
              </div>
            </div>
          </div>
          <div v-if="currentOrder.remark" class="info-row">
            <span class="info-label">备注：</span>
            <span class="info-value">{{ currentOrder.remark }}</span>
          </div>
        </div>
        
        <!-- 收货信息 -->
        <div v-if="currentOrder.receiverName" style="margin-top: 20px;">
          <h4>收货信息</h4>
          <div class="info-row">
            <span class="info-label">收货人：</span>
            <span class="info-value">{{ currentOrder.receiverName }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">联系电话：</span>
            <span class="info-value">{{ currentOrder.receiverPhone }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">收货地址：</span>
            <span class="info-value">{{ currentOrder.receiverAddress }}</span>
          </div>
        </div>
        
        <!-- 商品信息 -->
        <div v-if="currentOrder.items && currentOrder.items.length > 0" style="margin-top: 20px;">
          <h4>商品信息</h4>
          <el-table :data="currentOrder.items" border>
            <el-table-column prop="productName" label="商品名称" />
            <el-table-column prop="productPrice" label="单价" width="100">
              <template slot-scope="scope">
                ¥{{ scope.row.productPrice }}
              </template>
            </el-table-column>
            <el-table-column prop="quantity" label="数量" width="80" />
            <el-table-column label="小计" width="100">
              <template slot-scope="scope">
                ¥{{ (scope.row.productPrice * scope.row.quantity).toFixed(2) }}
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </el-dialog>

    <!-- 发货弹窗 -->
    <el-dialog title="订单发货" :visible.sync="shipDialogVisible" width="500px">
      <el-form ref="shipForm" :model="shipForm" label-width="100px">
        <el-form-item label="快递单号" prop="trackingNumber">
          <el-input
            v-model="shipForm.trackingNumber"
            placeholder="请输入快递单号"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="shipDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmShip">确定发货</el-button>
      </div>
    </el-dialog>

    <!-- 修改快递单号弹窗 -->
    <el-dialog title="修改快递单号" :visible.sync="editTrackingDialogVisible" width="500px">
      <el-form ref="editTrackingForm" :model="editTrackingForm" label-width="100px">
        <el-form-item label="快递单号" prop="trackingNumber">
          <el-input
            v-model="editTrackingForm.trackingNumber"
            placeholder="请输入快递单号"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editTrackingDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmEditTracking">确定修改</el-button>
      </div>
    </el-dialog>

    <!-- 取消订单弹窗 -->
    <el-dialog title="取消订单" :visible.sync="cancelDialogVisible" width="500px">
      <el-form ref="cancelForm" :model="cancelForm" label-width="100px">
        <el-form-item label="取消原因" prop="cancelReason">
          <el-input
            v-model="cancelForm.cancelReason"
            type="textarea"
            :rows="3"
            placeholder="请输入取消原因"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmCancel">确定</el-button>
      </div>
    </el-dialog>

    <!-- 确认退款弹窗 -->
    <el-dialog title="确认退款" :visible.sync="refundDialogVisible" width="500px">
      <el-form ref="refundForm" :model="refundForm" label-width="100px">
        <el-form-item label="退货单号" prop="returnTrackingNumber" v-if="refundForm.returnTrackingNumber">
          <el-input
            v-model="refundForm.returnTrackingNumber"
            readonly
            placeholder="暂无退货单号"
            style="background-color: #f5f7fa;"
          />
        </el-form-item>
        <el-form-item label="用户退款原因" prop="refundReason">
          <el-input
            v-model="refundForm.refundReason"
            type="textarea"
            :rows="3"
            readonly
            placeholder="暂无退款原因"
            style="background-color: #f5f7fa;"
          />
        </el-form-item>
        <el-form-item label="退款金额" prop="refundAmount">
          <el-input
            v-model="refundForm.refundAmount"
            placeholder="请输入退款金额"
            type="number"
            step="0.01"
          >
            <template slot="prepend">¥</template>
          </el-input>
        </el-form-item>
        <el-form-item label="处理备注" prop="refundProcessRemark">
          <el-input
            v-model="refundForm.refundProcessRemark"
            type="textarea"
            :rows="2"
            placeholder="请输入处理备注（可选）"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="refundDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmRefund">确认退款</el-button>
      </div>
    </el-dialog>

    <!-- 拒绝退款弹窗 -->
    <el-dialog title="拒绝退款" :visible.sync="rejectRefundDialogVisible" width="500px">
      <el-form ref="rejectRefundForm" :model="rejectRefundForm" label-width="100px">
        <el-form-item label="退货单号" prop="returnTrackingNumber" v-if="rejectRefundForm.returnTrackingNumber">
          <el-input
            v-model="rejectRefundForm.returnTrackingNumber"
            readonly
            placeholder="暂无退货单号"
            style="background-color: #f5f7fa;"
          />
        </el-form-item>
        <el-form-item label="用户退款原因" prop="userRefundReason">
          <el-input
            v-model="rejectRefundForm.userRefundReason"
            type="textarea"
            :rows="3"
            readonly
            placeholder="暂无退款原因"
            style="background-color: #f5f7fa;"
          />
        </el-form-item>
        <el-form-item label="拒绝原因" prop="rejectReason">
          <el-input
            v-model="rejectRefundForm.rejectReason"
            type="textarea"
            :rows="3"
            placeholder="请输入拒绝退款的原因"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="rejectRefundDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmRejectRefund">确认拒绝</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchOrderPage, getOrder, payOrder, shipOrder, completeOrder, cancelOrder, updateTrackingNumber, confirmRefund, rejectRefund } from '@/api/business/order'
import waves from '@/directive/waves'
import Pagination from '@/components/Pagination'
import { success, error } from '@/utils'

export default {
  name: 'OrderList',
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      tableKey: 0,
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        current: 1,
        size: 20,
        orderNo: '',
        authorName: '',
        orderStatus: ''
      },
      dialogVisible: false,
      currentOrder: null,
      shipDialogVisible: false,
      shipForm: {
        orderId: '',
        trackingNumber: ''
      },
      editTrackingDialogVisible: false,
      editTrackingForm: {
        orderId: '',
        trackingNumber: ''
      },
      cancelDialogVisible: false,
      cancelForm: {
        orderId: '',
        cancelReason: ''
      },
      refundDialogVisible: false,
      refundForm: {
        orderId: '',
        refundAmount: '',
        refundReason: '',
        refundProcessRemark: '',
        returnTrackingNumber: ''
      },
      rejectRefundDialogVisible: false,
      rejectRefundForm: {
        orderId: '',
        rejectReason: '',
        userRefundReason: '',
        returnTrackingNumber: ''
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchOrderPage(this.listQuery).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      }).catch(() => {
        this.listLoading = false
      })
    },
    
    handleFilter() {
      this.listQuery.current = 1
      this.getList()
    },
    
    resetSearch() {
      this.listQuery = {
        current: 1,
        size: 20,
        orderNo: '',
        authorName: '',
        orderStatus: ''
      }
      this.getList()
    },
    
    handleImageError(event) {
      // 图片加载失败时使用默认图片
      event.target.src = '/static/img/demo1.jpg'
    },
    
    handleView(row) {
      getOrder(row.orderId).then(response => {
        this.currentOrder = response.data
        this.dialogVisible = true
      }).catch(err => {
        error('获取订单详情失败')
      })
    },
    
    handlePay(row) {
      this.$confirm('确认支付此订单？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        payOrder(row.orderId).then(() => {
          success('支付成功')
          this.getList()
        }).catch(() => {
          error('支付失败')
        })
      })
    },
    
    handleShip(row) {
      this.shipForm.orderId = row.orderId
      this.shipForm.trackingNumber = ''
      this.shipDialogVisible = true
    },
    
    confirmShip() {
      if (!this.shipForm.trackingNumber.trim()) {
        error('请输入快递单号')
        return
      }
      
      shipOrder(this.shipForm.orderId, this.shipForm.trackingNumber).then(() => {
        success('发货成功')
        this.shipDialogVisible = false
        this.getList()
      }).catch(() => {
        error('发货失败')
      })
    },
    
    handleEditTracking(row) {
      this.editTrackingForm.orderId = row.orderId
      this.editTrackingForm.trackingNumber = row.trackingNumber || ''
      this.editTrackingDialogVisible = true
    },
    
    confirmEditTracking() {
      if (!this.editTrackingForm.trackingNumber.trim()) {
        error('请输入快递单号')
        return
      }
      
      updateTrackingNumber(this.editTrackingForm.orderId, this.editTrackingForm.trackingNumber).then(() => {
        success('快递单号修改成功')
        this.editTrackingDialogVisible = false
        this.getList()
        // 如果详情弹窗打开，也需要刷新详情
        if (this.dialogVisible && this.currentOrder) {
          this.handleView(this.currentOrder)
        }
      }).catch(() => {
        error('修改失败')
      })
    },
    
    handleComplete(row) {
      this.$confirm('确认完成此订单？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        completeOrder(row.orderId).then(() => {
          success('订单完成')
          this.getList()
        }).catch(() => {
          error('操作失败')
        })
      })
    },
    
    handleCancel(row) {
      this.cancelForm.orderId = row.orderId
      this.cancelForm.cancelReason = ''
      this.cancelDialogVisible = true
    },
    
    confirmCancel() {
      if (!this.cancelForm.cancelReason.trim()) {
        error('请输入取消原因')
        return
      }
      
      cancelOrder(this.cancelForm.orderId, this.cancelForm.cancelReason).then(() => {
        success('订单已取消')
        this.cancelDialogVisible = false
        this.getList()
      }).catch(() => {
        error('取消失败')
      })
    },

    handleConfirmRefund(row) {
      this.refundForm.orderId = row.orderId
      this.refundForm.refundAmount = row.totalAmount
      this.refundForm.refundReason = row.refundReason || '暂无退款原因'
      this.refundForm.refundProcessRemark = ''
      this.refundForm.returnTrackingNumber = row.returnTrackingNumber || ''
      this.refundDialogVisible = true
    },

    confirmRefund() {
      if (!this.refundForm.refundAmount || this.refundForm.refundAmount <= 0) {
        error('请输入正确的退款金额')
        return
      }
      
      // 调用确认退款API
       this.$confirm('确认退款此订单？', '提示', {
         confirmButtonText: '确定',
         cancelButtonText: '取消',
         type: 'warning'
       }).then(() => {
         confirmRefund(this.refundForm.orderId, this.refundForm.refundAmount, this.refundForm.refundProcessRemark || '').then(() => {
           success('退款成功')
           this.refundDialogVisible = false
           this.getList()
         }).catch(() => {
           error('退款失败')
         })
       })
    },

    handleRejectRefund(row) {
      this.rejectRefundForm.orderId = row.orderId
      this.rejectRefundForm.rejectReason = ''
      this.rejectRefundForm.userRefundReason = row.refundReason || '暂无退款原因'
      this.rejectRefundForm.returnTrackingNumber = row.returnTrackingNumber || ''
      this.rejectRefundDialogVisible = true
    },

    confirmRejectRefund() {
      if (!this.rejectRefundForm.rejectReason.trim()) {
        error('请输入拒绝原因')
        return
      }
      
      // 调用拒绝退款API
       this.$confirm('确认拒绝退款申请？', '提示', {
         confirmButtonText: '确定',
         cancelButtonText: '取消',
         type: 'warning'
       }).then(() => {
         rejectRefund(this.rejectRefundForm.orderId, this.rejectRefundForm.rejectReason).then(() => {
           success('已拒绝退款申请')
           this.rejectRefundDialogVisible = false
           this.getList()
         }).catch(() => {
           error('拒绝退款失败')
         })
       })
    },
    
    getStatusType(status) {
      const statusMap = {
        0: 'warning',  // 待付款
        1: 'info',     // 已付款
        2: 'primary',  // 已发货
        3: 'success',  // 已完成
        4: 'danger',   // 已取消
        5: 'warning'   // 退款申请
      }
      return statusMap[status] || 'info'
    },
    
    getStatusText(status) {
      const statusMap = {
        0: '待付款',
        1: '待发货',
        2: '已发货',
        3: '已完成',
        4: '已取消',
        5: '退款申请',
        6: '申请被拒绝',
        7: '已评价'
      }
      return statusMap[status] || '未知'
    },
    
    formatDate(dateStr) {
      if (!dateStr) return ''
      const date = new Date(dateStr)
      return date.toLocaleDateString('zh-CN')
    },
    
    formatTime(dateStr) {
      if (!dateStr) return ''
      const date = new Date(dateStr)
      return date.toLocaleTimeString('zh-CN')
    },
    
    formatDateTime(dateStr) {
      if (!dateStr) return ''
      const date = new Date(dateStr)
      return date.toLocaleString('zh-CN')
    }
  }
}
</script>

<style scoped>
.filter-container {
  padding: 10px 0;
  margin-bottom: 20px;
}

.filter-item {
  margin-right: 10px;
}

.order-products {
  max-width: 280px;
}

.product-item {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
  padding: 4px;
  border: 1px solid #f0f0f0;
  border-radius: 4px;
}

.product-image {
  width: 40px;
  height: 40px;
  background: #f0f0f0;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 10px;
  color: #999;
  flex-shrink: 0;
}

.product-info {
  flex: 1;
  font-size: 12px;
  min-width: 0;
}

.product-name {
  color: #333;
  margin-bottom: 2px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.product-price {
  color: #ff4757;
  font-weight: 500;
}

.order-info {
  background: #f8f9fa;
  padding: 15px;
  border-radius: 4px;
}

.info-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  padding: 8px 0;
  border-bottom: 1px solid #f0f0f0;
}

.info-label {
  color: #666;
  font-weight: 500;
  min-width: 100px;
}

.info-value {
  color: #333;
  flex: 1;
  text-align: right;
}

.dialog-footer {
  text-align: right;
}
</style>