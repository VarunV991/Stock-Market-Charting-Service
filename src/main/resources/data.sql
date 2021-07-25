INSERT INTO ROLE(id,name) VALUES
(1,'ROLE_USER'),
(2,'ROLE_ADMIN');

INSERT INTO SECTOR(id,name,description) VALUES
(1,'Finance','Companies that assist in Finance and Accounting'),
(2,'Healthcare','Companies that provide Healthcare Services'),
(3,'Energy','Companies that do business in the oil and natural gas industry'),
(4,'Materials','Companies that provide various goods for use in manufacturing and other applications'),
(5,'IT','Companies involved in the different categories of technological innovation');

INSERT INTO STOCK_EXCHANGE(id,name,description,address,remarks) VALUES
(1,'BSE','Bombay Stock Exchange','Dalal Street, Mumbai, India','World''s 10th largest stock-exchange'),
(2,'NSE','National Stock Exchange','Mumbai, India','India''s 4th largest stock-exchange');

INSERT INTO COMPANY(id,name,turnover,ceo,description,BOARD_OF_DIRECTORS,SECTOR_ID) VALUES
(1,'Reliance','$3.1 Billion/year','Mukesh','Largest Conglomerate','Mukesh,Nita,Anil',(SELECT id from SECTOR where name='Materials')),
(2,'TCS','$4 Billion/year','Ratan','Multi-National Conglomerate','Ratan,Chetan,Suman',(SELECT id from SECTOR where name='IT'));

INSERT INTO IPO(id,EXCHANGE_NAME,PRICE_PER_SHARE,TOTAL_SHARES,OPEN_DATE_TIME,remarks,COMPANY_ID) VALUES
(1,'BSE',121,7895,'2017-06-13T11:08:04.017494','Good',(SELECT id from COMPANY where name='Reliance'));

INSERT INTO COMPANY_EXCHANGE_CODE(id,COMPANY_CODE,COMPANY_ID,STOCK_EXCHANGE_ID) VALUES
(1,'500325',(SELECT id from COMPANY where name='Reliance'),(SELECT id from STOCK_EXCHANGE where name='BSE'));

INSERT INTO STOCK_PRICE(id,price,date,time,STOCK_EXCHANGE_NAME,COMPANY_ID) VALUES
(1,154.34,'2017-05-04','10:04:55','BSE',(SELECT id from COMPANY where name='Reliance'));