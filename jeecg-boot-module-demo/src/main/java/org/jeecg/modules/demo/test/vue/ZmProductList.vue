<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="产品型号">
              <a-input placeholder="请输入产品型号" v-model="queryParam.model"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('产品列表')">导出</a-button>
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

    <zm-product-modal ref="modalForm" @ok="modalFormOk"></zm-product-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ZmProductModal from './modules/ZmProductModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: 'ZmProductList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      ZmProductModal
    },
    data () {
      return {
        description: '产品列表管理页面',
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
            title:'中文名称',
            align:"center",
            dataIndex: 'cnName'
          },
          {
            title:'英文名称',
            align:"center",
            dataIndex: 'enName'
          },
          {
            title:'申报单价',
            align:"center",
            dataIndex: 'declaredPrice'
          },
          {
            title:'产品海关编码',
            align:"center",
            dataIndex: 'hscode'
          },
          {
            title:'产品材料',
            align:"center",
            dataIndex: 'material'
          },
          {
            title:'产品用途',
            align:"center",
            dataIndex: 'application'
          },
          {
            title:'品牌',
            align:"center",
            dataIndex: 'brand'
          },
          {
            title:'品牌类型',
            align:"center",
            dataIndex: 'type_dictText'
          },
          {
            title:'产品型号',
            align:"center",
            dataIndex: 'model'
          },
          {
            title:'销售链接',
            align:"center",
            dataIndex: 'link'
          },
          {
            title:'产品售价',
            align:"center",
            dataIndex: 'price'
          },
          {
            title:'图片链接',
            align:"center",
            dataIndex: 'picture'
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
          list: "/test/zmProduct/list",
          delete: "/test/zmProduct/delete",
          deleteBatch: "/test/zmProduct/deleteBatch",
          exportXlsUrl: "/test/zmProduct/exportXls",
          importExcelUrl: "test/zmProduct/importExcel",
          
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
        fieldList.push({type:'string',value:'cnName',text:'中文名称',dictCode:''})
        fieldList.push({type:'string',value:'enName',text:'英文名称',dictCode:''})
        fieldList.push({type:'double',value:'declaredPrice',text:'申报单价',dictCode:''})
        fieldList.push({type:'string',value:'hscode',text:'产品海关编码',dictCode:''})
        fieldList.push({type:'string',value:'material',text:'产品材料',dictCode:''})
        fieldList.push({type:'string',value:'application',text:'产品用途',dictCode:''})
        fieldList.push({type:'string',value:'brand',text:'品牌',dictCode:''})
        fieldList.push({type:'int',value:'type',text:'品牌类型',dictCode:'type'})
        fieldList.push({type:'string',value:'model',text:'产品型号',dictCode:''})
        fieldList.push({type:'string',value:'link',text:'销售链接',dictCode:''})
        fieldList.push({type:'double',value:'price',text:'产品售价',dictCode:''})
        fieldList.push({type:'string',value:'picture',text:'图片链接',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>