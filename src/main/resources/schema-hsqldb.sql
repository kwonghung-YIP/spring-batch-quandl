create table quandl_db_hkex (
	stock_code varchar(10) not null,
	trade_date date not null,
	open numeric,
	bid numeric,
	ask numeric,
	low numeric,
	high numeric,
	close numeric,
	volume numeric,
	turnover numeric
);