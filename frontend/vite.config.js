import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/api': {
        // target: 'http://10.4.56.127:8080',
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
        // rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})
