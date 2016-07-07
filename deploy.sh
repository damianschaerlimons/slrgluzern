./mvnw -Pprod clean package

mv target/slrg-0.0.5.war target/slrg.war

scp -r  target/slrg.war ssh root@46.101.103.2:/tmp

ssh -t root@46.101.103.2 'sh /var/www/deploy.sh'
