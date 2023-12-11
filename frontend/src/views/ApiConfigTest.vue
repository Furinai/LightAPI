<template>
    <el-form label-position="top">
        <el-form-item label="URL:">
            <el-input v-model="completeRequestPath" disabled>
                <template #append>
                    <el-button @click="copyToBoard">复制</el-button>
                </template>
            </el-input>
        </el-form-item>
        <el-form-item label="请求参数:">
            <el-form-item v-for="param in apiConfig.paramConfigList" :label="param.name">
                <div v-if="param.type==='日期时间'">
                    <el-date-picker v-model="param.value"
                                    type="datetime" value-format="YYYY-MM-DD HH:mm:ss"/>
                </div>
                <div v-else-if="param.type==='字符串'">
                    <el-input v-model="param.value"/>
                </div>
                <div v-else>
                    <el-input v-model.number="param.value"/>
                </div>
            </el-form-item>
            <el-form-item v-if:="apiConfig.pageable" label="PageNum">
                <el-input v-model="requestParam.pageNumber"/>
            </el-form-item>
            <el-form-item v-if:="apiConfig.pageable" label="PageSize">
                <el-input v-model="requestParam.pageSize"/>
            </el-form-item>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="testApi">测试</el-button>
        </el-form-item>
        <el-form-item v-show="responseContent !== ''" label="响应内容">
            <el-input type="textarea" v-model="responseContent" :autosize="{minRows: 6}"/>
        </el-form-item>
    </el-form>
</template>

<script>
import request from '../utils/request'

export default {
    name: 'ApiConfigTest',
    data() {
        return {
            apiConfig: {},
            completeRequestPath: '',
            requestParam: {},
            responseContent: '',
            responseStatus: '',
            responseStatusText: ''
        }
    },
    created() {
        this.getApiConfig(this.$route.params.id)
    },
    methods: {
        getApiConfig(id) {
            request('/sys/api-configs/' + id, {method: 'GET'}).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.apiConfig = data
                        let baseUrl = import.meta.env.VITE_BASE_API_URL
                        this.completeRequestPath = baseUrl + data.requestPath
                    })
                }
            })
        },
        testApi: function () {
            this.apiConfig.paramConfigList.map(param => {
                this.requestParam[param.key] = param.value
            })
            let requestMethod = this.apiConfig.requestMethod
            let config = {method: requestMethod}
            if (requestMethod === 'GET' || requestMethod === 'DELETE') {
                config.param = this.requestParam
            }
            if (requestMethod === 'POST' || requestMethod === 'PUT') {
                config.body = this.requestParam
            }
            request(this.apiConfig.requestPath, config).then(response => {
                if (response.ok) {
                    this.$message.success('OK')
                    response.text().then(data => {
                        if (data != null && data !== '') {
                            this.responseContent = JSON.stringify(JSON.parse(data), null, 4)
                        }
                    })
                }
            })
        },
        copyToBoard() {
            document.execCommand('copy', false, this.completeRequestPath)
            this.$message.success('复制成功')
        }
    }
}
</script>

<style scoped>
</style>