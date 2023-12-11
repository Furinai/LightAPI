import {ElMessage, ElMessageBox} from 'element-plus'

export default function (url, config) {
    let param = config.param
    delete config.param
    if (typeof param !== 'undefined') {
        let searchParams = new URLSearchParams()
        for (const key of Object.keys(param)) {
            searchParams.append(key, param[key])
        }
        url = url + '?' + searchParams
    }
    if (typeof config.body !== 'undefined') {
        config.body = JSON.stringify(config.body)
    }
    if (typeof config.headers === 'undefined') {
        config.headers = new Headers()
    }
    config.headers.append('Content-type', 'application/json')
    let token = localStorage.getItem('token');
    if (token != null) {
        config.headers.append('Authorization', token)
    }
    let baseUrl = import.meta.env.VITE_BASE_API_URL
    return fetch(baseUrl + url, config).then(response => {
        if (!response.ok) {
            switch (response.status) {
                case 401:
                    getToken(baseUrl);
                    break;
                case 403:
                    ElMessage.error('目标接口被禁用！')
                    break;
                case 404:
                    ElMessage.error('目标接口不存在！')
                    break;
                case 405:
                    ElMessage.error('目标接口不允许此请求方法！')
                    break;
                case 500:
                    response.text().then(message => {
                        ElMessage.error(message)
                    })
                    break;
            }
        }
        return response
    })
}

function getToken(baseUrl) {
    ElMessageBox.prompt('请输入管理员密码').then(({value}) => {
        fetch(baseUrl + '/sys/user/token?password=' + value, {
            headers: {'Content-type': 'application/json'}
        }).then(response => {
            if (response.ok) {
                response.text().then(data => {
                    localStorage.setItem('token', data)
                    location.reload()
                })
            } else {
                response.text().then(message => {
                    ElMessage.error(message)
                    getToken(baseUrl)
                })
            }
        })
    }).catch(() => {
        ElMessage.error('已取消')
    })
}