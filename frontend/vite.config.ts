import { defineConfig } from 'vite'
import react, { reactCompilerPreset } from '@vitejs/plugin-react'
import babel from '@rolldown/plugin-babel'
import basicSsl from '@vitejs/plugin-basic-ssl' // 1. Import the plugin

export default defineConfig({
  plugins: [
    react(),
    babel({
      presets: [reactCompilerPreset()]
    }),
    basicSsl() // 2. Add it to the plugins array
  ],
  server: {
    host: true // Keeps the network hosting active
  }
})
