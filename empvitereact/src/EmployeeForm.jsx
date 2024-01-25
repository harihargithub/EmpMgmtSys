import  { useState, useEffect } from 'react';
import axios from 'axios';
import { useHistory, useParams } from 'react-router-dom';

const EmployeeForm = () => {
    const [employee, setEmployee] = useState({ id: '', firstName: '', lastName: '', email: '' });
    const history = useHistory();
    const { id } = useParams();

    useEffect(() => {
        if (id) {
            axios.get(`/employees/${id}`)
                .then(response => {
                    setEmployee(response.data);
                })
                .catch(error => {
                    console.error('Error fetching data: ', error);
                    alert('Error fetching data');
                });
        }
    }, [id]);

    const handleInputChange = (event) => {
        setEmployee({
            ...employee,
            [event.target.name]: event.target.value
        });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        const token = localStorage.getItem('token'); // Get the token from local storage
        const config = {
            headers: { Authorization: `Bearer ${token}` } // Set the token in the headers
        };

        try {
            if (employee.id) {
                // Update existing employee
                await axios.put(`/employees/update/${employee.id}`, employee, config);
            } else {
                // Add new employee
                await axios.post('/employees/save', employee, config);
            }
            history.push('/employees/list');
        } catch (error) {
            console.error('Error saving data: ', error);
            alert('Error saving data');
        }
    };

    return (
        <div className="container">
            <h3>Employee Directory</h3>
            <hr />
            <p className="h4 mb-4">Save Employee</p>
            <form onSubmit={handleSubmit}>
                <input type="hidden" value={employee.id} />

                <input type="text" name="firstName" value={employee.firstName} onChange={handleInputChange} className="form-control mb-4 col-4" placeholder="First Name" />

                <input type="text" name="lastName" value={employee.lastName} onChange={handleInputChange} className="form-control mb-4 col-4" placeholder="Last Name" />

                <input type="text" name="email" value={employee.email} onChange={handleInputChange} className="form-control mb-4 col-4" placeholder="Email" />

                <button type="submit" className="btn btn-info col-2">Save</button>
            </form>

            <hr />
            <a href="/employees/list">Back to Employees List</a>
        </div>
    );
};

export default EmployeeForm;