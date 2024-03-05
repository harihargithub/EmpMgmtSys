Full Stack Development using Syncfusion components like Grid, DropDownList etc in React App and further connecting it to Java Spring Boot App with MySQL and successfully performing CRUD operations.
In this basic demo project I used the Grid to display and manipulate the list of employees, while using the DropDownList to select the position of new rows in the grid.
Used the `Inject` component to inject the necessary services into the Grid (paging, editing, toolbar, selection etc.). 
Backend: EmpMgmtSys_sboot_vite_react_mysql_sf> mvn install clean then mvn spring-boot:run (Port:8080).
Frontend: EmpMgmtSys_sboot_vite_react_mysql_sf\empvitereact>npm install then run dev (Port:5173).
Create an employee db @ MySQL and provide it's credentials @ main/src/main/resources/application.properties.
@ empvitereact/src/main.jsx - // .env - VITE_SYNCFUSION_LICENSE_KEY='Your SF Licence Key' registerLicense(import.meta.env.VITE_SYNCFUSION_LICENSE_KEY); or you may include your license directly here.
login @ http://localhost:5173/ with ramesh/welcome & try CRUD operations.
