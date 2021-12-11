import axios from 'axios'
import cookie from "js-cookie";
// 创建axios实例
const service = axios.create({
    baseURL: 'http://localhost:9001', // api的base_url 这里写nginx地址
    timeout: 20000 // 请求超时时间
})

//设置拦截器，每次请求带着token
service.interceptors.request.use(
  config=>{
  //  判断cookie里面是否有fkStore这个key，如果有，设置token放入header中
    if(cookie.get("fkStoreToken")){
      config.headers["token"]=cookie.get("fkStoreToken");
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

export default service
