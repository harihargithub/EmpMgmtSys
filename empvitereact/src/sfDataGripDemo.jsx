import { createRoot } from 'react-dom/client';
import './index.css';
import * as React from 'react';
import { GridComponent, ColumnsDirective, ColumnDirective, Inject, Page, Edit, Toolbar } from '@syncfusion/ej2-react-grids';
import { orderDataSource } from './data';

import { PropertyPane } from './property-pane';
import { DropDownListComponent } from '@syncfusion/ej2-react-dropdowns';
function NormalEdit() {
    const toolbarOptions = ['Add', 'Edit', 'Delete', 'Update', 'Cancel'];
    const editSettings = { allowEditing: true, allowAdding: true, allowDeleting: true, newRowPosition: 'Top' };
    const editparams = { params: { popupHeight: '300px' } };
    const validationRule = { required: true };
    const orderidRules = { required: true, number: true };
    const pageSettings = { pageCount: 5 };
    const format = { type: 'dateTime', format: 'M/d/y hh:mm a' };
    let gridInstance;
    let dropDownInstance;
    const droplist = [
        { text: 'Top', value: 'Top' },
        { text: 'Bottom', value: 'Bottom' }
    ];
    function actionBegin(args) {
        if (args.requestType === 'save') {
            if (gridInstance.pageSettings.currentPage !== 1 && gridInstance.editSettings.newRowPosition === 'Top') {
                args.index = (gridInstance.pageSettings.currentPage * gridInstance.pageSettings.pageSize) - gridInstance.pageSettings.pageSize;
            }
            else if (gridInstance.editSettings.newRowPosition === 'Bottom') {
                args.index = (gridInstance.pageSettings.currentPage * gridInstance.pageSettings.pageSize) - 1;
            }
        }
    }
    function ddChange() {
        gridInstance.editSettings.newRowPosition = dropDownInstance.value;
    }
    return (<div className='control-pane'>
      <div className='control-section'>
        <div className='col-md-9'>
          <GridComponent dataSource={orderDataSource} ref={grid => gridInstance = grid} toolbar={toolbarOptions} allowPaging={true} editSettings={editSettings} pageSettings={pageSettings} actionBegin={actionBegin.bind(this)}>
            <ColumnsDirective>
              <ColumnDirective field='OrderID' headerText='Order ID' width='140' textAlign='Right' validationRules={orderidRules} isPrimaryKey={true}></ColumnDirective>
              <ColumnDirective field='CustomerName' headerText='Customer Name' width='150' validationRules={validationRule}></ColumnDirective>
              <ColumnDirective field='Freight' headerText='Freight' width='140' format='C2' textAlign='Right' editType='numericedit'></ColumnDirective>
              <ColumnDirective field='OrderDate' headerText='Order Date' editType='datetimepickeredit' format={format} width='160'></ColumnDirective>
              <ColumnDirective field='ShipCountry' headerText='Ship Country' width='150' editType='dropdownedit' edit={editparams}></ColumnDirective>
            </ColumnsDirective>
            <Inject services={[Page, Toolbar, Edit]}/>
          </GridComponent>
        </div>

        <div className='col-md-3 property-section'>
          <PropertyPane title='Properties'>
            <table id='property' title='Properties' className='property-panel-table' style={{ width: '100%', marginBottom: '20px' }}>
              <tbody>
                <tr>
                  <td>
                    <div>Add New Row Position</div>
                  </td>
                  <td>
                    <div>
                      <DropDownListComponent id="newRowPosition" width="120px" index={0} change={ddChange.bind(this)} ref={d => dropDownInstance = d} dataSource={droplist} fields={{ text: 'text', value: 'value' }}/>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </PropertyPane>
        </div>
      </div>
    </div>);
}
export default NormalEdit;

const root = createRoot(document.getElementById('sample'));
root.render(<NormalEdit />);