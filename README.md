# gfee
Spring Boot application prototyping a limited Gfee service, done to validate a potential dynamoDB data model. Currently supports reading live and historial gfee values for a seller's products, and updating a live gfee value with versioning.

<b>Live gfee request urls: </b></br>
/gfee/live/{sellerNo} - retrieve a seller's gfee for all products  </br>
/gfee/live/{sellerNo}/{productCode} - retrieve a seller's gfee for specific product  </br>
 </br>
<b>Historical gfee request urls: </b></br>
/gfee/hist/{sellerNumber}/{productCode}/{gfeePriceEpoc} - retrieve a seller's historical gfee value for a specific product at a specific point in time </br>
</br><b>Update gfee for a specific seller and product and versions old value:</b></br>
/gfee/update/{sellerNumber}/{productCode}/{value}</br>
</br><b>Data provisioning </b>
</br>/provision - Provision dynamodb tables and a small set of test data</br>
</br><b>Data Model:</b></br>
Live gfee values for each seller / product are persistent in a dynamoDB table named "Gfee_Live." Querying by seller number will return the gfee for all of a seller's products. Querying by seller and product will return just that product's gfee for that seller </br>
Historical gfee values for each seller / product are persistend in a dynamoDB table named "Gfee_Hist." Each item has startEpocTime and endEpocTime values indicating when the value become effecitive and when it was replaced with a new value. </br>
The current live value also has a corresponding item in the Gfee_Hist table, which is needed for propery quering for historical gfee values. This item has an endEpocTime equal to "99999999999999", which represents a date in the year 5138. Utilizing a specific "special" endEpoctime allows quering on a partition key index for getting the historical item corresponding to the current live item.</br>
<b>Notes: </b></br>
1. All requests are "Get" requests </br>
2. Times are represented in epoc time </br>
3. Provisioning is done asynchronously...give it about a minute to complete before requesting data...verify in your aws console </br>
4. Add aws credentials to application.properties file </br>
<ul>
 <li>dynamodb.accessKey</li>
 <li>dynamodb.secrectKey</li>
</ul>
<b>Live data: </b></br>
sellers: 111111111 and 22222222 </br>
product codes: 100, 101, 102 </br>
</br>
<b>Historical data:</b></br>
Intervals for 4 historical values of gfee for seller '1111111' and product code = '100': </br>
startEpocTime: 1588330800000 </br>
endEpocTime: 1589540400000</br>
</br>
startEpocTime: 1589540405000 </br>
endEpocTime: 1591009200000</br>
</br>
startEpocTime: 1591009205000 </br>
endEpocTime: 1592218800000</br>
</br></br>
startEpocTime: 1592218805000 </br>
endEpocTime: 99999999999999</br>
</br>
