<template>
    <el-form :model="dataSourceConfig" :rules="validationRule" ref="dataSourceConfigForm" label-position="top">
        <el-form-item prop="type" label="类型">
            <el-select placeholder="请选择类型" v-model="dataSourceConfig.type" @change="changeType">
                <el-option v-for="item in dataSourceTypeList" :label="item" :value="item">
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="name">
            <el-input v-model="dataSourceConfig.name"></el-input>
        </el-form-item>
        <el-form-item label="URL" prop="url">
            <el-input v-model="dataSourceConfig.url"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
            <el-input v-model="dataSourceConfig.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input v-model="dataSourceConfig.password" show-password></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="success" @click="testConnection('dataSourceConfigForm')">测试</el-button>
            <el-button type="primary" @click="submitForm('dataSourceConfigForm')">提交</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import request from '../utils/request'

export default {
    name: "DataSourceConfigEdit",
    props: {
        'initialDataSourceConfig': {
            type: Object,
            default() {
                return {}
            }
        }
    },
    emits: [
        'submitForm'
    ],
    data() {
        return {
            dataSourceTypeList: [
                'Elasticsearch', 'MySQL', 'MariaDB', 'PostgreSQL', 'Oracle', 'MSSQL', '其它'
            ],
            validationRule: {
                type: [
                    {required: true, message: '请选择类型', trigger: 'change'}
                ],
                name: [
                    {required: true, message: '请输入名称', trigger: 'blur'},
                    {min: 1, max: 20, message: '名称长度在 1 到 20 个字符之间', trigger: 'blur'}
                ],
                url: [
                    {required: true, message: '请输入URL', trigger: 'blur'},
                    {min: 1, max: 200, message: 'URL长度在 1 到 200 个字符之间', trigger: 'blur'}
                ],
                username: [
                    {required: true, message: '请输入用户名', trigger: 'blur'},
                    {min: 1, max: 20, message: '用户名长度在 1 到 20 个字符之间', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    {min: 1, max: 20, message: '密码长度在 1 到 20 个字符之间', trigger: 'blur'}
                ]
            }
        }
    },
    computed: {
        dataSourceConfig() {
            return this.initialDataSourceConfig
        }
    },
    methods: {
        testConnection(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    let config = {method: 'POST', body: this.dataSourceConfig}
                    request('/sys/data-sources', config).then(response => {
                        if (response.ok) {
                            this.$message.success('连接成功！')
                        }
                    })
                }
            })
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$emit('submitForm', this.dataSourceConfig)
                }
            })
        },
        changeType(value) {
            switch (value) {
                case 'Elasticsearch':
                    this.dataSourceConfig.url = 'http://localhost:9200'
                    break;
                case 'MongoDB':
                    this.dataSourceConfig.url = 'mongodb://localhost:27017/test'
                    break;
                case 'MySQL':
                    this.dataSourceConfig.url = 'jdbc:mysql://localhost:3306/test'
                    break;
                case 'MariaDB':
                    this.dataSourceConfig.url = 'jdbc:mariadb://localhost:3306/test'
                    break;
                case 'PostgreSQL':
                    this.dataSourceConfig.url = 'jdbc:postgresql://localhost:5432/test'
                    break;
                case 'Oracle':
                    this.dataSourceConfig.url = 'jdbc:oracle:thin:@localhost:1521:XE'
                    break;
                case 'MSSQL':
                    this.dataSourceConfig.url = 'jdbc:sqlserver://localhost\;database=test'
                    break;
                default:
                    this.dataSourceConfig.url = ''
            }
        }
    }
}
</script>

<style scoped>
</style>