<template>
    <div class="mb-2">
        <el-button type="primary" size="small" @click="createDataSourceConfig">
            新增
        </el-button>
    </div>
    <el-table :data="dataSourceConfigList" border>
        <el-table-column prop="name" label="名称" width="250"/>
        <el-table-column prop="jdbcUrl" label="URL"/>
        <el-table-column label="操作" width="250">
            <template #default="scope">
                <el-button type="warning" size="small" @click="updateDataSourceConfig(scope.row)" circle>
                    <el-icon>
                        <edit/>
                    </el-icon>
                </el-button>
                <el-button type="danger" size="small" @click="deleteDataSourceConfig(scope.row)" circle>
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
    name: "DataSourceConfigList",
    components: {
        Delete,
        DocumentAdd,
        Edit,
    },
    data() {
        return {
            dataSourceConfigList: []
        }
    },
    created() {
        this.getDataSourceConfigList()
    },
    methods: {
        getDataSourceConfigList() {
            request('/sys/data-source-configs', {method: 'GET'}).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.dataSourceConfigList = data
                    })
                }
            })
        },
        createDataSourceConfig() {
            this.$router.push({name: 'DataSourceConfigCreate'})
        },
        updateDataSourceConfig(dataSourceConfig) {
            this.$router.push({name: 'DataSourceConfigUpdate', params: {id: dataSourceConfig.id}})
        },
        deleteDataSourceConfig(dataSourceConfig) {
            this.$confirm('确定删除？').then(() => {
                let url = '/sys/data-source-configs/' + dataSourceConfig.id
                request(url, {method: 'DELETE'}).then(response => {
                    if (response.ok) {
                        let index = this.dataSourceConfigList.indexOf(dataSourceConfig)
                        this.dataSourceConfigList.splice(index, 1)
                        this.$message.success('删除成功！')
                    }
                })
            })
        }
    }
}
</script>

<style scoped>
</style>