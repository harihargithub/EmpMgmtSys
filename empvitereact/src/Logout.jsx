import { useCallback } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';

function Logout({ setLoggedIn }) {
  const logout = useCallback(async () => {
    console.log('handleLogout called');

    try {
      const token = localStorage.getItem('token');
      
      if (token) {
        console.log('Logging out with token', token);

        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

        try {
          const response = await axios.post(
            'http://localhost:5173/logout', // add the full URL here
            {},
            {
              headers: {
                'Authorization': `Bearer ${token}`
              }
            }
          );

          console.log('Logout response', response);
        } catch (error) {
          console.error('Error logging out', error);
        }

        localStorage.removeItem('token');
        setLoggedIn(false);
      } else {
        console.log('No token found, skipping logout request');
      }
    } catch (error) {
      console.error('Error logging out', error);
    }
  }, [setLoggedIn]);

  return (
    <button onClick={logout}>Logout</button>
  );
}

Logout.propTypes = {
  setLoggedIn: PropTypes.func.isRequired,
};

export default Logout;