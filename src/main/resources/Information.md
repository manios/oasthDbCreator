### Η δομή του url έχει ως εξής:


``http://oasth.gr/{lang}/routeinfo/list/{lineId}/{masterLineId}/{dayOfWeek}/?a=1``

όπου τα path variables είναι τα εξής:
* **lang**: η γλώσσα, ελληνικά (el) ή αγγλικά (en)
* **lineId**: το id της γραμμής
* **masterLineId**: το id της master γραμμής. O master μιας γραμμής είναι μια γραμμή. Δηλαδή το λεωφορείο 15 (masterLineId = 40) έχει ως παιδιά τα:
 *  15:ΣΑΡΑΝΤΑ ΕΚΚΛΗΣΙΕΣ - ΙΣΤΟΡΙΚΟ ΚΕΝΤΡΟ με lineId = 81
 *  15Α:ΣΑΡΑΝΤΑ ΕΚΚΛΗΣΙΕΣ - ΓΥΜΝΑΣΙΟ με lineId = 322
* **dayOfWeek** : η κατεύθυνση
 * 1: καθημερινές (working day)
 * 3: Σάββατο (Saturday)
 * 4: Κυριακή και αργίες (Sundays - Off)

### Παράδειγμα: 

15: ΣΑΡΑΝΤΑ ΕΚΚΛΗΣΙΕΣ - ΙΣΤΟΡΙΚΟ ΚΕΝΤΡΟ


``http://oasth.gr/el/routeinfo/list/81/40/1/?a=1``

* **el**: ελληνικά
* **81**: το id της γραμμής
* **40**: το id της master γραμμής
* **1** : καθημερινή

Πληροφορίες γραμμής 15 : ΣΑΡΑΝΤΑ ΕΚΚΛΗΣΙΕΣ - ΙΣΤΟΡΙΚΟ ΚΕΝΤΡΟ
http://oasth.gr/el/routeinfo/list/81/40/1/?a=1

**ΣΗΜΕΙΩΣΗ: Η γραμμή αυτή έχει μόνο μία κατεύθυνση**

Η απάντηση εμπεριέχει:

* Τα ονόματα των στάσεων της γραμμής (μετάβαση και επιστροφή)
* Τις γεωργραφικές θέσεις των στάσεων της γραμμής
* Τα γεωγραφικά σημεία του δρομολογίου της γραμμής

### Παράδειγμα με τη χρήση του curl:
Γραμμή: 02 - A.S.IKEA-N.S.STATHMOS (MESO EGNATIAS)

```sh
curl "http://oasth.gr/el/routeinfo/list/67/23/1/?a=1"

-H "Accept: text/html, */*; q=0.01"
-H "Accept-Language: el-gr,el;q=0.8,en-us;q=0.5,en;q=0.3"
-H "Connection: keep-alive"
-H "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
-H "Host: oasth.gr"
-H "Referer: http://oasth.gr/"
-H "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:22.0) Gecko/20100101 Firefox/22.0"
-H "X-Requested-With: XMLHttpRequest"
```