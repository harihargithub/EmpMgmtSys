import { useEffect } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';

function Logout({ setLoggedIn }) {
  useEffect(() => {
    const handleLogout = async () => {
      console.log('handleLogout called');

      try {
        const token = localStorage.getItem('token');

        console.log('Logging out with token', token);

        const response = await axios.post(
          '/logout',
          {},
          {
            headers: {
              'Authorization': `Bearer ${token}`
            }
          }
        );

        console.log('Logout response', response);

        localStorage.removeItem('token');
        setLoggedIn(false);
      } catch (error) {
        console.error('Error logging out', error);
      }
    };

    handleLogout();
  }, [setLoggedIn]);

  return null;
}

Logout.propTypes = {
  setLoggedIn: PropTypes.func.isRequired,
};

export default Logout;