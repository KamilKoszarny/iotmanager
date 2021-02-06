INSERT INTO site(id, name, city, street, street_no, user_id)
SELECT convert(id, bigint), name, city, street, street_no, convert(user_id, bigint)
FROM CSVREAD('classpath:/config/liquibase/fake-data/site.csv', null, 'fieldSeparator=;');
