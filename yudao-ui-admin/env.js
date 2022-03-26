const env = {
    active: 'dev',//preview,dev
    baseUrl: '/design',
    fileUrl: process.env.VUE_APP_BASE_API + "/admin-api/infra/file/",
    fileUrlUpload: process.env.VUE_APP_BASE_API + "/admin-api/infra/file/upload/3",
    version: '1.8.5',
}
export const baseUrl = env.baseUrl
export const fileUrl = process.env.VUE_APP_BASE_API + "/admin-api/infra/file/"
export const fileUrlUpload = process.env.VUE_APP_BASE_API + "/admin-api/infra/file/upload/3"
export default env
