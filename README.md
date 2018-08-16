# EasePharma ![CI status](https://img.shields.io/badge/build-passing-brightgreen.svg)

Prototype the following project:
Build an application where the User’s data can be shared only if they approve it.
There are 3 types of users/roles:
 
1.Patient/User
2.Doctor
3.Pharmacist
 
The Patient has medical records and prescriptions. If a doctor asks for a patient’s prescription, the patient has to approve it. Same goes with the Pharmacist, if the pharmacist wants to view the patient’s prescription, the patient has to approve it.



## Installation

### Requirements
* Java 1.8 or higher
* Maven
* Apache tomcat 8 or higher
* mysql 5.6




## Usage

* Navigate to EasePharma folder and run

```
$ make clean
```
* Copy the war created to tomcat webapps
* Run EasePharma/db/schema.sql to initialize db
* Start the tomcat server and hit localhost:8080/EasePharma/index.html
## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)# pharmease
