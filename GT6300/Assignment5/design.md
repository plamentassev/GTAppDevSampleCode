
# Payment and Rewards Management System Notes

## General approach:
The design I decided to implement with UML is relatively simple , however it may need to be changed some if it is to be implemented with code, as I may have overlooked some of the methods.
The general structure resembles MVC model , where the View is not at all implemented. Thus the only 2 visible parts on the UML are the model and the controller. For simplicity I decided to use only one controller CustomerTransactionManager, which will do most of the work. This way it should be easy for me to modify the logic of the program if this is needed during the development phase.The rest of the classes are used for data representation.

## CustomerTransactionManager
This class will serve functionality similar to the function of controller in MVC. CustomerTransactionManager is directly connected to the CreditCardClass and has a method addTransaction, which makes has as one of its arguments Credit Card. This way no information about the credit card is stored in the system.

## Customer
Customer is a class used to create customers, In addition to creating a new customer, the class stores data for the total amount spent by the customer in a year and as the amount increases can modify "promote" the customer to VIP. In addition the class stores info about the total transactions made by the customer.

## Transaction 
A class used to generate individual transactions. It has logic to issue transaction credit and also checks if the customer has previously issued credit. The class provides methods to calculate the amount for each individual transaction.

## TransactionCredit
Used to generate transaction credits. The transaction credits are property of Customer as only a customer can have a transaction credit and the transaction credit can be applied to individual transaction. Each time a new credit is generated, an email is sent to the customer.Therefore Transaction Credit is directly connected to the EmailGenerator class.


## TransactionReport
Is used to generate transaction reports, therefore it is connected to the CustomerTransactionManager. The customer Transaction manager has all the Transactions stored and can send them as parameter to the TransactionReportClass

## EmailGenerator
According to the requirements emails are sent in 2 cases only : when a transaction is generated and when a transaction credit is issued. Therefore the EmailGenerator class is connected to Transaction and Transaction Credit classes. 


 

## Customer Card 
In the example from the lecture the Library Card was incorporated in the Customer properties, I decided t keep it separate for now. I may further move the only property QRCode to the Customer class.

* The system utilizes several external devices:

	Camera : used to scan the  customer card

	Printer: used to print receipts from the transactions

	Credit card scanner: used to scan credit cards
	
	**For simplicity all devices are connected with arrows, which in this case do not indicate inheritance, but are used to show to flow of data to and from the external devices or libraries.


* Customer Card : while in the example from the lecture the Library Card was incorporated in the Customer properties, I decided t keep it separate. Incorporation of classes can and eventually lead to problems with cohesion.
 
	