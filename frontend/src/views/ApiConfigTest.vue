<template>
    <el-form label-position="top">
        <el-form-item label="URL:">
            <el-input v-model="completeRequestPath" disabled>
                <template #append>
                    <el-button @click="copyToBoard">复制</el-button>
                </template>
            </el-input>
        </el-form-item>
        <el-form-item v-for="param in apiConfig.paramConfigList" :label="param.name">
            <div v-if="param.type==='日期时间'">
                <div v-if="param.listable">
                    <div v-for="(item, index) in param.values ">
                        <el-date-picker v-model="item.value" type="datetime" value-format="YYYY-MM-DD HH:mm:ss"/>
                        <el-button size="small" type="danger" class="removeButton"
                                   @click="removeParamItem(param.values, index)">
                            <el-icon>
                                <delete/>
                            </el-icon>
                        </el-button>
                    </div>
                    <el-button size="small" type="success" @click="addParamItem(param.values)">
                        <el-icon>
                            <plus/>
                        </el-icon>
                    </el-button>
                </div>
                <div v-else>
                    <el-date-picker v-model="param.value" type="datetime" value-format="YYYY-MM-DD HH:mm:ss"/>
                </div>
            </div>
            <div v-else-if="param.type==='字符串'">
                <div v-if="param.listable">
                    <div v-for="(item, index) in param.values ">
                        <el-input v-model="item.value"/>
                        <el-button size="small" type="danger" class="removeButton"
                                   @click="removeParamItem(param.values, index)">
                            <el-icon>
                                <delete/>
                            </el-icon>
                        </el-button>
                    </div>
                    <el-button size="small" type="success" @click="addParamItem(param.values)">
                        <el-icon>
                            <plus/>
                        </el-icon>
                    </el-button>
                </div>
                <div v-else>
                    <el-input v-model="param.value"/>
                </div>
            </div>
            <div v-else>
                <div v-if="param.listable">
                    <div v-for="(item, index) in param.values">
                        <el-input v-model.number="item.value"/>
                        <el-button size="small" type="danger" class="removeButton"
                                   @click="removeParamItem(param.values, index)">
                            <el-icon>
                                <delete/>
                            </el-icon>
                        </el-button>
                    </div>
                    <el-button size="small" type="success" @click="addParamItem(param.values)">
                        <el-icon>
                            <plus/>
                        </el-icon>
                    </el-button>
                </div>
                <div v-else>
                    <el-input v-model.number="param.value"/>
                </div>
            </div>
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
import {Delete, Plus} from '@element-plus/icons-vue'

export default {
    name: 'ApiConfigTest',
    components: {Delete, Plus},
    data() {
        return {
            apiConfig: {},
            completeRequestPath: '',
            requestParam: {},
            responseContent: ''
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
                        this.completeRequestPath = import.meta.env.VITE_BASE_API_URL + data.requestPath
                        this.apiConfig.paramConfigList.forEach(paramConfig => {
                            if (paramConfig.listable) {
                                paramConfig.values = []
                            }
                        })
                    })
                }
            })
        },
        testApi() {
            this.apiConfig.paramConfigList.map(param => {
                if (param.listable) {
                    param.value = []
                    for (const item of param.values) {
                        if (item.value != null) {
                            param.value.push(item.value)
                        }
                    }
                    if (param.value.length === 0) {
                        param.value = null
                    }
                }
                if (param.value != null) {
                    this.requestParam[param.key] = param.value
                }
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
                        this.requestParam = {}
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
        },
        addParamItem(values) {
            values.push({value: null})
        },
        removeParamItem(values, index) {
            values.splice(index, 1)
        }
    }
}
</script>

<style scoped>
.removeButton {
    display: block;
    margin-top: 10px;
    margin-bottom: 10px;
}
</style>