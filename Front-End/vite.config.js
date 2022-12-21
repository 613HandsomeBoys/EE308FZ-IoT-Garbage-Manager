import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import * as path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
	resolve: {
    	alias: {
			'@': path.resolve(__dirname, 'src'),
		}
	},
	plugins: [vue()],
	server: {
		host: '0.0.0.0',
		port: 3000,
		proxy: {
		  '/api': {
			target: 'http://api.crap.cn/',
			changeOrigin: true,
			rewrite: (path) => path.replace(/^\/api/, '')
		  },
		}
	}
})