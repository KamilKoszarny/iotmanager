INSERT INTO site(id, name, city, street, street_no, user_id, created_by)
SELECT convert(id, bigint), name, city, street, street_no, convert(user_id, bigint), created_by
FROM CSVREAD('classpath:/config/liquibase/fake-data/site.csv', null, 'fieldSeparator=;');
