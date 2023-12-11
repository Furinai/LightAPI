<template>
    <el-form :model="apiGroup" :rules="validationRule" ref="apiGroupForm" label-position="top">
        <el-form-item label="名称" prop="name">
            <el-input v-model="apiGroup.name"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm('apiGroupForm')">提交</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
export default {
    name: "ApiGroupEdit",
    props: [
        'initialApiGroup'
    ],
    emits:[
        'submitForm'
    ],
    data() {
        return {
            validationRule: {
                name: [
                    {required: true, message: '请输入名称', trigger: 'blur'},
                    {min: 1, max: 20, message: '名称长度在 1 到 20 个字符之间', trigger: 'blur'}
                ]
            }
        }
    },
    computed: {
        apiGroup() {
            return this.initialApiGroup
        }
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$emit('submitForm', this.apiGroup)
                }
            })
        }
    }
}
</script>

<style scoped>
</style>