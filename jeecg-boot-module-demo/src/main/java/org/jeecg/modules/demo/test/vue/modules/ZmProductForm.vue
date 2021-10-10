<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-model-item label="中文名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cnName">
              <a-input v-model="model.cnName" placeholder="请输入中文名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="英文名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="enName">
              <a-input v-model="model.enName" placeholder="请输入英文名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="申报单价" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="declaredPrice">
              <a-input-number v-model="model.declaredPrice" placeholder="请输入申报单价" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="产品海关编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="hscode">
              <a-input v-model="model.hscode" placeholder="请输入产品海关编码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="产品材料" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="material">
              <a-input v-model="model.material" placeholder="请输入产品材料"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="产品用途" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="application">
              <a-input v-model="model.application" placeholder="请输入产品用途"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="品牌" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="brand">
              <a-input v-model="model.brand" placeholder="请输入品牌"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="品牌类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="type">
              <j-dict-select-tag type="list" v-model="model.type" dictCode="type" placeholder="请选择品牌类型" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="产品型号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="model">
              <a-input v-model="model.model" placeholder="请输入产品型号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="销售链接" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="link">
              <a-input v-model="model.link" placeholder="请输入销售链接"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="产品售价" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="price">
              <a-input-number v-model="model.price" placeholder="请输入产品售价" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12">
            <a-form-model-item label="图片链接" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="picture">
              <a-input v-model="model.picture" placeholder="请输入图片链接"  ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'ZmProductForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
           cnName: [
              { required: true, message: '请输入中文名称!'},
           ],
           hscode: [
              { required: true, message: '请输入产品海关编码!'},
           ],
        },
        url: {
          add: "/test/zmProduct/add",
          edit: "/test/zmProduct/edit",
          queryById: "/test/zmProduct/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
    }
  }
</script>