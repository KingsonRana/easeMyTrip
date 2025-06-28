# Video Link 
https://www.loom.com/share/2f16db38643e4ed4a3dd19846b0e9629?sid=e48a51c5-94ac-4c7d-9f13-68387e9e91bb
\
# ✈️ EaseMyTrip Automation Framework

This is a **TestNG-based Selenium automation framework** designed to perform end-to-end testing of core workflows on [EaseMyTrip](https://www.easemytrip.com), including:

* User login
* Flight search
* Flight sorting
* Booking
* Booking detail verification
* Payment method verification

---

## 🚀 Prerequisites

Ensure the following tools are installed on your machine:

* **Java JDK 17+**
* **Maven**
* **IntelliJ IDEA** (✅ *Recommended*)

> ⚠️ *Note: The framework is tested and fully functional with **IntelliJ IDEA**. Using **VS Code** may cause failures due to execution order issues.*

---

## 🛠️ How to Set Up the Project

### 1. Clone the Repository

```bash
git clone https://github.com/KingsonRana/easeMyTrip.git
cd EaseMyTrip
```

### 2. Import into IntelliJ

* Open IntelliJ IDEA
* Select **Open Project** → Choose the cloned folder
* Wait for Maven to resolve dependencies
* If dependencies are not downloaded automatically, open the terminal and run:

```bash
mvn clean install
```

---

## 🔐 User Credential Configuration

To run the automation suite, you need an active [EaseMyTrip](https://www.easemytrip.com) account.

### Update the following method:

📍 Path: `EaseMyTrip/src/test/DataProviders/DataProvider.java`

```java
public Object[][] getUserAccountCredentials(){
   return new Object[][]{
       {"91", "8709560736", "Kingsonr5@gmail.com"}
   };
}
```

* **Mobile number**: Must be registered on EaseMyTrip
* **Country code & email**: Optional (but keep format intact)

---

## 📁 Test Data Configuration

All test data is stored in the `DataProvider` folder.
📍 Path: `EaseMyTrip/src/test/DataProviders/DataProvider.java`

You can modify the values (e.g., source/destination), but ensure:

* Valid locations are used
* There are available flights between source and destination

---

## ▶️ How to Run the Tests

* Open the `pom.xml` file in IntelliJ
* Right-click → Run as Maven build

> 🟡 **Important**: You will be required to manually enter the OTP and click **Login** during execution.
> Do **not** interact with the browser once login is completed.

---

## 🔄 Test Flow & Dependencies

* All test cases are **interdependent**
* A proper sequential run is necessary
* Future iterations will aim to make test cases independent

---

## 🧾 Test Reports

* A **detailed HTML test report** is generated after execution
* Any failed test will have a **screenshot attached** for reference

---

## 📌 Summary

| Feature Tested        | Status |
| --------------------- | ------ |
| Login                 | ✅      |
| Search Flight         | ✅      |
| Sort Flights          | ✅      |
| Book Flight           | ✅      |
| Verify Booking        | ✅      |
| Verify Payment Method | ✅      |

---

## 🤝 Contribution & Support

Feel free to raise issues or contribute to the improvement of this framework via pull requests.

---
