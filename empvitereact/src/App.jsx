import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { useState } from 'react';
import ConxApp from './ConxApp';
import EmployeeList from './EmployeeList';
import Logout from './Logout';

function App() {
  const [isLoggedIn, setLoggedIn] = useState(false);

  return (
    <Router>
      {isLoggedIn && <Logout setLoggedIn={setLoggedIn} />}
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/emplist" element={<EmployeeList />} />
        <Route path="/login" element={<ConxApp isLoggedIn={isLoggedIn} setLoggedIn={setLoggedIn} />} />
      </Routes>
    </Router>
  );
}

export default App;