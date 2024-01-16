import { useState } from 'react';
import axios from 'axios';
import Logout from './Logout';
import PropTypes from 'prop-types';  // Import PropTypes

function ConxApp({ setLoggedIn, isLoggedIn }) {  // Use isLoggedIn prop
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async (event) => {
    console.log('handleLogin called');
    event.preventDefault();

    try {
      const response = await axios.post('/login', { username, password });
      console.log('Login response', response);

      const token = response.data.token;

      if (!token) {
        console.error('No token in response');
        return;
      }

      localStorage.setItem('token', token);
      console.log('Token stored in local storage');
      setLoggedIn(true);
    } catch (error) {
      console.error('Error logging in', error);
    }
  };

  return (
    <div>
      {!isLoggedIn ? (  // Use isLoggedIn prop
        <form onSubmit={handleLogin}>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="Username"
          />
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Password"
          />
          <button type="submit">Login</button>
        </form>
      ) : (
        <Logout setLoggedIn={setLoggedIn} />
      )}
    </div>
  );
}

ConxApp.propTypes = {
  setLoggedIn: PropTypes.func.isRequired,
  isLoggedIn: PropTypes.bool.isRequired,
};

export default ConxApp;