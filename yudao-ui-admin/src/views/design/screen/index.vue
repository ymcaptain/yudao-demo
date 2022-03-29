<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input v-model="queryParams.title" placeholder="请输入标题" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="访问码" prop="viewCode">
        <el-input v-model="queryParams.viewCode" placeholder="请输入访问码" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="dateRangeCreateTime" style="width: 240px" value-format="yyyy-MM-dd"
                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['design:screen:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['design:screen:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="屏幕比例X" align="center" prop="scaleX" />
      <el-table-column label="屏幕比例Y" align="center" prop="scaleY" />
      <el-table-column label="访问码" align="center" prop="viewCode" />
      <el-table-column label="访问量" align="center" prop="countView" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-monitor" @click="handleView(scope.row)"
                     v-hasPermi="['design:screen:query']">预览</el-button>
          <el-button size="mini" type="text" icon="el-icon-coordinate" @click="handleEdit(scope.row)"
                     v-hasPermi="['design:screen:update']">设计</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['design:screen:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['design:screen:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="描述" prop="simpleDesc">
          <el-input v-model="form.simpleDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="访问码" prop="viewCode">
          <el-input v-model="form.viewCode" placeholder="请输入访问码" />
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
import { createScreen, updateScreen, deleteScreen, getScreen, getScreenPage, getScreenSelectList, exportScreenExcel } from "@/api/design/screen";
import ImageUpload from '@/components/ImageUpload';

export default {
  name: "Screen",
  components: {
    ImageUpload,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 数据大屏列表
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
        title: null,
        scaleX: null,
        scaleY: null,
        designImgId: null,
        state: null,
        viewCode: null,
        countView: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      selectLists:{
      }
    };
  },
  created() {
    this.getSelectQueryList();
    this.getList();
  },
  methods: {
    /** 获取组合查询 */
    getSelectQueryList(){
    },    /** 获取组合查询 */
    getSelectFormList(){
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 处理查询参数
      let params = {...this.queryParams};
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行查询
      getScreenPage(params).then(response => {
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
        title: undefined,
        simpleDesc: undefined,
        bgImg: undefined,
        bgColor: undefined,
        scaleX: undefined,
        scaleY: undefined,
        components: undefined,
        designImgId: undefined,
        state: undefined,
        viewCode: undefined,
        countView: undefined,
      };
      this.getSelectFormList();
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
      this.getSelectQueryList();
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.getSelectFormList();
      this.title = "添加数据大屏";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getScreen(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.getSelectFormList();
        this.title = "修改数据大屏";
      });
    },
    /** 设计按钮操作 */
    handleEdit(row) {
      window.open('/#/design?id='+row.id,"_blank");
    },
    /** 设计按钮操作 */
    handleView(row) {
      window.open('/#/view?id='+row.id,"_blank");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 修改的提交
        if (this.form.id != null) {
          updateScreen(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createScreen(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除数据大屏编号为"' + id + '"的数据项?').then(function() {
          return deleteScreen(id);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      let excel_name = '数据大屏.xls';
      // 处理查询参数
      let params = {...this.queryParams};
      params.pageNo = undefined;
      params.pageSize = undefined;
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');

      // 执行导出
      this.$modal.confirm('是否确认导出所有数据大屏数据项?').then(() => {
          this.exportLoading = true;
          return exportScreenExcel(params);
        }).then(response => {
          this.$download.excel(response,excel_name);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
