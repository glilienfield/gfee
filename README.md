# gfee
Spring Boot application prototyping a Gfee service. Currently implements only read operations for live and historical values. 

Live gfee request urls: </br>
/gfee/live/{sellerNo} - retrieve a seller's gfee for all products  </br>
/gfee/live/{sellerNo}/{productCode} - retrieve a seller's gfee for specific product  </br>
 </br>
Historical gfee request urls: </br>
/gfee/hist/{sellerNumber}/{productCode}/{gfeePriceEpoc} - retrieve a seller's historical gfee value for a specific product at a specific point in time </br>
 </br>
Data provisioning  </br>
/provision - Provision a small set of test data  </br>
 </br> 
Notes: </br>
1. all requests are "Get" requests </br>
2. times are represented in epoc time </br>
3. provisioning is done asynchronously...give it about a minute to complete before requesting data...verify in your aws console </br>
4. add aws credentials to application.properties file </br>
</br>
Live data: </br>
sellers: 111111111 and 22222222 </br>
product codes: 100, 101, 102 </br>
</br>
Historical data:</br>
intervals for 4 histrical values of gfee for seller '1111111' and product code = '100': </br>
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
