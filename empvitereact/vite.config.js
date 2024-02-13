import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/employees/list': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      '/employees/save': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      '/employees/update': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        rewrite: (path) => path
      },
      '/employees/delete': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      '/login': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      '/logout': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})