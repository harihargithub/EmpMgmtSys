import { useState, useEffect, useCallback, useRef } from 'react';
import axios from 'axios';
import { GridComponent, ColumnsDirective, ColumnDirective, Inject, Page, Edit, Toolbar, Selection } from '@syncfusion/ej2-react-grids';
import { DropDownListComponent } from '@syncfusion/ej2-react-dropdowns';

function EmployeeList() {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const gridInstance = useRef(null);
  const dropDownInstance = useRef(null);
  const droplist = [
    { text: 'Top', value: 'Top' },
    { text: 'Bottom', value: 'Bottom' }
  ];

  const getEmployees = useCallback(async () => {
    try {
      setLoading(true);
      const response = await axios.get('/employees/list');
      setEmployees(response.data);
    } catch (error) {
      console.error('Error fetching employees', error);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    getEmployees();
  }, [getEmployees]);

  const actionComplete = async (args) => {
    if (args.requestType === 'save') {
      if (args.action === 'add') {
        try {
          await axios.post('/employees/save', args.data);
        } catch (error) {
          console.error('Error adding employee', error);
        }
      } else if (args.action === 'edit') {
        try {
          await axios.put(`/employees/update/${args.data.id}`, args.data);
        } catch (error) {
          console.error('Error updating employee', error);
        }
      }
      getEmployees();
    } else if (args.requestType === 'delete') {
      const id = args.data[0].id;
    console.log('Deleting employee with ID:', id);
      try {
        await axios.post(`/employees/delete?id=${id}`);
      } catch (error) {
        console.error('Error deleting employee', error);
      }
      getEmployees();
    }
  };

  const ddChange = () => {
    gridInstance.current.editSettings.newRowPosition = dropDownInstance.current.value;
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className='control-pane'>
      <div className='control-section'>
        <div className='col-md-9'>
          <GridComponent dataSource={employees} ref={gridInstance} toolbar={['Add', 'Edit', 'Delete', 'Update', 'Cancel']} allowPaging={true} editSettings={{ allowEditing: true, allowAdding: true, allowDeleting: true, newRowPosition: 'Top' }} actionComplete={actionComplete}>
            <ColumnsDirective>
              <ColumnDirective field='id' headerText='Employee ID' isPrimaryKey={true} width='130' />
              <ColumnDirective field='FirstName' headerText='First Name' width='230' />
              <ColumnDirective field='LastName' headerText='Last Name' width='170' />
              <ColumnDirective field='Email' headerText='Email' width='230' />
            </ColumnsDirective>
            <Inject services={[Page, Edit, Toolbar, Selection]} />
          </GridComponent>
        </div>
        <div className='col-md-3 property-section'>
          <table id='property' title='Properties' className='property-panel-table' style={{ width: '100%', marginBottom: '20px' }}>
            <tbody>
              <tr>
                <td>
                  <div>Add New Row Position</div>
                </td>
                <td>
                  <div>
                    <DropDownListComponent id="newRowPosition" width="120px" index={0} change={ddChange} ref={dropDownInstance} dataSource={droplist} fields={{ text: 'text', value: 'value' }}/>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default EmployeeList;