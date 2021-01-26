<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="设备编号">
              <a-input placeholder="请输入设备编号" v-model="queryParam.code"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="设备名称">
              <a-input placeholder="请输入设备名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('设备信息')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <deviceinformation-modal ref="modalForm" @ok="modalFormOk"></deviceinformation-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import DeviceinformationModal from './modules/DeviceinformationModal'
  import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'

  export default {
    name: 'DeviceinformationList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      DeviceinformationModal,
      JSuperQuery,
    },
    data () {
      return {
        description: '设备信息管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'设备编号',
            align:"center",
            dataIndex: 'code'
          },
          {
            title:'设备名称',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'规格型号',
            align:"center",
            dataIndex: 'model'
          },
          {
            title:'生产厂家',
            align:"center",
            dataIndex: 'manufacturer'
          },
          {
            title:'出产编号',
            align:"center",
            dataIndex: 'manufacturercode'
          },
          {
            title:'出产日期',
            align:"center",
            dataIndex: 'manufacturerdate'
          },
          {
            title:'投产日期',
            align:"center",
            dataIndex: 'commissiondate'
          },
          {
            title:'数量',
            align:"center",
            dataIndex: 'quantity'
          },
          {
            title:'单价',
            align:"center",
            dataIndex: 'unitprice'
          },
          {
            title:'保养人',
            align:"center",
            dataIndex: 'maintainer'
          },
          {
            title:'仪器部门',
            align:"center",
            dataIndex: 'instrunentdept'
          },
          {
            title:'放置地点',
            align:"center",
            dataIndex: 'placementlocation'
          },
          {
            title:'仪器检测日期',
            align:"center",
            dataIndex: 'instrumenttestdate'
          },
          {
            title:'下次检测日期',
            align:"center",
            dataIndex: 'nexttestdate'
          },
          {
            title:'说明书',
            align:"center",
            dataIndex: 'descriptionString'
          },
          {
            title:'仪器，设备',
            align:"center",
            dataIndex: 'instrumentandequipment'
          },
          {
            title:'性质类别',
            align:"center",
            dataIndex: 'naturecategory'
          },
          {
            title:'重要性类别',
            align:"center",
            dataIndex: 'importancecategory'
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'states'
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'remarks'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/device/deviceinformation/list",
          delete: "/device/deviceinformation/delete",
          deleteBatch: "/device/deviceinformation/deleteBatch",
          exportXlsUrl: "/device/deviceinformation/exportXls",
          importExcelUrl: "device/deviceinformation/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'code',text:'设备编号',dictCode:''})
        fieldList.push({type:'string',value:'name',text:'设备名称',dictCode:''})
        fieldList.push({type:'string',value:'model',text:'规格型号',dictCode:''})
        fieldList.push({type:'Blob',value:'qrcode',text:'二维码',dictCode:''})
        fieldList.push({type:'string',value:'manufacturer',text:'生产厂家',dictCode:''})
        fieldList.push({type:'string',value:'manufacturercode',text:'出产编号',dictCode:''})
        fieldList.push({type:'datetime',value:'manufacturerdate',text:'出产日期'})
        fieldList.push({type:'datetime',value:'commissiondate',text:'投产日期'})
        fieldList.push({type:'int',value:'quantity',text:'数量',dictCode:''})
        fieldList.push({type:'double',value:'unitprice',text:'单价',dictCode:''})
        fieldList.push({type:'string',value:'maintainer',text:'保养人',dictCode:''})
        fieldList.push({type:'string',value:'instrunentdept',text:'仪器部门',dictCode:''})
        fieldList.push({type:'string',value:'placementlocation',text:'放置地点',dictCode:''})
        fieldList.push({type:'datetime',value:'instrumenttestdate',text:'仪器检测日期'})
        fieldList.push({type:'datetime',value:'nexttestdate',text:'下次检测日期'})
        fieldList.push({type:'Blob',value:'description',text:'说明书',dictCode:''})
        fieldList.push({type:'string',value:'instrumentandequipment',text:'仪器，设备',dictCode:''})
        fieldList.push({type:'string',value:'naturecategory',text:'性质类别',dictCode:''})
        fieldList.push({type:'string',value:'importancecategory',text:'重要性类别',dictCode:''})
        fieldList.push({type:'string',value:'states',text:'状态',dictCode:''})
        fieldList.push({type:'string',value:'remarks',text:'备注',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>