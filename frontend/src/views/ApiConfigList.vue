<template>
    <el-row class="mb-2" justify="space-between">
        <el-button type="primary" size="small" @click="createApiConfig">
            新增
        </el-button>
        <div>
            <el-select v-model="dataSourceConfigId" class="mr-2">
                <el-option v-for="dataSourceConfig in dataSourceConfigList" :key="dataSourceConfig.id"
                           :label="dataSourceConfig.name" :value="dataSourceConfig.id">
                </el-option>
            </el-select>
            <el-select v-model="apiGroupId" class="mr-2">
                <el-option v-for="apiGroup in apiGroupList" :key="apiGroup.id"
                           :label="apiGroup.name" :value="apiGroup.id">
                </el-option>
            </el-select>
            <el-button @click="getApiList">查询</el-button>
            <el-button @click="resetCondition">重置</el-button>
        </div>
    </el-row>
    <el-table :data="apiConfigList" empty-text="暂无数据" border>
        <el-table-column label="请求参数" type="expand" width="100">
            <template #default="props">
                <el-table :data="props.row.paramConfigList" border>
                    <el-table-column prop="name" label="名称"/>
                    <el-table-column prop="key" label="Key"/>
                    <el-table-column prop="type" label="类型"/>
                    <el-table-column prop="listable" label="是否数组" :formatter="formatBoolean"/>
                    <el-table-column prop="required" label="是否必需" :formatter="formatBoolean"/>
                </el-table>
            </template>
        </el-table-column>
        <el-table-column prop="requestMethod" label="请求方法" width="100"/>
        <el-table-column prop="requestPath" label="请求路径"/>
        <el-table-column prop="name" label="接口名称" width="200"/>
        <el-table-column prop="listable" label="是否缓存结果" width="120" :formatter="formatBoolean"/>
        <el-table-column prop="listable" label="是否列表查询" width="120" :formatter="formatBoolean"/>
        <el-table-column prop="pageable" label="是否分页查询" width="120" :formatter="formatBoolean"/>
        <el-table-column prop="enable" label="是否启用" width="100">
            <template #default="scope">
                <el-switch v-model="scope.row.enable" @change="changeEnable($event,scope.row)">
                </el-switch>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
            <template #default="scope">
                <el-button type="primary" size="small" @click="testApiConfig(scope.row)" circle>
                    <el-icon>
                        <monitor/>
                    </el-icon>
                </el-button>
                <el-button type="warning" size="small" @click="updateApiConfig(scope.row)" circle>
                    <el-icon>
                        <edit/>
                    </el-icon>
                </el-button>
                <el-button type="danger" size="small" @click="deleteApiConfig(scope.row)" circle>
                    <el-icon>
                        <delete/>
                    </el-icon>
                </el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination v-model="currentPage" :total="apiConfigTotal" @current-change="handlePageChange"
                   class="pagination" layout="prev, pager, next" :pager-count="5" background>
    </el-pagination>
</template>

<script>
import request from '../utils/request'
import {Delete, Edit, Monitor} from '@element-plus/icons-vue'

export default {
    name: "ApiConfigList",
    components: {
        Delete,
        Edit,
        Monitor
    },
    data() {
        return {
            dataSourceConfigId: null,
            dataSourceConfigList: [],
            apiGroupId: null,
            apiGroupList: [],
            apiConfigList: [],
            apiConfigTotal: 0,
            currentPage: 0,
        }
    },
    created() {
        this.getDataSourceConfigList()
        this.getApiGroupList()
        this.getApiList()
    },
    methods: {
        getDataSourceConfigList() {
            request('/sys/data-source-configs', {Method: 'GET'}).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.dataSourceConfigList = data
                    })
                }
            })
        },
        getApiGroupList() {
            request('/sys/api-groups', {Method: 'GET'}).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.apiGroupList = data
                    })
                }
            })
        },
        getApiList() {
            let config = {Method: "GET", param: {page: this.currentPage, size: 10}}
            if (this.dataSourceConfigId != null) {
                config.param.dataSourceConfigId = this.dataSourceConfigId
            }
            if (this.apiGroupId != null) {
                config.param.groupId = this.apiGroupId
            }
            request('/sys/api-configs', config).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.apiConfigList = data.list
                        this.apiConfigTotal = data.total
                    })
                }
            })
        },
        createApiConfig() {
            this.$router.push({name: 'ApiConfigCreate'})
        },
        testApiConfig(apiConfig) {
            this.$router.push({name: 'ApiConfigTest', params: {id: apiConfig.id}})
        },
        updateApiConfig(apiConfig) {
            this.$router.push({name: 'ApiConfigUpdate', params: {id: apiConfig.id}})
        },
        deleteApiConfig(apiConfig) {
            this.$confirm('确定删除？').then(() => {
                let url = '/sys/api-configs/' + apiConfig.id
                request(url, {method: 'DELETE'}).then(response => {
                    if (response.ok) {
                        let index = this.apiConfigList.indexOf(apiConfig)
                        this.apiConfigList.splice(index, 1)
                        this.$message.success('删除成功！')
                    }
                })
            })
        },
        changeEnable(enable, apiConfig) {
            this.$confirm('确定' + (enable ? '启用' : '禁用') + '？').then(() => {
                apiConfig.enable = enable
                let config = {method: 'PUT', body: apiConfig}
                request('/sys/api-configs', config).then(response => {
                    if (response.ok) {
                        this.$message.success((enable ? '启用' : '禁用') + '成功！')
                    }
                })
            })
        },
        formatBoolean(row, column, cellValue) {
            return cellValue ? '是' : '否';
        },
        resetCondition() {
            this.dataSourceConfigId = null
            this.apiGroupId = null
            this.getApiList()
        },
        handlePageChange(page) {
            this.currentPage = page - 1
            this.getApiList()
        }
    }
}
</script>

<style scoped>
</style>