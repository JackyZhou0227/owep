import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import VueSetupExtend from 'vite-plugin-vue-setup-extend'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import fs from 'fs'
  const optimizeDepsElementPlusIncludes = ['element-plus/es','schart.js','vue-cropperjs','@wangeditor/editor-for-vue','@vueuse/core']
  fs.readdirSync("node_modules/element-plus/es/components").map((dirname) => {
    fs.access(
      `node_modules/element-plus/es/components/${dirname}/style/css.mjs`,
      (err) => {
        if (!err) {
          // console.log(1111)
          optimizeDepsElementPlusIncludes.push(
            `element-plus/es/components/${dirname}/style/css`
          )
        }
      }
    )
  })

// https://vitejs.dev/config/
export default defineConfig({
  /*  build: {
    rollupOptions: {
      output: {
        manualChunks: id => {
          // 将 node_modules 中的代码单独打包成一个 JS 文件
          if(id.includes('node_modules')) {
            return 'vendor'
          }
        }
      }
    }
  }, */

  plugins: [
    vue(),
    VueSetupExtend(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },

  optimizeDeps: {
		include: optimizeDepsElementPlusIncludes,
	},
  server: {
    proxy: {
      '/api': {
        // target: 'http://192.168.2.139:9090/owep', // 凡是遇到 /api 路径的请求，都映射到 target 属性
        target: ' http://localhost:9090/owep', // 凡是遇到 /api 路径的请求，都映射到 target 属性
        // target: ' http://192.168.2.145:9090/owep/', // 凡是遇到 /api 路径的请求，都映射到 target 属性
        changeOrigin: true,
        rewrite: path => path.replace(/^\/api/, '') // 重写 api 为 空，就是去掉它
      }
    }
  },

})
