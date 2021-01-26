<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="8">
            <a-form-item label="设备编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['code', validatorRules.code]" placeholder="请输入设备编号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入设备名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="规格型号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['model']" placeholder="请输入规格型号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="二维码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload isMultiple  v-decorator="['qrcode']" disabled></j-image-upload>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="生产厂家" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['manufacturer']" placeholder="请输入生产厂家"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="出产编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['manufacturercode']" placeholder="请输入出产编号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="出产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择出产日期" v-decorator="['manufacturerdate']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="投产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择投产日期" v-decorator="['commissiondate']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['quantity', validatorRules.quantity]" placeholder="请输入数量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="单价" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['unitprice', validatorRules.unitprice]" placeholder="请输入单价" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="保养人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['maintainer']" placeholder="请输入保养人"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="仪器部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['instrunentdept']" placeholder="请输入仪器部门"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="放置地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['placementlocation']" placeholder="请输入放置地点"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="仪器检测日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择仪器检测日期" v-decorator="['instrumenttestdate']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="下次检测日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择下次检测日期" v-decorator="['nexttestdate']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="说明书" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-upload v-decorator="['description']" :trigger-change="true"  ></j-upload>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="仪器，设备" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['instrumentandequipment']" placeholder="请输入仪器，设备"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="性质类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['naturecategory']" placeholder="请输入性质类别"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="重要性类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['importancecategory']" placeholder="请输入重要性类别"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['states']" placeholder="请输入状态"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['remarks']" rows="4" placeholder="请输入备注" />
            </a-form-item>
          </a-col>
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JDate from '@/components/jeecg/JDate'  
  import JUpload from '@/components/jeecg/JUpload'
  import JImageUpload from '@/components/jeecg/JImageUpload'

  export default {
    name: 'DeviceinformationForm',
    components: {
      JFormContainer,
      JDate,
      JUpload,
      JImageUpload,
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
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
          code: {
            rules: [
              { required: false},
              { validator: (rule, value, callback) => validateDuplicateValue('deviceinformation', 'code', value, this.model.id, callback)},
            ]
          },
          name: {
            rules: [
              { required: true, message: '请输入设备名称!'},
            ]
          },
          quantity: {
            rules: [
              { required: false},
              { pattern: /^-?\d+$/, message: '请输入整数!'},
            ]
          },
          unitprice: {
            rules: [
              { required: false},
              { pattern: /^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
            ]
          },
        },
        url: {
          add: "/device/deviceinformation/add",
          edit: "/device/deviceinformation/edit",
          queryById: "/device/deviceinformation/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'code','name','model','qrcodeString','manufacturer','manufacturercode','manufacturerdate','commissiondate','quantity','unitprice','maintainer','instrunentdept','placementlocation','instrumenttestdate','nexttestdate','descriptionString','instrumentandequipment','naturecategory','importancecategory','states','remarks'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
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
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
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
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'code','name','model','qrcodeString','manufacturer','manufacturercode','manufacturerdate','commissiondate','quantity','unitprice','maintainer','instrunentdept','placementlocation','instrumenttestdate','nexttestdate','descriptionString','instrumentandequipment','naturecategory','importancecategory','states','remarks'))
      },
    }
  }
</script>