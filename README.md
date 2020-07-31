# gfee
Spring Boot application prototyping a Gfee service. Currently implements only read operations for live and historical values. 

Live gfee request urls: </br>
/gfee/live/{sellerNo} - retrieve a seller's gfee for all products  </br>
/gfee/live/{sellerNo}/{product} - retrieve a seller's gfee for specific product  </br>
 </br>
Historical gfee request urls: </br>
/gfee/hist/{sellerNumber}/{productCode}/{gfeePriceEpoc} - retrieve a seller's historical gfee value a specific product at a specific point in time </br>
 </br>
Data provisioning  </br>
/provision - Provision small set of test data  </br>
 </br> 
Notes: </br>
1. all requests are "Get" requests </br>
2. times are represented in epoc time </br>
