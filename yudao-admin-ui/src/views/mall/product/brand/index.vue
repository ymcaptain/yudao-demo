<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="品牌名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入品牌名称" clearable size="small"
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
                   v-hasPermi="['mall:product-brand:create']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                   v-hasPermi="['mall:product-brand:export']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="品牌编号" align="center" prop="id"/>
      <el-table-column label="品牌名称" align="center" prop="name"/>
      <el-table-column label="品牌描述" align="center" prop="description"/>
      <el-table-column label="品牌名图片" align="center" prop="picUrl">
        <template slot-scope="scope">
          <el-image style="width: 100px; height: 100px"
                    :src="scope.row.picUrl" :fit="fill" :preview-src-list="imageList">
          </el-image>
        </template>
      </el-table-column>
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
                     v-hasPermi="['mall:product-brand:update']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['mall:product-brand:delete']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body v-loading="formLoading"
               element-loading-text="提交中，请等候" element-loading-spinner="el-icon-loading">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="品牌名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入品牌名称"/>
        </el-form-item>
        <el-form-item label="品牌名图片" prop="picUrl">
          <el-upload ref="upload" action="#"
                     list-type="picture-card" :limit="1" :file-list="uploadList"
                     :before-upload="handleBeforeUpload" :http-request="handleUpload"
                     :on-exceed="handleExceed" :on-preview="handlePictureCardPreview">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio v-for="dict in commonStatusDatum" :key="parseInt(dict.value)" :label="parseInt(dict.value)">
              {{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="品牌描述" prop="description">
          <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="form.description"
                    placeholder="请输入品牌描述"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="pictureCardPreviewOpen" append-to-body>
      <img width="100%" :src="pictureCardPreviewUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import {
  createProductBrand,
  updateProductBrand,
  updateProductBrandStatus,
  deleteProductBrand,
  getProductBrand,
  getProductBrandPage,
  exportProductBrandExcel
} from "@/api/mall/product/brand";
import {DICT_TYPE, getDictDatas} from "@/utils/dict";
import {uploadFile} from "@/api/infra/file";
import {SysCommonStatusEnum} from "@/utils/constants";

const defaultBrandForm = {
  id: null,
  name: '',
  description: '',
  picUrl: '',
  status: '',
  createTime: ''
}

export default {
  name: "ProductBrand",
  components: {},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 商品品牌列表
      list: [],
      // 商品品牌图片列表
      imageList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dateRangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        name: null,
        description: null,
        picUrl: null,
        status: null,
      },
      // 表单参数
      form: JSON.parse(JSON.stringify(defaultBrandForm)),
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
      // 表单校验
      rules: {
        name: [{required: true, message: "品牌名称不能为空", trigger: "blur"}],
        status: [{required: true, message: "状态不能为空", trigger: "blur"}],
        picUrl: [{required: true, message: "品牌图片不能为空", trigger: "blur"}],
      },
      // 通用状态
      commonStatusDatum: getDictDatas(DICT_TYPE.SYS_COMMON_STATUS),
    };
  },
  created() {
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
      getProductBrandPage(params).then(response => {
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
      this.form = {
        id: undefined,
        name: undefined,
        description: undefined,
        picUrl: undefined,
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
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商品品牌";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getProductBrand(id).then(response => {
        this.form = response.data;
        this.uploadList = [{
          name: response.data.picUrl,
          url: response.data.picUrl
        }];
        this.title = "修改商品品牌";
        this.open = true;
      });
    },
    /** 提交按钮*/
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 修改的提交
        if (this.form.id != null) {
          updateProductBrand(this.form).then(response => {
            this.msgSuccess("修改成功");
            this.open = false;
            this.form = JSON.parse(JSON.stringify(defaultBrandForm));
            this.getList();
          });
          return;
        }
        // 添加的提交
        createProductBrand(this.form).then(response => {
          this.msgSuccess("新增成功");
          this.open = false;
          this.form = JSON.parse(JSON.stringify(defaultBrandForm));
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$confirm('是否确认删除商品品牌编号为"' + id + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return deleteProductBrand(id);
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
      this.$confirm('是否确认导出所有商品品牌数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportProductBrandExcel(params);
      }).then(response => {
        this.downloadExcel(response, '商品品牌.xls');
      })
    },
    // 商品规格键状态修改
    handleStatusChange(row) {
      let text = row.status === SysCommonStatusEnum.ENABLE ? "启用" : "停用";
      // '确认要"' + text + '""' + row.name + '"当前商品品牌吗?'
      this.$confirm(`确定要[${text}] "${row.name}"当商品品牌吗？`, "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return updateProductBrandStatus(row.id, row.status);
      }).then(() => {
        this.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === SysCommonStatusEnum.ENABLE ? SysCommonStatusEnum.DISABLE
          : SysCommonStatusEnum.ENABLE;
      });
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

