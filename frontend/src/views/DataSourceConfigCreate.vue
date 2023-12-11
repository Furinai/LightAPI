<template>
    <DataSourceConfigEdit @submitForm="createDataSourceConfig" :initialDataSourceConfig="dataSourceConfig"/>
</template>

<script>
import DataSourceConfigEdit from '../components/DataSourceConfigEdit.vue'
import request from '../utils/request'

export default {
    name: 'DataSourceConfigCreate',
    components: {
        DataSourceConfigEdit
    },
    data() {
        return {
            dataSourceConfig: {}
        }
    },
    methods: {
        createDataSourceConfig(dataSourceConfig) {
            let config = {method: 'POST', body: dataSourceConfig}
            request('/sys/data-source-configs', config).then(response => {
                if (response.ok) {
                    this.$message.success('新增成功！')
                }
            })
        }
    }
}
</script>

<style scoped>
</style>