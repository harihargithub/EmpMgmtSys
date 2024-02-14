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
      console.log('Sending login request');
      const response = await axios.post('/login', { username, password });
      console.log('Login response', response);

      // Get the token from the response
      const token = response.data.jwt;

      // Check if the token is undefined
      if (token === undefined) {
        console.error('Token is undefined');
      } else {
        console.log('Token:', token);
      }

      // Set the token in local storage
      localStorage.setItem('token', token);
      console.log('Token stored in local storage');

      // Set the Authorization header for future requests
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      console.log('Authorization header set');

      setLoggedIn(true);
      console.log('setLoggedIn called with true');
    } catch (error) {
      console.error('Error logging in', error);

      // Check if the error response status is 401
      if (error.response && error.response.status === 401) {
        console.log('401 response received');
        setLoggedIn(false);
        console.log('setLoggedIn called with false');
      }
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
        /* <Logout setLoggedIn={setLoggedIn} /> */
        <h2>Welcome back!</h2>
      )}
    </div>
  );
}

ConxApp.propTypes = {
  setLoggedIn: PropTypes.func.isRequired,
  isLoggedIn: PropTypes.bool.isRequired,
};

export default ConxApp;