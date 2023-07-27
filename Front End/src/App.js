import React from 'react';
import './style.css';
import { BrowserRouter as Router } from 'react-router-dom';

import Layout from './components/Layout';

export default function App() {
  return (
      <Router>
        <Layout />
      </Router>
  );
}
