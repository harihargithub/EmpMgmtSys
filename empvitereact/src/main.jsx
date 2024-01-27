
import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { registerLicense } from '@syncfusion/ej2-base';

// .env - VITE_SYNCFUSION_LICENSE_KEY='Your SF Licence Key'
registerLicense(import.meta.env.VITE_SYNCFUSION_LICENSE_KEY);

console.log('Executing main.jsx');

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)
