var webpack = require('webpack');

module.exports = {
  publicPath: (function () {
    if (process.env.NODE_ENV === 'production') return ''
    if (process.env.NODE_ENV === 'development') return ''
    if (process.env.NODE_ENV === 'test') return '/jmsight-admin-poc/'
  })(),
  pages: {
    index: {
      entry: 'src/main.js',
      template: 'public/index.html'
    }
  },
  devServer: {
    proxy: {
      '/_api': {
        target: process.env.VUE_APP_PROXY_URL, // this configuration needs to correspond to the Spring Boot backends' application.properties server.port
        ws: false,
        changeOrigin: true,
      },
    },
  },
  configureWebpack : {
    plugins: [
      new webpack.ProvidePlugin({
        'window.Quill': 'quill/dist/quill.js',
        'Quill': 'quill/dist/quill.js'
      })
    ]
  }
};
