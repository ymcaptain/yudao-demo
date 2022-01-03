<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="规格键" prop="attrKeyId">
        <el-select v-model="queryParams.attrKeyId" size="small" filterable placeholder="请选择"
                   @change="findByAttrKeyIdGetObject(queryParams.attrKeyId);">
          <el-option v-for="item in attrKeyList" :key="item.id" :label="item.name" :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="规格值名字" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入规格值名字" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option v-for="dict in commonStatusDatum" :key="parseInt(dict.value)" :label="dict.label"
                     :value="parseInt(dict.value)"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['mall:product-attr-key:create']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                   v-hasPermi="['mall:product-attr-key:export']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="规格值编号" align="center" prop="id"/>
      <el-table-column label="规格值名字" align="center" prop="name"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" :active-value="0" :inactive-value="1"
                     @change="handleStatusChange(scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['mall:product-attr-key:update']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['mall:product-attr-key:delete']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="规格键名称">
          <el-input v-model="this.selectedAttrKeyObject.name" :disabled="true"/>
        </el-form-item>
        <el-form-item label="规格值名字" prop="name">
          <el-input v-model="form.name" placeholder="请输入规格值名字"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio v-for="dict in commonStatusDatum" :key="parseInt(dict.value)" :label="parseInt(dict.value)">
              {{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  createProductAttrValue,
  updateProductAttrValue,
  deleteProductAttrValue,
  getProductAttrValue,
  getProductAttrValuePage,
  exportProductAttrValueExcel, updateProductAttrValueStatus
} from "@/api/mall/product/attrValue";
import {getAttrKeySimpleList, getProductAttrKey} from "@/api/mall/product/attrKey";
import {DICT_TYPE, getDictDatas} from "@/utils/dict";
import {SysCommonStatusEnum} from "@/utils/constants";

export default {
  name: "AttrValue",
  components: {},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 商品规格值列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dateRangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        attrKeyId: null,
        name: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        attrKeyId: [{required: true, message: "规格键编号不能为空", trigger: "blur"}],
        name: [{required: true, message: "规格值名字不能为空", trigger: "blur"}],
        status: [{required: true, message: "状态不能为空", trigger: "blur"}],
      },
      // 传递过来的规格商品键ID
      transferAttrKeyId: null,
      // 选中的商品规格键对象
      selectedAttrKeyObject: {
        id: null,
        name: ''
      },
      // 所有的商品规格键列表
      attrKeyList: null,
      // 通用状态
      commonStatusDatum: getDictDatas(DICT_TYPE.SYS_COMMON_STATUS),
    };
  },
  created() {
    this.transferAttrKeyId = this.$route.params && parseInt(this.$route.params.attrId);
    this.getAttrKeyAllList();
    this.findByAttrKeyIdGetObject(this.transferAttrKeyId);
  },
  methods: {
    /**
     * 根据选中的商品规格键 ID 获取对象
     */
    findByAttrKeyIdGetObject(attrKeyId) {
      getProductAttrKey(attrKeyId).then(response => {
        this.selectedAttrKeyObject = response.data;
        this.queryParams.attrKeyId = this.selectedAttrKeyObject.id;
        this.getList();
      })
    },
    /**
     * 查询所有的商品规格键
     */
    getAttrKeyAllList() {
      getAttrKeySimpleList().then(response => {
        this.attrKeyList = response.data;
      })
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 处理查询参数
      let params = {...this.queryParams};
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行查询
      getProductAttrValuePage(params).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: undefined,
        attrKeyId: this.selectedAttrKeyObject.id,
        name: undefined,
        status: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRangeCreateTime = [];
      this.resetForm("queryForm");
      this.queryParams.attrKeyId = this.transferAttrKeyId;
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商品规格值";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      // this.findByAttrKeyIdGetObject(row.attrKeyId);
      getProductAttrValue(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品规格值";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 修改的提交
        if (this.form.id != null) {
          updateProductAttrValue(this.form).then(response => {
            this.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createProductAttrValue(this.form).then(response => {
          this.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$confirm('是否确认删除商品规格值编号为"' + row.name + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return deleteProductAttrValue(id);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      // 处理查询参数
      let params = {...this.queryParams};
      params.pageNo = undefined;
      params.pageSize = undefined;
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行导出
      this.$confirm('是否确认导出所有商品规格值数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportProductAttrValueExcel(params);
      }).then(response => {
        this.downloadExcel(response, '商品规格值.xls');
      })
    },
    // 商品规格键状态修改
    handleStatusChange(row) {
      let text = row.status === SysCommonStatusEnum.ENABLE ? "启用" : "停用";
      this.$confirm('确认要"' + text + '""' + row.name + '"当前商品规格吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return updateProductAttrValueStatus(row.id, row.status);
      }).then(() => {
        this.msgSuccess(text + "成功");
      }).catch(function () {
        row.status = row.status === SysCommonStatusEnum.ENABLE ? SysCommonStatusEnum.DISABLE
          : SysCommonStatusEnum.ENABLE;
      });
    },
  }
};
</script>
