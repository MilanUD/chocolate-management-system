# **Chocolate Management System**

## **Description**
The Chocolate Management System is a web application designed to manage chocolates and chocolate factories. The system offers functionalities tailored to different user roles, including Administrators, Managers, Workers, and Customers.

---

## **Features**
The application supports four distinct roles, each with specific permissions and capabilities:

### **Customer**
- Can purchase chocolates from factories.
- Can leave comments and reviews for factories from which they purchased chocolates.

### **Worker**
- Can increase or decrease the quantity of available chocolates.

### **Manager**
- Can create new workers.
- Can add and edit chocolates.
- Can approve or reject customer orders and comments.

### **Administrator**
- Can create factories and managers.
- Has the ability to block users.

---

## **How to Run the Project**

### **1. Clone the Repository**
Clone the repository to your local machine:
```bash
git clone https://github.com/MilanUD/chocolate-management-system.git

### **2. Run the Frontend**
-Navigate to the frontend directory:
```bash
cd chocolate-factory-front-end
-Install dependencies:
```bash
npm install
-Start the frontend application:
```bash
npm run serve
### 3. Set Up and Run the Backend
- **Open** the backend portion of the project in your development environment (e.g., **Eclipse**).
- **Configure** the Tomcat server:
  - Ensure that you have Apache Tomcat installed (version compatible with JAX-RS).
  - In **Eclipse**, go to **Servers** and add a new server.
  - Select **Apache Tomcat** and configure its installation path.
- **Deploy** the backend project to the server.
- **Run** the backend:
  ```plaintext
  Run As -> Run on Server
## **Technologies**

- **Backend**: JAX-RS for REST API services, running on a Tomcat server.
- **Frontend**: Vue.js with Vuex for state management.
- **Styling**: Basic styling using Bootstrap.

## **Author**
Developed by **Milan Keča**.

