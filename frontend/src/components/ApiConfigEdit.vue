<template>
    <el-steps class="mb-2" :active="step" finish-status="success">
        <el-step></el-step>
        <el-step></el-step>
        <el-step></el-step>
    </el-steps>
    <el-form :model="apiConfig" :rules="validationRule" ref="apiConfigForm" label-position="top">
        <div v-if="step === 0">
            <el-form-item label="分组" prop="groupId">
                <el-select v-model="apiConfig.groupId">
                    <el-option v-for="apiGroup in apiGroupList" :key="apiGroup.id"
                               :label="apiGroup.name" :value="apiGroup.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="名称" prop="name">
                <el-input v-model="apiConfig.name"></el-input>
            </el-form-item>
            <el-form-item label="请求方法" prop="requestMethod">
                <el-select v-model="apiConfig.requestMethod">
                    <el-option v-for="requestMethod in requestMethodList"
                               :label="requestMethod" :value="requestMethod">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="请求路径" prop="requestPath">
                <el-input v-model="apiConfig.requestPath"></el-input>
            </el-form-item>
            <el-form-item v-if="apiConfig.requestMethod === 'GET'" label="是否列表" prop="listable">
                <el-switch v-model="apiConfig.listable"></el-switch>
            </el-form-item>
            <el-form-item v-if="apiConfig.listable" label="是否分页" prop="pageable">
                <el-switch v-model="apiConfig.pageable"></el-switch>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="nextStep('apiConfigForm')">下一步</el-button>
            </el-form-item>
        </div>
        <div v-if="step === 1">
            <div v-for="(param, index) in apiConfig.paramConfigList">
                <el-form-item label="名称" :prop="'paramConfigList.' + index + '.name'" :rules="{
                    required: true, message: '请输入名称', trigger: 'blur',
                    min: 1, max: 20, message: '名称长度在 1 到 20 个字符之间', trigger: 'blur'}">
                    <el-input v-model="param.name"></el-input>
                </el-form-item>
                <el-form-item label="键" :prop="'paramConfigList.' + index + '.key'" :rules="{
                    required: true, message: '请输入键', trigger: 'blur',
                    min: 1, max: 20, message: '键长度在 1 到 20 个字符之间', trigger: 'blur'}">
                    <el-input v-model="param.key"></el-input>
                </el-form-item>
                <el-form-item label="类型" :prop="'paramConfigList.' + index + '.type'" :rules="{
                        required: true, message: '请选择类型', trigger: 'change'}">
                    <el-select v-model="param.type">
                        <el-option v-for="paramType in apiParamTypeList"
                                   :label="paramType" :value="paramType">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否必需">
                    <el-switch v-model="param.required"></el-switch>
                </el-form-item>
                <el-form-item label="是否数组">
                    <el-switch v-model="param.listable"></el-switch>
                </el-form-item>
                <el-form-item>
                    <el-button size="small" type="danger" @click="removeParam(index)">删除本项</el-button>
                </el-form-item>
                <el-divider></el-divider>
            </div>
            <el-form-item>
                <el-button type="primary" @click="previousStep">上一步</el-button>
                <el-button type="primary" @click="nextStep('apiConfigForm')">下一步</el-button>
                <el-button type="primary" @click="addParam">新增参数</el-button>
            </el-form-item>
        </div>
        <div v-if="step === 2">
            <el-form-item label="数据源" prop="dataSourceConfigId">
                <el-select v-model="apiConfig.dataSourceConfigId">
                    <el-option v-for="dataSourceConfig in dataSourceConfigList" :key="dataSourceConfig.id"
                               :label="dataSourceConfig.name" :value="dataSourceConfig.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="SQL">
                <el-input type="textarea" :autosize="{ minRows: 15, maxRows: 20}" v-model="apiConfig.sqlStatement"
                          :input-style="{'font-family': 'Consolas', 'font-size': '20px', color: '#409EFF'}"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="previousStep">上一步</el-button>
                <el-button type="primary" @click="submitForm('apiConfigForm')">提交</el-button>
            </el-form-item>
        </div>
    </el-form>
</template>

<script>
import request from "../utils/request";

export default {
    name: "ApiConfigEdit",
    props: [
        'initialApiConfig'
    ],
    emits: [
        'submitForm'
    ],
    data() {
        return {
            step: 0,
            apiGroupList: [],
            apiParamTypeList: [],
            dataSourceConfigList: [],
            requestMethodList: [
                'GET', "POST", "PUT", "DELETE"
            ],
            validationRule: {
                groupId: [
                    {required: true, message: '请选择分组', trigger: 'change'},
                ],
                name: [
                    {required: true, message: '请输入名称', trigger: 'blur'},
                    {min: 1, max: 20, message: '名称长度在 1 到 20 个字符之间', trigger: 'blur'}
                ],
                requestMethod: [
                    {required: true, message: '请选择分组', trigger: 'change'},
                ],
                requestPath: [
                    {required: true, message: '请输入请求路径', trigger: 'blur'},
                    {min: 1, max: 50, message: '请求路径长度在 1 到 50 个字符之间', trigger: 'blur'}
                ],
                dataSourceConfigId: [
                    {required: true, message: '请选择数据源', trigger: 'change'},
                ],
                sqlStatement: [
                    {required: true, message: '请输入SQL语句', trigger: 'blur'},
                    {min: 10, max: 5000, message: '名称长度在 10 到 5000 个字符之间', trigger: 'blur'}
                ],
            }
        }
    },
    computed: {
        apiConfig() {
            return this.initialApiConfig
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
        getParamTypeList() {
            request('/sys/api-param-configs/types', {Method: 'GET'}).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.apiParamTypeList = data
                    })
                }
            })
        },
        getDataSourceConfigList() {
            request('/sys/data-source-configs', {Method: 'GET'}).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.dataSourceConfigList = data
                    })
                }
            })
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$emit('submitForm', this.apiConfig)
                }
            })
        },
        addParam() {
            this.apiConfig.paramConfigList.push({
                name: '',
                key: '',
                type: '',
                listable: false,
                required: false
            })
        },
        removeParam(index) {
            this.apiConfig.paramConfigList.splice(index, 1)
        },
        previousStep() {
            this.step--
        },
        nextStep(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.step++
                    if (this.step === 1) {
                        this.getParamTypeList()
                    }
                    if (this.step === 2) {
                        this.getDataSourceConfigList()
                    }
                }
            })
        }
    }
}
</script>

<style scoped>
.codeEditor {
    font-size: 18px;
    color: #409EFF !important;
}
</style>