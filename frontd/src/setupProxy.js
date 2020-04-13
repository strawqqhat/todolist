const proxy = require('http-proxy-middleware');
module.exports = function (app) {
    app.use(
        '/api',
        proxy.createProxyMiddleware({
            target: 'http://123.57.34.206/task',
            changeOrigin: true,
            pathRewrite: {
                '^/api': ''
            }
        })
    );
};