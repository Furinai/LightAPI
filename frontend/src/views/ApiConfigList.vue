<template>
    <el-row class="mb-2" justify="space-between">
        <el-button type="primary" size="small" @click="createApiConfig">
            新增
        </el-button>
        <el-select v-model="apiGroupId" @change="changeApiGroup">
            <el-option v-for="apiGroup in apiGroupList" :key="apiGroup.id"
                       :label="apiGroup.name" :value="apiGroup.id">
            </el-option>
        </el-select>
    </el-row>
    <el-table :data="apiConfigList" empty-text="未选择分组或者暂无数据" border>
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
        <el-table-column prop="name" label="名称" width="200"/>
        <el-table-column prop="requestMethod" label="请求方法" width="100"/>
        <el-table-column prop="requestPath" label="请求路径"/>
        <el-table-column prop="listable" label="是否列表" width="100" :formatter="formatBoolean"/>
        <el-table-column prop="pageable" label="是否分页" width="100" :formatter="formatBoolean"/>
        <el-table-column prop="enable" label="是否启用" width="100">
            <template #default="scope">
                <el-switch v-model="scope.row.enable" @change="changeEnable($event,scope.row)">
                </el-switch>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
            <template #default="scope">
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
</template>

<script>
import request from '../utils/request'
import {Delete, DocumentAdd, Edit} from '@element-plus/icons'

export default {
    name: "ApiConfigList",
    components: {
        Delete,
        DocumentAdd,
        Edit,
    },
    data() {
        return {
            apiGroupId: null,
            apiGroupList: [],
            apiConfigList: []
        }
    },
    created() {
        this.getApiGroupList()
    },
    methods: {
        getApiGroupList() {
            request('/sys/api-groups', {Method: 'GET'}).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.apiGroupList = data
                    })
                }
            })
        },
        getApiListByGroupId(groupId) {
            let url = '/sys/api-groups/' + groupId + '/api-configs'
            request(url, {Method: "GET"}).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.apiConfigList = data
                    })
                }
            })
        },
        changeApiGroup(groupId) {
            this.getApiListByGroupId(groupId)
        },
        createApiConfig() {
            this.$router.push({name: 'ApiConfigCreate'})
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
        }
    }
}
</script>

<style scoped>
</style>