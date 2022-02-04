<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="父类目名称" prop="pid">
        <el-select v-model="queryParams.pid" filterable placeholder="请选择" clearable>
          <el-option v-for="item in rootCategories" :key="parseInt(item.id)" :label="item.name" :value="parseInt(item.id)">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="分类名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入分类名称" clearable size="small"
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
                   v-hasPermi="['mall:product-category:create']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                   v-hasPermi="['mall:product-category:export']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="编号" align="center" prop="id" width="90"/>
      <el-table-column label="名称" align="center" prop="name" width="360px"/>
      <el-table-column label="图片" align="center" prop="picUrl" width="100px">
        <template slot-scope="scope">
          <el-image style="width: 80px; height: 80px"
                    :src="scope.row.picUrl" fit="fit" :preview-src-list="imageList">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center" prop="description" width="150" :show-overflow-tooltip="true"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" :active-value="0" :inactive-value="1"
                     @change="handleStatusChange(scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" :disabled="scope.row.pid !== 0"
                     @click="handleChildQuery(scope.row)" v-hasPermi="['mall:product-category:query']">查看下级
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['mall:product-category:update']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['mall:product-category:delete']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类栏目" prop="pid">
          <el-select v-model="form.pid" filterable placeholder="请选择" clearable>
            <el-option :value="parseInt(0)" label="顶级栏目"></el-option>
            <el-option v-for="item in rootCategories" :key="parseInt(item.id)" :label="item.name" :value="parseInt(item.id)">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入分类名称"/>
        </el-form-item>
        <el-form-item label="图片" prop="picUrl">
          <el-upload ref="upload" action="#"
                     list-type="picture-card" :limit="1" :file-list="uploadList"
                     :before-upload="handleBeforeUpload" :http-request="handleUpload"
                     :on-exceed="handleExceed" :on-preview="handlePictureCardPreview">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入分类排序"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio v-for="dict in commonStatusDatum" :key="parseInt(dict.value)" :label="parseInt(dict.value)">
              {{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="form.description"
                    placeholder="请输入品牌描述"/>
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
  createProductCategory,
  updateProductCategory,
  updateProductCategoryStatus,
  deleteProductCategory,
  getProductCategory,
  getProductRootCategory,
  getProductCategoryPage,
  exportProductCategoryExcel
} from "@/api/mall/product/category";
import {DICT_TYPE, getDictDatas} from "@/utils/dict";
import {uploadFile} from "@/api/infra/file";
import {SysCommonStatusEnum} from "@/utils/constants";

const defaultForm = {
  id: null,
  pid: null,
  name: '',
  description: '',
  picUrl: '',
  sort: null,
  status: null,
};

export default {
  name: "ProductCategory",
  components: {},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 商品分类列表
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
        pid: null,
        name: null,
        description: null,
        picUrl: null,
        sort: null,
        status: null,
      },
      // 表单参数
      form: JSON.parse(JSON.stringify(defaultForm)),
      // 表单校验
      rules: {
        pid: [{required: true, message: "父分类编号不能为空", trigger: "blur"}],
        name: [{required: true, message: "分类名称不能为空", trigger: "blur"}],
        sort: [{required: true, message: "分类排序不能为空", trigger: "blur"}],
        status: [{required: true, message: "状态不能为空", trigger: "blur"}],
      },
      // 跟类目分类列表
      rootCategories: [],
      // 图片列表
      imageList: [],
      // 通用状态
      commonStatusDatum: getDictDatas(DICT_TYPE.SYS_COMMON_STATUS),
      // 表单loading
      formLoading: false,
      // 表单loading 文本
      formLoadingText: '',
      // 预览图片状态
      pictureCardPreviewOpen: false,
      // 预览图片URL
      pictureCardPreviewUrl: '',
      // 上传文件列表
      uploadList: [],
    };
  },
  created() {
    this.getCategoryRoots();
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 处理查询参数
      let params = {...this.queryParams};
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行查询
      getProductCategoryPage(params).then(response => {
        this.list = response.data.list;
        this.imageList = [];
        response.data.list.forEach(o => this.imageList.push(o.picUrl));
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
      this.form = JSON.parse(JSON.stringify(defaultForm));
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
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商品分类";
      if (this.queryParams.pid !== null) {
        this.form.pid = this.queryParams.pid;
      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getProductCategory(id).then(response => {
        this.form = response.data;
        this.uploadList = [{
          name: response.data.picUrl,
          url: response.data.picUrl
        }];
        this.open = true;
        this.title = "修改商品分类";
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
          updateProductCategory(this.form).then(response => {
            this.msgSuccess("修改成功");
            this.open = false;
            this.form = JSON.parse(JSON.stringify(defaultForm));
            this.getList();
          });
          return;
        }
        // 添加的提交
        createProductCategory(this.form).then(response => {
          this.msgSuccess("新增成功");
          this.open = false;
          this.form = JSON.parse(JSON.stringify(defaultForm));
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$confirm('是否确认删除商品分类编号为"' + id + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return deleteProductCategory(id);
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
      this.$confirm('是否确认导出所有商品分类数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportProductCategoryExcel(params);
      }).then(response => {
        this.downloadExcel(response, '商品分类.xls');
      })
    },
    /** 获取顶级分类列表 */
    getCategoryRoots() {
      getProductRootCategory().then(res => {
        this.rootCategories = res.data;
      })
    },
    /** 执行修改状态 */
    handleStatusChange(row) {
      let text = row.status === SysCommonStatusEnum.ENABLE ? "启用" : "停用";
      // '确认要"' + text + '""' + row.name + '"当前商品品牌吗?'
      this.$confirm(`确定要[${text}] "${row.name}"当商品分类吗？`, "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return updateProductCategoryStatus(row.id, row.status);
      }).then(() => {
        this.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === SysCommonStatusEnum.ENABLE ? SysCommonStatusEnum.DISABLE
          : SysCommonStatusEnum.ENABLE;
      });
    },
    /** 执行子类目搜索 */
    handleChildQuery(row) {
      this.queryParams.pid = row.id;
      this.queryParams.name = '';
      this.queryParams.status = null;
      this.getList();
    },
    /**图片超出限制*/
    handleExceed() {
      this.$message.warning("图片选择已达上限，请删除后重新选择");
    },
    /**图片上传之前的逻辑处理*/
    handleBeforeUpload(file) {
      if (file.type.indexOf("image/") === -1) {
        this.$message.error("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
      } else {
        this.formLoading = true;
        this.formLoadingText = "图片上传中。。。";
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.form.picUrl = reader.result;
        };
      }
    },
    /**重新图片上传逻辑*/
    handleUpload(file) {
      let formData = new FormData();
      let path = new Date().getTime() + file.file.name.substring(file.file.name.lastIndexOf("."));
      formData.append("file", file.file);
      formData.append("path", path)
      uploadFile(formData).then(res => {
        this.form.picUrl = res.data;
        this.$message.success("图片上传成功");
        this.formLoading = false;
      }).catch(error => {
        this.formLoading = true;
        this.$message.error(error.msg());
      })
    },
    /**图片预览*/
    handlePictureCardPreview(file) {
      this.pictureCardPreviewUrl = file.url;
      this.pictureCardPreviewOpen = true;
    }
  }
};
</script>
