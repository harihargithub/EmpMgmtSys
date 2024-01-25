import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import EmployeeList from './EmployeeList';
import Logout from './Logout';
import ConxApp from './ConxApp';

function App() {
  const [isLoggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setLoggedIn(true);
    }
  }, []);

  return (
    <Router>
      {isLoggedIn && <Logout setLoggedIn={setLoggedIn} />}
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/emplist" element={<EmployeeList isLoggedIn={isLoggedIn} setLoggedIn={setLoggedIn} />} />
        <Route path="/login" element={<ConxApp isLoggedIn={isLoggedIn} setLoggedIn={setLoggedIn} />} />
      </Routes>
    </Router>
  );
}

export default App;