import React from 'react';

class EmployeeForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            id: '',
            firstName: '',
            lastName: '',
            email: ''
        };
    }

    // Add methods to handle form submission and input changes...

    render() {
        return (
            <div className="container">
                <h3>Employee Directory</h3>
                <hr />
                <p className="h4 mb-4">Save Employee</p>
                <form action="#">
                    <input type="hidden" value={this.state.id} />

                    <input type="text" value={this.state.firstName} className="form-control mb-4 col-4" placeholder="First Name" />

                    <input type="text" value={this.state.lastName} className="form-control mb-4 col-4" placeholder="Last Name" />

                    <input type="text" value={this.state.email} className="form-control mb-4 col-4" placeholder="Email" />

                    <button type="submit" className="btn btn-info col-2">Save</button>
                </form>

                <hr />
                <a href="/employees/list">Back to Employees List</a>
            </div>
        );
    }
}

export default EmployeeForm;

// Need to inlcude methods for handling form submission or input changes @EmployeeForm.jsx to make the form functional. Also need to handle the form submission in your React application instead of submitting the form to the server directly.