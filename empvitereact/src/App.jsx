import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { useState } from 'react';
import ConxApp from './ConxApp';
import EmployeeList from './EmployeeList';
import Logout from './Logout';

function App() {
  const [isLoggedIn, setLoggedIn] = useState(false);

  return (
    <Router>
      <Routes>
        {isLoggedIn ? (
          <>
            <Route path="/emplist" element={<EmployeeList />} />
            <Route path="/logout" element={<Logout setLoggedIn={setLoggedIn} />} />
          </>
        ) : (
          <Route path="/login" element={<ConxApp setLoggedIn={setLoggedIn} />} />
        )}
      </Routes>
    </Router>
  );
}

export default App;