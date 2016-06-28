./mvnw -Pprod package

mv target/slrg-0.0.1-SNAPSHOT.war target/slrg.war

scp -r  target/slrg.war ssh slrg@46.101.186.218:/tmp

#ssh -t slrg@46.101.186.218 'sh /slrg/deploy.sh'
