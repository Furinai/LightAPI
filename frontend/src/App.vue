<template>
    <Layout/>
</template>

<script>
import Layout from './views/Layout.vue'

export default {
    name: 'App',
    components: {
        Layout
    }
}
</script>

<style>
.mt-2 {
    margin-top: 20px;
}

.mb-2 {
    margin-bottom: 20px;
}

.ml-2 {
    margin-left: 20px;
}

.mr-2 {
    margin-right: 20px;
}

.pagination {
    margin-top: 15px;
    justify-content: center;
}
</style>